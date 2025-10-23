package kware.apps.mobigen.cetus.dataset.service;


import cetus.bean.Page;
import cetus.bean.Pageable;
import cetus.user.UserUtil;
import cetus.util.StringUtil;
import kware.apps.mobigen.cetus.dataset.domain.CetusMobigenDataset;
import kware.apps.mobigen.cetus.dataset.domain.CetusMobigenDatasetDao;
import kware.apps.mobigen.cetus.dataset.dto.request.*;
import kware.apps.mobigen.cetus.dataset.dto.response.DeleteApprovedDatasetIfExist;
import kware.apps.mobigen.cetus.dataset.dto.response.MobigenDatasetList;
import kware.apps.mobigen.cetus.dataset.dto.response.MobigenDatasetView;
import kware.apps.mobigen.cetus.tag.dto.response.TagList;
import kware.apps.mobigen.cetus.tag.service.CetusMobigenDatasetTagService;
import kware.apps.thirdeye.mobigen.datasetfile.domain.CetusDatasetFile;
import kware.apps.thirdeye.mobigen.datasetfile.dto.request.SearchDatasetFile;
import kware.apps.thirdeye.mobigen.datasetfile.dto.response.CetusDatasetFileView;
import kware.apps.thirdeye.mobigen.datasetfile.enumcd.DataFileTpCd;
import kware.apps.thirdeye.mobigen.datasetfile.service.CetusDatasetFileService;
import kware.apps.thirdeye.mobigen.mobigenregistrant.dto.response.MobigenRegistrantView;
import kware.apps.thirdeye.mobigen.mobigenregistrant.service.CetusMobigenRegistrantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CetusMobigenDatasetService {

    private final CetusMobigenDatasetDao dao;

    private final CetusDatasetFileService datasetFileService;
    private final CetusMobigenDatasetTagService tagService;
    private final CetusMobigenRegistrantService registrantService;


    /**
     * @method      changeListToPage
     * @author      dahyeon
     * @date        2025-10-15
     * @description 리스트에 대한 페이징 작업 (공통)
     *              => 모비젠 측과 kware 포탈의 페이징 로직 차이로 인한 공통 메서드
     */
    private <T> Page<T> changeListToPage(Integer pageNumber, Integer pageSize, List<T> list) {

        // 1. 전체 개수 및 페이지 정보
        int totalCount = list.size();
        int fromIndex = (pageNumber - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, totalCount);

        // 2. 리스트 자르기
        List<T> pagedList = new ArrayList<>();
        if (fromIndex < totalCount) {
            pagedList = list.subList(fromIndex, toIndex);
        }

        // 3. Page 객체 생성 (카운트 포함)
        return new Page<>(pagedList, totalCount, new Pageable(pageSize, pageNumber, pageSize));
    }


    /**
     * @method      findAllMobigenDatasetPage
     * @author      dahyeon
     * @date        2025-10-13
     * @deacription [Mobigen] 데이터셋 목록 페이징 조회
    **/
    @Transactional(readOnly = true)
    public Page<MobigenDatasetList> findAllMobigenDatasetPage(SearchMobigenDataset search) {

        List<MobigenDatasetList> allMobigenDatasetList = dao.getAllMobigenDatasetList(search);
        if(search.getApprovedIds() != null && search.getApprovedIds().length != 0) {
            // {approvedIds}에 포함되지 않은 dataset 목록만 필터링
            Long[] approvedIds = search.getApprovedIds();
            Set<Long> approvedSet = approvedIds != null
                    ? new HashSet<>(Arrays.asList(approvedIds))
                    : Collections.emptySet();
            // 필터링
            allMobigenDatasetList = allMobigenDatasetList.stream()
                    .filter(ds -> !approvedSet.contains(ds.getUid()))
                    .collect(Collectors.toList());
        }

        Page<MobigenDatasetList> page = this.changeListToPage(search.getPageNumber(), search.getSize(), allMobigenDatasetList);
        page.getList().forEach(data -> {
            Long datasetId = data.getUid();
            MobigenRegistrantView registrantView = registrantService.findMobigenRegistrant(datasetId);
            if(registrantView != null) data.setRegistrantInfo(registrantView.isRegistered(), registrantView.getRegistrantId());
            else data.setRegistrantInfo(false, null);
        });
        return page;
    }


    /**
     * @method      findRealDataPage
     * @author      dahyeon
     * @date        2025-10-21
     * @deacription 메타데이터 하위 원본데이터 파일 목록 페이징 조회
    **/
    @Transactional(readOnly = true)
    public Page<CetusDatasetFileView> findRealDataPage(Long datasetId, SearchMobigenDatasetRealdata search) {
        SearchDatasetFile searchFile = new SearchDatasetFile(Long.toString(datasetId), null, DataFileTpCd.RAWDATA.name());
        List<CetusDatasetFileView> rawdataFiles = datasetFileService.findDataFile(searchFile);
        return this.changeListToPage(search.getPageNumber(), search.getSize(), rawdataFiles);
    }


    /**
     * @method      changeMobigenDataset
     * @author      dahyeon
     * @date        2025-10-13
     * @deacription [Mobigen] 데이터셋 수정
     *
     *                  (1) 실데이터(원본데이터) 파일 저장
     *                      => 해당 파일은 누적 등록 가능
     *                      => 누적 등록될 경우, 주기성 데이터셋처럼 보이게 됨
     *                  (2) 모비젠 데이터 정보 수정
     *                  (3) 모비젠 데이터에 대한 태그 정보 저장
    **/
    @Transactional
    public void changeMobigenDataset(Long datasetId, ChangeMobigenDataset request) {

        // 1. save realdata file
        CetusDatasetFile realFile = request.getRealFile();
        realFile.setMetadataId(Long.toString(datasetId));
        realFile.setDataTpCd(DataFileTpCd.RAWDATA.name());
        String rawdataId = "raw_" + datasetId + "_" + StringUtil.random(3);
        realFile.setRawdataId(rawdataId);
        datasetFileService.processAddFile(realFile);

        // 2. update mobigen data
        CetusMobigenDataset bean = new CetusMobigenDataset(datasetId, request);
        dao.update(bean);

        // 3. save tag
        tagService.saveDatasetTag(request.getTags(), datasetId);
    }

    /**
     * @method      deleteSeveralMobigenDataset
     * @author      dahyeon
     * @date        2025-10-13
     * @deacription [Mobigen] 데이터셋 삭제
     *                  => 예비 KWARE 포탈 시스템에서는 논리 삭제로 동작중
    **/
    @Transactional
    public void deleteSeveralMobigenDataset(DeleteDatasets request) {
        for (Long datasetId: request.getUids()) {
            CetusMobigenDataset bean = new CetusMobigenDataset(datasetId);
            dao.deleteMobigenDataset(bean);

            // {datasetId} 데이터셋이 kware 포탈 시스템에서 관리중인 값이라면 포탈 시스템에서도 삭제 (논리삭제)
            DeleteApprovedDatasetIfExist req = new DeleteApprovedDatasetIfExist(datasetId, UserUtil.getUserWorkplaceUid());
            dao.ifExistDeleteApprovedDataset(req);
        }
    }

    /**
     * @method      findMobigenDatasetByDatasetId
     * @author      dahyeon
     * @date        2025-10-13
     * @deacription [Mobigen] 데이터셋 단건 조회
    **/
    @Transactional(readOnly = true)
    public MobigenDatasetView findMobigenDatasetByDatasetId(Long datasetId, boolean useTag, boolean useFiles) {

        // 1. dataset view
        // => todo 추후에 해당 부분은 API 이용 변경
        MobigenDatasetView view = dao.getMobigenDatasetByDatasetId(datasetId);

        // 2. dataset registrant info
        // => kware 백단 태우기 유지
        MobigenRegistrantView registrantView = registrantService.findMobigenRegistrant(datasetId);
        view.setRegistrantId((registrantView != null) ? registrantView.getRegistrantId() : null);

        // 3. dataset tag
        // => todo 추후에 해당 부분은 API 이용 변경
        if(useTag) {
            List<TagList> tags = tagService.findMobigenDatasetTagListByDatasetUid(datasetId);
            view.setTags(tags);
        }

        // 4. dataset package file
        if(useFiles) {
            SearchDatasetFile search = new SearchDatasetFile(Long.toString(datasetId), null, DataFileTpCd.PACKAGE.name());
            List<CetusDatasetFileView> packagedataFiles = datasetFileService.findDataFile(search);
            view.setPackagedataFile((packagedataFiles != null && !packagedataFiles.isEmpty()) ? packagedataFiles.get(0) : null);
        }

        // 5. dataset metadata file
        // => todo 추후에 해당 부분은 API 이용 변경
        if(useFiles){
            SearchDatasetFile search = new SearchDatasetFile(Long.toString(datasetId), null, DataFileTpCd.METADATA.name());
            List<CetusDatasetFileView> metadataFiles = datasetFileService.findDataFile(search);
            view.setMetadataFile((metadataFiles != null && !metadataFiles.isEmpty()) ? metadataFiles.get(0) : null);
        }

        // 6. dataset rawdata file
        // => todo 추후에 해당 부분은 API 이용 변경
        if(useFiles){
            SearchDatasetFile search = new SearchDatasetFile(Long.toString(datasetId), null, DataFileTpCd.RAWDATA.name());
            List<CetusDatasetFileView> rawdataFiles = datasetFileService.findDataFile(search);
            view.setRawdataFiles(rawdataFiles);
        }

        return view;
    }

    @Transactional
    public void deleteRealdatas(DeleteRealdatas request) {
        for (String fileId: request.getFileIds()) {
            datasetFileService.processDelFile(fileId);
        }
    }

    @Transactional
    public void insert(CetusMobigenDataset bean) {
        dao.insert(bean);
    }
}
