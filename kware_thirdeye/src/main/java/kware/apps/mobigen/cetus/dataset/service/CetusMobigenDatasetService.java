package kware.apps.mobigen.cetus.dataset.service;


import cetus.bean.Page;
import cetus.bean.Pageable;
import cetus.user.UserUtil;
import kware.apps.mobigen.cetus.category.dto.response.CategoryList;
import kware.apps.mobigen.cetus.category.service.CetusMobigenDatasetCategoryService;
import kware.apps.mobigen.cetus.dataset.domain.CetusMobigenDataset;
import kware.apps.mobigen.cetus.dataset.domain.CetusMobigenDatasetDao;
import kware.apps.mobigen.cetus.dataset.dto.request.ChangeMobigenDataset;
import kware.apps.mobigen.cetus.dataset.dto.request.DeleteDatasets;
import kware.apps.mobigen.cetus.dataset.dto.request.SaveMobigenDataset;
import kware.apps.mobigen.cetus.dataset.dto.request.SearchMobigenDataset;
import kware.apps.mobigen.cetus.dataset.dto.response.MobigenDatasetList;
import kware.apps.mobigen.cetus.dataset.dto.response.MobigenDatasetView;
import kware.apps.mobigen.cetus.tag.dto.response.TagList;
import kware.apps.mobigen.cetus.tag.service.CetusMobigenDatasetTagService;
import kware.apps.thirdeye.mobigen.mobigenregistrant.service.CetusMobigenRegistrantService;
import kware.common.config.auth.dto.SessionUserInfo;
import kware.common.file.domain.CommonFile;
import kware.common.file.service.CommonFileService;
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
    private final CommonFileService commonFileService;

    private final CetusMobigenDatasetTagService tagService;
    private final CetusMobigenDatasetCategoryService categoryService;

    private final CetusMobigenRegistrantService registrantService;

    /**
     * @method      findAllMobigenDatasetPage
     * @author      dahyeon
     * @date        2025-10-13
     * @deacription [Mobigen] 데이터셋 목록 페이징 조회
    **/
    @Transactional(readOnly = true)
    public Page<MobigenDatasetList> findAllMobigenDatasetPage(SearchMobigenDataset search, Pageable pageable) {

        log.info(">>> [Mobigen] 데이터셋 목록 페이징 조회");

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
        int count = filteredList.size();

        Page<MobigenDatasetList> page = new Page<MobigenDatasetList>(filteredList, count, pageable);

        return page;
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
     *                  (4) 모비젠 데이터에 대한 카테고리 & 태그 정보 저장
     *                  (5) 모비젠 등록자 정보 저장
    **/
    @Transactional
    public void saveMobigenDataset(SaveMobigenDataset request) {
        SessionUserInfo user = UserUtil.getUser();

        log.info(">>> [Mobigen] 데이터셋 저장");

        // 1. save metadata file
        Long metadataFileUid = null;
        metadataFileUid= commonFileService.processFile2(request.getMetaFile(), null, user, metadataFileUid);

        // 2. save realdata file
        Long realdataFileUid = null;
        realdataFileUid= commonFileService.processFile2(request.getRealFile(), null, user, realdataFileUid);

        // 3. save mobigen data
        CetusMobigenDataset bean = new CetusMobigenDataset(request, metadataFileUid, realdataFileUid);
        dao.insert(bean);
        Long datasetUid = bean.getUid();

        // 4. save category
        tagService.saveDatasetTag(request.getTags(), datasetUid);

        // 5. save tag
        categoryService.saveDatasetCategory(request.getCategories(), datasetUid);

        // 6. 모비젠 등록자 정보 저장
        registrantService.saveMobigenRegistrant(datasetUid);
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
     *                  (3) 모비젠 데이터에 대한 카테고리 & 태그 정보 저장
    **/
    @Transactional
    public void changeMobigenDataset(Long datasetUid, ChangeMobigenDataset request) {

        log.info(">>> [Mobigen] 데이터셋 수정");

        // 1. save realdata file
        Long realdataFileUid = request.getRealdataFileUid();
        realdataFileUid= commonFileService.processFile2(request.getRealFile(), null, UserUtil.getUser(), realdataFileUid);

        // 2. update mobigen data
        CetusMobigenDataset bean = new CetusMobigenDataset(datasetUid, realdataFileUid, request);
        dao.update(bean);

        // 3. save category
        tagService.saveDatasetTag(request.getTags(), datasetUid);

        // 4. save tag
        categoryService.saveDatasetCategory(request.getCategories(), datasetUid);
    }

    /**
     * @method      deleteSeveralMobigenDataset
     * @author      dahyeon
     * @date        2025-10-13
     * @deacription [Mobigen] 데이터셋 삭제 (하드 삭제)
     *                  => 예비 KWARE 포탈 시스템에서는 논리 삭제로 동작중
    **/
    @Transactional
    public void deleteSeveralMobigenDataset(DeleteDatasets request) {
        log.info(">>> [Mobigen] 데이터셋 삭제 (하드 삭제)");
        for (Long uid: request.getUids()) {
            CetusMobigenDataset bean = new CetusMobigenDataset(uid);
            dao.deleteMobigenDataset(bean);
        }
    }

    /**
     * @method      findMobigenDatasetByDatasetId
     * @author      dahyeon
     * @date        2025-10-13
     * @deacription [Mobigen] 데이터셋 단건 조회
    **/
    @Transactional(readOnly = true)
    public MobigenDatasetView findMobigenDatasetByDatasetId(Long datasetUid) {

        log.info(">>> [Mobigen] 데이터셋 단건 조회");

        // 1. dataset view
        MobigenDatasetView view = dao.getMobigenDatasetByDatasetId(datasetUid);

        // 2. dataset tag
        List<TagList> tags = tagService.findMobigenDatasetTagListByDatasetUid(datasetUid);
        view.setTags(tags);

        // 3. dataset category
        List<CategoryList> categories = categoryService.findMobigenDatasetCategoryListByDatasetUid(datasetUid);
        view.setCategories(categories);

        // 4. dataset metadata file
        List<CommonFile> metadataFiles = (view.getMetadataFileUid() != null) ? commonFileService.findCommonFileListByFileUid(view.getMetadataFileUid()) : new ArrayList<>();
        view.setMetadataFiles(metadataFiles);

        // 5. dataset realdata file
        List<CommonFile> realdataFiles = (view.getRealdataFileUid() != null) ? commonFileService.findCommonFileListByFileUid(view.getRealdataFileUid()) : new ArrayList<>();
        view.setRealdataFiles(realdataFiles);

        return view;
    }
}
