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
import kware.common.config.auth.dto.SessionUserInfo;
import kware.common.file.domain.CommonFile;
import kware.common.file.service.CommonFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CetusMobigenDatasetService {

    private final CetusMobigenDatasetDao dao;
    private final CommonFileService commonFileService;

    private final CetusMobigenDatasetTagService tagService;
    private final CetusMobigenDatasetCategoryService categoryService;

    @Transactional(readOnly = true)
    public Page<MobigenDatasetList> findAllMobigenDatasetPage(SearchMobigenDataset search, Pageable pageable) {
        return dao.page("getAllMobigenDatasetPage", "getAllMobigenDatasetPageCount", search, pageable);
    }

    @Transactional
    public void saveMobigenDataset(SaveMobigenDataset request) {
        SessionUserInfo user = UserUtil.getUser();

        // 1. save metadata file
        Long metadataFileUid = null;
        metadataFileUid= commonFileService.processFile2(request.getMetaFile(), null, user, metadataFileUid);

        // 2. save realdata file
        Long realdataFileUid = null;
        realdataFileUid= commonFileService.processFile2(request.getRealFile(), null, user, realdataFileUid);

        // 3. save mobigen data
        CetusMobigenDataset bean = new CetusMobigenDataset(request, metadataFileUid, realdataFileUid, user.getUserId());
        dao.insert(bean);
        Long datasetUid = bean.getUid();

        // 4. save category
        tagService.saveDatasetTag(request.getTags(), datasetUid);

        // 5. save tag
        categoryService.saveDatasetCategory(request.getCategories(), datasetUid);
    }

    @Transactional
    public void changeMobigenDataset(Long datasetUid, ChangeMobigenDataset request) {

        // 1. save realdata file
        Long realdataFileUid = request.getRealdataFileUid();
        realdataFileUid= commonFileService.processFile2(request.getRealFile(), null, UserUtil.getUser(), realdataFileUid);

        // 2. save mobigen data
        CetusMobigenDataset bean = new CetusMobigenDataset(datasetUid, realdataFileUid, request);
        dao.update(bean);

        // 3. save category
        tagService.saveDatasetTag(request.getTags(), datasetUid);

        // 4. save tag
        categoryService.saveDatasetCategory(request.getCategories(), datasetUid);
    }

    @Transactional
    public void deleteSeveralMobigenDataset(DeleteDatasets request) {
        for (Long uid: request.getUids()) {
            CetusMobigenDataset bean = new CetusMobigenDataset(uid);
            dao.deleteMobigenDataset(bean);
        }
    }

    @Transactional(readOnly = true)
    public MobigenDatasetView findMobigenDatasetByDatasetId(Long datasetUid) {

        // 1. dataset view
        MobigenDatasetView view = dao.getMobigenDatasetByDatasetId(datasetUid);

        // 2. dataset tag
        List<TagList> tags = tagService.findMobigenDatasetTagListByDatasetId(datasetUid);
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
