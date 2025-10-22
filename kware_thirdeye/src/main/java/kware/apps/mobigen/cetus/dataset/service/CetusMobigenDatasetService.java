package kware.apps.mobigen.cetus.dataset.service;


import cetus.bean.Page;
import cetus.bean.Pageable;
import cetus.user.UserUtil;
import cetus.util.StringUtil;
import kware.apps.mobigen.cetus.dataset.domain.CetusMobigenDataset;
import kware.apps.mobigen.cetus.dataset.domain.CetusMobigenDatasetDao;
import kware.apps.mobigen.cetus.dataset.dto.request.*;
import kware.apps.mobigen.cetus.dataset.dto.response.*;
import kware.apps.mobigen.cetus.tag.dto.response.TagList;
import kware.apps.mobigen.cetus.tag.service.CetusMobigenDatasetTagService;
import kware.apps.thirdeye.mobigen.datasetfile.domain.CetusDatasetFile;
import kware.apps.thirdeye.mobigen.datasetfile.dto.response.CetusDatasetFileView;
import kware.apps.thirdeye.mobigen.datasetfile.enumcd.DataFileTpCd;
import kware.apps.thirdeye.mobigen.datasetfile.service.CetusDatasetFileService;
import kware.apps.thirdeye.mobigen.mobigenregistrant.dto.response.MobigenRegistrantView;
import kware.apps.thirdeye.mobigen.mobigenregistrant.service.CetusMobigenRegistrantService;
import kware.common.config.auth.dto.SessionUserInfo;
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
     * @method      findAllMobigenDatasetList
     * @author      dahyeon
     * @date        2025-10-13
     * @deacription [Mobigen] 데이터셋 목록 페이징 조회
    **/
    @Transactional(readOnly = true)
    public Page<MobigenDatasetList> findAllMobigenDatasetList(SearchMobigenDataset search) {

        List<MobigenDatasetList> list = dao.getAllMobigenDatasetList(search);

        // {approvedIds}에 포함되지 않은 dataset 목록만 필터링
        Long[] approvedIds = search.getApprovedIds();
        Set<Long> approvedSet = approvedIds != null
                ? new HashSet<>(Arrays.asList(approvedIds))
                : Collections.emptySet();
        // 필터링
        List<MobigenDatasetList> filteredList = list.stream()
                .filter(ds -> !approvedSet.contains(ds.getUid()))
                .collect(Collectors.toList());

        // 최종 페이징
        Page<MobigenDatasetList> page = this.changeListToPage(search.getPageNumber(), search.getSize(), filteredList);
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
        List<CetusDatasetFileView> realdataFiles = datasetFileService.findRawdataFilesByMetadataId(Long.toString(datasetId));
        return this.changeListToPage(search.getPageNumber(), search.getSize(), realdataFiles);
    }

    /**
     * @method      saveMobigenDataset
     * @author      dahyeon
     * @date        2025-10-13
     * @deacription [Mobigen] 데이터셋 저장
     * 
     *                  (1) 메타데이터 파일 저장
     *                  (2) 실데이터(원본데이터) 파일 저장
     *                  (3) 모비젠 데이터 정보 저장
     *                  (4) 모비젠 데이터에 대한 태그 정보 저장
     *                  (5) 모비젠 등록자 정보 저장
    **/
    @Transactional
    public void saveMobigenDataset(SaveMobigenDataset request) {
        SessionUserInfo user = UserUtil.getUser();

        // 1. save mobigen data
        CetusMobigenDataset bean = new CetusMobigenDataset(request);
        dao.insert(bean);
        Long datasetId = bean.getUid();

        // 1. save metadata file
        CetusDatasetFile metaFile = request.getMetaFile();
        metaFile.setMetadataId(Long.toString(datasetId));
        metaFile.setDataTpCd(DataFileTpCd.METADATA.name());
        datasetFileService.processFile(new CetusDatasetFile[]{ metaFile }, null);

        // 2. save realdata file
        CetusDatasetFile[] realFiles = request.getRealFiles();
        for (CetusDatasetFile datasetFile: realFiles) {
            datasetFile.setMetadataId(Long.toString(datasetId));
            datasetFile.setDataTpCd(DataFileTpCd.RAWDATA.name());
            String rawdataId = "raw_" + datasetId + "_" + StringUtil.random(3);
            datasetFile.setRawdataId(rawdataId);
        }
        datasetFileService.processFile(realFiles, null);

        // 3. save tag
        tagService.saveDatasetTag(request.getTags(), datasetId);

        // 4. 모비젠 등록자 정보 저장
        registrantService.saveMobigenRegistrant(datasetId);
    }

    /**
     * @method      saveMobigenPackageDataset
     * @author      dahyeon
     * @date        2025-10-17
     * @deacription [Mobigen] 데이터셋 저장
     *              => 패키지 파일 형태 업로드 (zip)
    **/
    public void saveMobigenPackageDataset(SaveMobigenPackageDataset request) {
        // todo mobigen API 태우기
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
        CetusDatasetFile[] realFiles = request.getRealFiles();
        for (CetusDatasetFile datasetFile: realFiles) {
            datasetFile.setMetadataId(Long.toString(datasetId));
            datasetFile.setDataTpCd(DataFileTpCd.RAWDATA.name());
            String rawdataId = "raw_" + datasetId + "_" + StringUtil.random(3);
            datasetFile.setRawdataId(rawdataId);
        }
        datasetFileService.processFile(realFiles, null);

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

        // 4. dataset metadata file
        // => todo 추후에 해당 부분은 API 이용 변경
        if(useFiles){
            CetusDatasetFileView metadataFile = datasetFileService.findMetadataFileByMetadataId(Long.toString(datasetId));
            view.setMetadataFile(metadataFile);
        }

        // 5. dataset realdata file
        // => todo 추후에 해당 부분은 API 이용 변경
        if(useFiles){
            List<CetusDatasetFileView> rawdataFiles = datasetFileService.findRawdataFilesByMetadataId(Long.toString(datasetId));
            view.setRawdataFiles(rawdataFiles);
        }

        return view;
    }

    @Transactional
    public void deleteRealdatas(DeleteRealdatas request) {
        for (String fileId: request.getFileIds()) {
            dao.deleteRealdata(fileId);
        }
    }
}
