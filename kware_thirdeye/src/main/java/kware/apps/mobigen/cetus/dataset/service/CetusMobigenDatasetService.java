package kware.apps.mobigen.cetus.dataset.service;


import cetus.bean.Page;
import cetus.bean.Pageable;
import kware.apps.mobigen.cetus.dataset.domain.CetusMobigenDataset;
import kware.apps.mobigen.cetus.dataset.domain.CetusMobigenDatasetDao;
import kware.apps.mobigen.cetus.dataset.dto.request.SearchMobigenDataset;
import kware.apps.mobigen.cetus.dataset.dto.request.SearchMobigenDatasetRealdata;
import kware.apps.mobigen.cetus.dataset.dto.response.MobigenDatasetView;
import kware.apps.mobigen.cetus.tag.service.CetusMobigenDatasetTagService;
import kware.apps.mobigen.mobigen.dto.response.common.MetadataResultResponse;
import kware.apps.thirdeye.mobigen.datasetfile.dto.request.SearchDatasetFile;
import kware.apps.thirdeye.mobigen.datasetfile.dto.response.CetusDatasetFileView;
import kware.apps.thirdeye.mobigen.datasetfile.enumcd.DataFileTpCd;
import kware.apps.thirdeye.mobigen.datasetfile.service.CetusDatasetFileService;
import kware.apps.thirdeye.mobigen.mobigenregistrant.service.CetusMobigenRegistrantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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


    /* =============== */

    @Transactional
    public void insert(CetusMobigenDataset bean) {
        dao.insert(bean);
    }

    @Transactional
    public void update(CetusMobigenDataset bean) {
        dao.update(bean);
    }

    @Transactional
    public void ifExistDeleteApprovedDataset(Long datasetId) {
        dao.ifExistDeleteApprovedDataset(datasetId);
    }

    @Transactional
    public void deleteMobigenDataset(CetusMobigenDataset bean) {
        dao.deleteMobigenDataset(bean);
    }

    @Transactional(readOnly = true)
    public MobigenDatasetView findMobigenDatasetByDatasetId(Long datasetId) {
        return dao.getMobigenDatasetByDatasetId(datasetId);
    }

    @Transactional(readOnly = true)
    public int findAllMobigenDatasetListCount() {
        return dao.getAllMobigenDatasetListCount();
    }

    @Transactional(readOnly = true)
    public List<MetadataResultResponse> findAllMobigenDatasetList(SearchMobigenDataset search) {
        return dao.getAllMobigenDatasetList(search);
    }
}
