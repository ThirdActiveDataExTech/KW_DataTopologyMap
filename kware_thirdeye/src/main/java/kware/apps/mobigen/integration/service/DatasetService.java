package kware.apps.mobigen.integration.service;

import cetus.bean.Page;
import cetus.bean.Pageable;
import cetus.user.UserUtil;
import cetus.util.StringUtil;
import kware.apps.mobigen.cetus.dataset.domain.CetusMobigenDataset;
import kware.apps.mobigen.cetus.dataset.dto.request.SearchMobigenDataset;
import kware.apps.mobigen.cetus.dataset.service.CetusMobigenDatasetService;
import kware.apps.mobigen.cetus.tag.dto.response.TagList;
import kware.apps.mobigen.cetus.tag.service.CetusMobigenDatasetTagService;
import kware.apps.mobigen.cetus.tag.service.CetusMobigenTagService;
import kware.apps.mobigen.integration.dto.request.meta.SearchMetaValues;
import kware.apps.mobigen.integration.dto.request.metadata.*;
import kware.apps.mobigen.integration.dto.request.pckg.DownloadPackageDataset;
import kware.apps.mobigen.integration.dto.request.pckg.SavePackageDataset;
import kware.apps.mobigen.integration.dto.request.rawdata.*;
import kware.apps.mobigen.integration.dto.request.recommendation.SearchRecommendationPage;
import kware.apps.mobigen.integration.dto.request.relation.SearchRelationsPage;
import kware.apps.mobigen.integration.dto.response.meta.MetaKeyList;
import kware.apps.mobigen.integration.dto.response.meta.MetaKeyValueList;
import kware.apps.mobigen.integration.dto.response.metadata.MetadataList;
import kware.apps.mobigen.integration.dto.response.metadata.MetadataView;
import kware.apps.mobigen.integration.dto.response.rawdata.RawdataList;
import kware.apps.mobigen.integration.dto.response.rawdata.RawdataView;
import kware.apps.mobigen.integration.dto.response.recommendation.RecommendationList;
import kware.apps.mobigen.integration.dto.response.relation.RelationsList;
import kware.apps.mobigen.mobigen.dto.request.recommendation.SearchRecommendationListRequest;
import kware.apps.mobigen.mobigen.dto.response.ApiResponse;
import kware.apps.mobigen.mobigen.dto.response.common.MetadataResultResponse;
import kware.apps.mobigen.mobigen.dto.response.metadata.MetadataFilePreviewResponse;
import kware.apps.mobigen.mobigen.dto.response.rawdata.RawdataListItemResponse;
import kware.apps.mobigen.mobigen.dto.response.recommendation.RecommendationListResponse;
import kware.apps.mobigen.mobigen.dto.response.recommendation.RecommendationsResponse;
import kware.apps.mobigen.mobigen.dto.response.relation.RelatedMetadataResponse;
import kware.apps.mobigen.mobigen.service.MobigenExternalApiService;
import kware.apps.thirdeye.mobigen.approveddataset.service.CetusApprovedDatasetService2;
import kware.apps.thirdeye.mobigen.category.dto.request.SearchCategory;
import kware.apps.thirdeye.mobigen.category.dto.response.CategoryList;
import kware.apps.thirdeye.mobigen.category.service.CetusDatasetCategoryService;
import kware.apps.thirdeye.mobigen.datasetfile.domain.file.CetusDatasetFile;
import kware.apps.thirdeye.mobigen.datasetfile.dto.request.DeleteDatasetFile;
import kware.apps.thirdeye.mobigen.datasetfile.dto.request.SearchDatasetFile;
import kware.apps.thirdeye.mobigen.datasetfile.dto.request.SearchDatasetFilePage;
import kware.apps.thirdeye.mobigen.datasetfile.dto.request.SearchDatasetFileView;
import kware.apps.thirdeye.mobigen.datasetfile.dto.response.CetusDatasetFileView;
import kware.apps.thirdeye.mobigen.datasetfile.enumcd.DataFileTpCd;
import kware.apps.thirdeye.mobigen.datasetfile.service.CetusDatasetFileService;
import kware.apps.thirdeye.mobigen.mobigenregistrant.dto.response.MobigenRegistrantView;
import kware.apps.thirdeye.mobigen.mobigenregistrant.service.CetusMobigenRegistrantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/**
* @fileName     DatasetSendService
* @author       dahyeon
* @version      1.0.0
* @date         2025-10-23
* @summary      { kware 백엔드 <-> 모비젠 백엔드 } 접근을 위한 서비스
**/

@Slf4j
@Service
@RequiredArgsConstructor
public class DatasetService {

    private final MobigenExternalApiService apiService;
    private final CetusDatasetFileService datasetFileService;
    private final CetusMobigenRegistrantService registrantService;
    private final CetusApprovedDatasetService2 approvedDatasetService2;

    // 추후 삭제
    private final CetusMobigenDatasetService mobigenDatasetService;
    private final CetusMobigenDatasetTagService datasetTagService;
    private final CetusMobigenTagService tagService;
    private final CetusDatasetCategoryService categoryService;

    private Path convertToPath(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return null; // 파일이 없을 경우 null 반환
        }
        Path tempDir = Files.createTempDirectory("upload_");
        Path filePath = tempDir.resolve(file.getOriginalFilename());
        file.transferTo(filePath);
        return filePath;
    }

    @Transactional
    public void savePackageDataset( SavePackageDataset request, MultipartFile packageFileData ) throws IOException {

        /*Path path = this.convertToPath(packageFileData);
        ApiResponse<PackageImportResponse> apiResponse = apiService.packageImport(path);*/

        // 1. save mobigen data
        // todo 삭제..
        String title = "random package title_" + StringUtil.random(3);
        CetusMobigenDataset bean = new CetusMobigenDataset(title, "{}");
        mobigenDatasetService.insert(bean);
        Long metadataId = bean.getUid(); //apiResponse.getResult().getMetadata().getMetadata_id();
        log.info(">>>> [savePackageDataset] metadataId : {} ", metadataId);

        String rawdataId = "raw_" + metadataId + "_" + StringUtil.random(3); //apiResponse.getResult().getRawdata().getRawdata_id();

        // 1. save [PACKAGE] data file
        CetusDatasetFile metaFile = request.getPackageFile();
        metaFile.setPackageDto(Long.toString(metadataId), rawdataId, DataFileTpCd.PACKAGE);
        datasetFileService.processAddFile(metaFile);

        // 3. 모비젠 등록자 정보 저장
        registrantService.saveMobigenRegistrant(metadataId); //apiResponse.getResult().getMetadata().getMetadata_id();
    }

    @Transactional(readOnly = true)
    public ApiResponse<MetadataFilePreviewResponse> previewMetadata(MultipartFile file) throws IOException {
        log.info(">>>> [previewMetadata] file : {} ", file.getOriginalFilename());
        Path path = this.convertToPath(file);
        /*ApiResponse<MetadataFilePreviewResponse> apiResponse = apiService.previewMetadataFile(path);*/
        return null;
    }

    @Transactional
    public void createMetadata( SaveMetadata request, MultipartFile realFileData ) throws IOException {

        /*CreateMetadataRequest metadataRequest = new CreateMetadataRequest(new CreateMetadataRequest.CreateMetadataFieldRequest(request.getTitle()));
        Path realFilePath = this.convertToPath(realFileData);
        ApiResponse<CreateMetadataResponse> apiResponse = apiService.createMetadata(metadataRequest, realFilePath);*/

        // 1. save mobigen data
        // todo 삭제..
        CetusMobigenDataset bean = new CetusMobigenDataset(request);
        mobigenDatasetService.insert(bean);
        Long metadataId = bean.getUid();    //apiResponse.getResult().getMetadata().getMetadata_id();
        log.info(">>>> [createMetadata] metadataId : {} ", metadataId);

        String rawdataId = "raw_" + metadataId + "_" + StringUtil.random(3); //apiResponse.getResult().getRawdata().getRawdata_id();

        // 1. save metadata file
        CetusDatasetFile metaFile = request.getMetaFile();
        metaFile.setMetadataDto(Long.toString(metadataId), DataFileTpCd.METADATA);
        datasetFileService.processAddFile(metaFile);

        if( realFileData != null && !realFileData.isEmpty() ) {
            // 2. save realdata file
            CetusDatasetFile realFile = request.getRealFile();
            realFile.setRawdataDto(Long.toString(metadataId), rawdataId, DataFileTpCd.RAWDATA);
            datasetFileService.processAddFile(realFile);
        }

        // 3. save tag
        datasetTagService.saveDatasetTag(request.getTags(), metadataId);

        // 4. 모비젠 등록자 정보 저장
        registrantService.saveMobigenRegistrant(metadataId);
    }

    @Transactional
    public void updateMetadata( ChangeMetadata request, MultipartFile realFileData ) throws IOException {

        Long metadataId = request.getMetadataId();

        /*ChangeMetadataRequest changeRequest = new ChangeMetadataRequest(
                Long.toString(metadataId), new ChangeMetadataRequest.ChangeMetadataFieldRequest(request.getTitle())
        );
        ApiResponse<ChangeMetadataResponse> apiResponse1 = apiService.changeMetadata(changeRequest);*/

        // >> update mobigen data
        CetusMobigenDataset bean = new CetusMobigenDataset(metadataId, request);
        mobigenDatasetService.update(bean);

        // >> save tag
        datasetTagService.saveDatasetTag(request.getTags(), metadataId);


        // 2. if not empty upload real-file-data
        if( realFileData != null && !realFileData.isEmpty() ) {
            /*UploadRawdataRequest uploadRequest = new UploadRawdataRequest(Long.toString(metadataId), realFileData.getContentType());
            Path realFilePath = this.convertToPath(realFileData);
            ApiResponse<UploadRawdataResponse> apiResponse2 = apiService.uploadRawdata(uploadRequest, realFilePath);*/
            String rawdataId = "raw_" + metadataId + "_" + StringUtil.random(3); //apiResponse2.getResult().getRawdata_id();

            CetusDatasetFile realFile = request.getRealFile();
            realFile.setMetadataId(Long.toString(metadataId));
            realFile.setDataTpCd(DataFileTpCd.RAWDATA.name());
            realFile.setRawdataId(rawdataId);
            datasetFileService.processAddFile(realFile);
        }

        MetadataView metadataView = this.viewMetadata(new SearchMetadataView(Long.toString(metadataId)));
        approvedDatasetService2.updateDatasetSearchData(metadataId, metadataView.getTitle(), metadataView.getTags());
    }

    @Transactional
    public void deleteMetadatas(DeleteMetadatas request) {

        List<String> uidList = request.getUids().stream().map(String::valueOf).collect(Collectors.toList());
       /*DeleteMetadatasRequest deleteRequest = new DeleteMetadatasRequest(uidList);
        ApiResponse<DeleteMetadataResponse> apiResponse = apiService.deleteSeveralMetadata(deleteRequest);*/

        for (String metadataId: uidList) {
            // todo 추후 삭제
            CetusMobigenDataset bean = new CetusMobigenDataset(Long.parseLong(metadataId));
            mobigenDatasetService.deleteMobigenDataset(bean);

            // {metadataId} 데이터셋이 kware 포탈 시스템에서 관리중인 값이라면 포탈 시스템에서도 삭제 (논리삭제)
            mobigenDatasetService.ifExistDeleteApprovedDataset(Long.parseLong(metadataId));

            // 해당 파일 정보도 삭제
            datasetFileService.processDelFile(DeleteDatasetFile.deleteMetadata(metadataId));
        }
    }

    @Transactional
    public void deleteRawdatas(DeleteRawdatas request) {

        /*DeleteRawdatasRequest deleteRequest = new DeleteRawdatasRequest(Long.toString(request.getMetadataId()), request.getRawdataIds());
        ApiResponse<DeleteRawdatasResponse> apiResponse = apiService.deleteSeveralRawdata(deleteRequest);
        int deletedCount = apiResponse.getResult().getDeleted_count();*/

        for (String rawdataId: request.getRawdataIds()) {
            datasetFileService.processDelFile(DeleteDatasetFile.deleteRawdata(rawdataId));
        }
    }

    @Transactional
    public void updateRawdata(ChangeRawdata request) {
        /*ChangeRawdataRequest updateRequest = new ChangeRawdataRequest(request.getRawdataId(), new ChangeRawdataRequest.ChangeRawdataFieldRequest(request.getDescription(), request.getTags()));
        ApiResponse<ChangeRawdataResponse> apiResponse = apiService.changeRawdata(updateRequest);*/
    }

    @Transactional(readOnly = true)
    public MetadataView viewMetadata( SearchMetadataView search ) {

        String metadataId = search.getMetadataId();

        /*SearchMetadataViewRequest searchRequest = new SearchMetadataViewRequest(metadataId);
        ApiResponse<ViewMetadataResponse> apiResponse = apiService.findMetadataById(searchRequest);
        ViewMetadataResponse result = apiResponse.getResult();*/

        // 0. 우선은 데이터 원본 정보들을 kware 포탈 시스템에서 가져오기
        MetadataView metadataView = mobigenDatasetService.findMobigenDatasetByMetadataId(Long.parseLong(metadataId));

        // 1. 모비젠으로부터 얻어온 데이터 정보들로 세팅
        metadataView.setMetadataResponse(null);

        // 2. 데이터 등록자가 있다면 세팅
        MobigenRegistrantView registrantView = registrantService.findMobigenRegistrant(Long.parseLong(metadataId));
        metadataView.setRegistrantId((registrantView != null) ? registrantView.getRegistrantId() : null);

        // 3. 데이터에 대한 파일 등록 정보가 KWARE 포탈 시스템에 있다면 세팅
        List<CetusDatasetFileView> packagedataFiles = datasetFileService.findDataFileList(new SearchDatasetFile(metadataId, null, DataFileTpCd.PACKAGE.name()));
        metadataView.setPackagedataFile((packagedataFiles != null && !packagedataFiles.isEmpty()) ? packagedataFiles.get(0) : null);

        List<CetusDatasetFileView> metadataFiles = datasetFileService.findDataFileList(new SearchDatasetFile(metadataId, null, DataFileTpCd.METADATA.name()));
        metadataView.setMetadataFile((metadataFiles != null && !metadataFiles.isEmpty()) ? metadataFiles.get(0) : null);

        List<CetusDatasetFileView> rawdataFiles = datasetFileService.findDataFileList(new SearchDatasetFile(metadataId, null, DataFileTpCd.RAWDATA.name()));
        metadataView.setRawdataFiles(rawdataFiles);

        // 4. 데이터에 대한 태그 정보 (추후 삭제할 예정)
        List<TagList> tags = datasetTagService.findMobigenDatasetTagListByMetadataId(Long.parseLong(metadataId));
        metadataView.setTags(tags);

        return metadataView;
    }

    @Transactional(readOnly = true)
    public RawdataView viewRawdata(SearchRawdataView search) {

        String metadataId = search.getMetadataId();
        String rawdataId = search.getRawdataId();
        RawdataView rawdataView = new RawdataView(metadataId, rawdataId);

        /*SearchRawdataViewRequest searchRequest = new SearchRawdataViewRequest(metadataId, rawdataId);
        ApiResponse<ViewRawdataResponse> apiResponse = apiService.findRawdataById(searchRequest);
        ViewRawdataResponse result = apiResponse.getResult();*/

        // mobigen 에서 얻은 정보 세팅
        rawdataView.setRawdataResponse(null);

        // 우선은 원본데이터 파일 정보들 임시 세팅
        SearchDatasetFileView fileViewSearch = new SearchDatasetFileView(metadataId, rawdataId);
        CetusDatasetFileView rawdataFile = datasetFileService.findRawdataFileView(fileViewSearch);
        rawdataView.setRawdataFile(rawdataFile);

        return rawdataView;
    }

    @Transactional
    public Page<MetadataList> pageMetadata(SearchMetadataPage search) {

        /*SearchMetadataListRequest pageSearchRequest = new SearchMetadataListRequest(
                search.getPublisher(), search.getTheme(), search.getPageNumber(), search.getSize(),
                search.getDateRangeStart(), search.getDateRangeEnd(), search.getSortOrder()
        );
        ApiResponse<MetadataListResponse> apiResponse = apiService.findMetadataList(pageSearchRequest);
        MetadataListResponse metadataListResponse = apiResponse.getResult();*/

        int totalCount = mobigenDatasetService.findAllMobigenDatasetListCount(); //metadataListResponse.getTotal_count();
        int page = search.getPageNumber(); //metadataListResponse.getPage();
        int limit = search.getSize(); //metadataListResponse.getLimit();
        /*List<MetadataResultResponse> items = //metadataListResponse.getItems();*/
        List<MetadataResultResponse> items = mobigenDatasetService.findAllMobigenDatasetList(
                new SearchMobigenDataset(search.getPageNumber(), search.getSize())
        );

        List<MetadataList> metadataList = new ArrayList<>();
        items.forEach(data -> {

            Long metadataId = data.getUid();
            MetadataList metadataDto = new MetadataList(Long.toString(metadataId));
            metadataDto.setMetadataView(data);

            MobigenRegistrantView registrantView = registrantService.findMobigenRegistrant(metadataId);
            if(registrantView != null) metadataDto.setRegistrantInfo(registrantView.isRegistered(), registrantView.getRegistrantId());
            else metadataDto.setRegistrantInfo(false, null);

            if(search.getApprovedIds() != null && search.getApprovedIds().length != 0) {
                boolean isApproved = Arrays.asList(search.getApprovedIds()).contains(metadataId);
                metadataDto.setApproved(isApproved);
            }

            metadataList.add(metadataDto);
        });
        return new Page<>(metadataList, totalCount, new Pageable(limit, page, limit));
    }

    @Transactional(readOnly = true)
    public Page<RawdataList> pageRawdata(SearchRawdataPage search) {

        String metadataId = search.getMetadataId();

        /*SearchRawdataListRequest searchRequest = new SearchRawdataListRequest(metadataId, new PaginationRequest(search.getPageNumber(), search.getSize()));
        ApiResponse<RawdataListResponse> apiResponse = apiService.findRawdataList(searchRequest);
        RawdataListResponse rawdataListResponse = apiResponse.getResult();*/

        SearchDatasetFilePage searchFilePage = new SearchDatasetFilePage(
                search.getMetadataId(), null, DataFileTpCd.RAWDATA.name(),
                search.getPageNumber(), search.getSize());

        int totalCount = datasetFileService.findDataFilePageCount(searchFilePage); //rawdataListResponse.getTotal_count();
        /*List<RawdataList> items = rawdataListResponse.getItems();*/
        List<RawdataListItemResponse> items = datasetFileService.findDataFilePage(searchFilePage);

        List<RawdataList> rawdataList = new ArrayList<>();
        items.forEach(item -> {

            String rawdataId = item.getRawdataId(); //item.getRawdata_id();

            RawdataList rawdata = new RawdataList(metadataId, rawdataId);
            rawdata.setRawdataView(item);

            SearchDatasetFileView fileViewSearch = new SearchDatasetFileView(metadataId, rawdataId);
            CetusDatasetFileView rawdataFile = datasetFileService.findRawdataFileView(fileViewSearch);
            rawdata.setRawdataFile(rawdataFile);

            rawdataList.add(rawdata);
        });

        return new Page<>(rawdataList, totalCount, new Pageable(search.getSize(), search.getPageNumber(), search.getSize()));
    }

    @Transactional(readOnly = true)
    public Page<RelationsList> pageRelations(SearchRelationsPage search) {

        /*SearchRelationListRequest listRequest = new SearchRelationListRequest(
                search.getPublisher(), search.getTheme(), search.getScoreMin(), search.getScoreMax(),
                search.getPageNumber(), search.getSize(), search.getSortOrder()
        );
        ApiResponse<RelationListResponse> apiResponse = apiService.findRelationDataList(listRequest);
        RelationListResponse relationListResponse = apiResponse.getResult();*/

        int totalCount =  mobigenDatasetService.findAllRelationMobigenDatasetListCount(); //relationListResponse.getTotal_count();
        int page =  search.getPageNumber(); //relationListResponse.getPage();
        int limit = search.getSize(); //relationListResponse.getLimit();
        /*List<RelatedMetadataResponse> items = relationListResponse.getItems();*/
        List<RelatedMetadataResponse> items = mobigenDatasetService.findAllRelationMobigenDatasetList(
                new SearchMobigenDataset(search.getPageNumber(), search.getSize())
        );

        List<RelationsList> relationsList = new ArrayList<>();
        items.forEach(data -> {
            Long metadataId = data.getUid();
            RelationsList relationDto = new RelationsList(Long.toString(metadataId));
            relationDto.setRelationMetadata(data);
            relationsList.add(relationDto);
        });
        return new Page<>(relationsList, totalCount, new Pageable(limit, page, limit));
    }

    @Transactional(readOnly = true)
    public Page<RecommendationList> pageRecommendation(SearchRecommendationPage search) {

        SearchRecommendationListRequest pageRequest = new SearchRecommendationListRequest(
                search.getRecommendationType(), search.getPublisher(), search.getTheme(),
                search.getPageNumber(), search.getSize(), search.getSortOrder()
        );
        ApiResponse<RecommendationListResponse> apiResponse = apiService.findRecommendationDataList(pageRequest);
        RecommendationListResponse recommendationListResponse = apiResponse.getResult();

        int totalCount =  mobigenDatasetService.findAllRecommendationMobigenDatasetListCount(); //recommendationListResponse.getTotal_count();
        int page =  search.getPageNumber(); //recommendationListResponse.getPage();
        int limit = search.getSize(); //recommendationListResponse.getLimit();
        String metadata_id = null; //recommendationListResponse.getMetadata_id();

        List<RecommendationsResponse> items = mobigenDatasetService.findAllRecommendationMobigenDatasetList(
                new SearchMobigenDataset(search.getPageNumber(), search.getSize())
        );

        List<RecommendationList> recommendationList = new ArrayList<>();
        items.forEach(data -> {
            Long metadataId = data.getUid();
            RecommendationList recommendationDto = new RecommendationList(Long.toString(metadataId));
            recommendationDto.setRecommendationResponse(data);
            recommendationList.add(recommendationDto);
        });
        return new Page<>(recommendationList, totalCount, new Pageable(limit, page, limit));
    }

    @Transactional
    public ResponseEntity downloadPackage(DownloadPackageDataset request) {

        // todo API 체크해보고 더 로직 구현하기
        /*PackageExportRequest exportRequest = new PackageExportRequest(request.getMetadataId(), request.getRawdataIds());
        ApiResponse<PackageExportResponse> apiResponse = apiService.packageExport(exportRequest);*/

        return null;
    }

    @Transactional
    public ResponseEntity downloadMetadata(DownloadMetadata request, HttpServletRequest req) throws IOException {

        /*DownloadMetadataFileRequest downloadRequest = new DownloadMetadataFileRequest(request.getMetadataId());
        apiService.downloadMetadataFile(downloadRequest);*/

        // todo 추후에는 > 로그insert & down_count++ 만 진행

        return datasetFileService.downloadMetaFile(request.getMetadataFileId(), req);
    }

    @Transactional
    public ResponseEntity downloadRawdata(DownloadRawdata request, HttpServletRequest req) throws IOException {

        /*DownloadRawdataRequest downloadRequest = new DownloadRawdataRequest(request.getMetadataId(), request.getRawdataId());
        apiService.downloadRawdata(downloadRequest);*/

        return datasetFileService.downloadMetaFile(request.getRawdataFileId(), req);
    }

    @Transactional(readOnly = true)
    public MetaKeyList findMetaKeyList() {
        /*ApiResponse<MetaKeysListResponse> apiResponse = apiService.findMetaKeysList();
        MetaKeysListResponse result = apiResponse.getResult();
        List<String> filterList = result.getFilters();
        return new MetaKeyList(filterList);*/
        return new MetaKeyList(Arrays.asList("category", "tag", "date"));
    }

    @Transactional(readOnly = true)
    public MetaKeyValueList findMetaKeyValueList(SearchMetaValues search) {
        /*SearchMetaValuesRequest searchKeyValueRequest = new SearchMetaValuesRequest(search.getKey());
        ApiResponse<MetaValuesListResponse> apiResponse = apiService.findMetaValuesList(searchKeyValueRequest);
        MetaValuesListResponse valuesListResponse = apiResponse.getResult();*/
        String filter = search.getKey(); //valuesListResponse.getFilter();
        List<String> values = new ArrayList<>(); //valuesListResponse.getValues();

        Map<Long, String> maps = new HashMap<>();
        MetaKeyValueList valueList = new MetaKeyValueList(filter);

        if("tag".equals(search.getKey())) {
            List<TagList> mobigenTagList = tagService.findMobigenTagList();
            maps = mobigenTagList.stream()
                    .collect(Collectors.toMap(TagList::getTagUid, TagList::getTagNm));
            valueList.setMaps(maps);

        } else if("category".equals(search.getKey())) {
            List<CategoryList> categoryList = categoryService.findDatasetCategoryList(new SearchCategory(UserUtil.getUserWorkplaceUid()));
            maps = categoryList.stream()
                    .collect(Collectors.toMap(CategoryList::getCategoryUid, CategoryList::getCategoryNm));
            valueList.setMaps(maps);

        }

        return valueList;
    }
}
