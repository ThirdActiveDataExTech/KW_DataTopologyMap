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
import kware.apps.thirdeye.mobigen.approveddataset.dto.request.SearchMetadataApproved;
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


    /**
     * @method      [1] savePackageDataset
     * @author      dahyeon
     * @date        2025-10-29
     * @deacription 패키지 데이터셋 등록
     *
     * @param request           KWARE 포탈 시스템에  파일 정보 저장을 위한 DTO
     * @param packageFileData   모비젠 측에 전달할 파일
    **/
    @Transactional
    public void savePackageDataset( SavePackageDataset request, MultipartFile packageFileData ) throws IOException {

        /*Path path = this.convertToPath(packageFileData);
        ApiResponse<PackageImportResponse> apiResponse = apiService.packageImport(path);*/

        // ## save mobigen data
        // todo 추후 연결후 삭제..
        String title = "random package title_" + StringUtil.random(3);
        CetusMobigenDataset bean = new CetusMobigenDataset(title, "{}");
        mobigenDatasetService.insert(bean);
        Long metadataId = bean.getUid(); // metadataId = apiResponse.getResult().getMetadata().getMetadata_id();
        log.info("======================= [1] Save Package Metadata : {} ===========================", metadataId);

        // 1. save [PACKAGE] data file
        String rawdataId = "raw_" + metadataId + "_" + StringUtil.random(3); // rawdataId = apiResponse.getResult().getRawdata().getRawdata_id();
        CetusDatasetFile metaFile = request.getPackageFile();
        metaFile.setPackageDto(Long.toString(metadataId), rawdataId, DataFileTpCd.PACKAGE);
        datasetFileService.processAddFile(metaFile);

        // 2. 모비젠 등록자 정보 저장
        registrantService.saveMobigenRegistrant(Long.toString(metadataId)); // metadataId = apiResponse.getResult().getMetadata().getMetadata_id();
    }

    /**
     * @method      [2] previewMetadata
     * @author      dahyeon
     * @date        2025-10-29
     * @deacription 메타데이터 파일 정보 미리보기
     *
     * @param file  미리보기로 읽어올 메타데이터 파일
     *              => 해당 파일 내부의 파일 정보를 읽어서 응답 결과로 보내준다.
    **/
    @Transactional(readOnly = true)
    public ApiResponse<MetadataFilePreviewResponse> previewMetadata(MultipartFile file) throws IOException {
        log.info("======================= [2] Preview Read Metadata : {} ===========================", file.getOriginalFilename());
        Path path = this.convertToPath(file);
        /*ApiResponse<MetadataFilePreviewResponse> apiResponse = apiService.previewMetadataFile(path);*/
        return null;
    }

    /**
     * @method      [3] createMetadata
     * @author      dahyeon
     * @date        2025-10-29
     * @deacription 메타데이터 생성
     *
     * @param request       생성할 메타데이터 정보 DTO
     * @param realFileData  함께 업로드를 진행할 원본데이터 파일 ( 선택적 )
     *                      => 메타데이터(Metadata) 하위로 원본데이터(RealData)가 들어간다.
    **/
    @Transactional
    public void createMetadata( SaveMetadata request, MultipartFile realFileData ) throws IOException {

        /*CreateMetadataRequest metadataRequest = new CreateMetadataRequest(new CreateMetadataRequest.CreateMetadataFieldRequest(request.getTitle()));
        Path realFilePath = this.convertToPath(realFileData);
        ApiResponse<CreateMetadataResponse> apiResponse = apiService.createMetadata(metadataRequest, realFilePath);*/

        // ## save mobigen data
        // todo 추후 연결후 삭제..
        CetusMobigenDataset bean = new CetusMobigenDataset(request);
        mobigenDatasetService.insert(bean);
        Long metadataId = bean.getUid();    // metadataId = apiResponse.getResult().getMetadata().getMetadata_id();
        log.info("======================= [3-1] Create Metadata : {} ===========================", metadataId);

        // 1. save metadata file
        CetusDatasetFile metaFile = request.getMetaFile();
        metaFile.setMetadataDto(Long.toString(metadataId), DataFileTpCd.METADATA);
        datasetFileService.processAddFile(metaFile);

        // 2. save realdata file
        if( realFileData != null && !realFileData.isEmpty() ) {
            String rawdataId = "raw_" + metadataId + "_" + StringUtil.random(3); // rawdataId = apiResponse.getResult().getRawdata().getRawdata_id();
            CetusDatasetFile realFile = request.getRealFile();
            realFile.setRawdataDto(Long.toString(metadataId), rawdataId, DataFileTpCd.RAWDATA);
            datasetFileService.processAddFile(realFile);
            log.info("======================= [3-2] Upload Realdata : {} ===========================", rawdataId);
        }

        // ## save tag
        // todo 추후 연결후 삭제..
        datasetTagService.saveDatasetTag(request.getTags(), Long.toString(metadataId));

        // 3. 모비젠 등록자 정보 저장
        registrantService.saveMobigenRegistrant(Long.toString(metadataId));
    }

    /**
     * @method      [4] updateMetadata
     * @author      dahyeon
     * @date        2025-10-29
     * @deacription 메타데이터 정보 수정
     *
     * @param request       수정할 메타데이터 DTO
     * @param realFileData  함께 업로드를 진행할 원본데이터 파일 ( 선택적 )
    **/
    @Transactional
    public void updateMetadata( ChangeMetadata request, MultipartFile realFileData ) throws IOException {

        String metadataId = request.getMetadataId();

        // 1. 메타데이터 정보 수정
        /*ChangeMetadataRequest changeRequest = new ChangeMetadataRequest(
                Long.toString(metadataId), new ChangeMetadataRequest.ChangeMetadataFieldRequest(request.getTitle())
        );
        ApiResponse<ChangeMetadataResponse> apiResponse1 = apiService.changeMetadata(changeRequest);*/

        // ## update mobigen data
        // todo 추후 연결후 삭제..
        CetusMobigenDataset bean = new CetusMobigenDataset(metadataId, request);
        mobigenDatasetService.update(bean);
        log.info("======================= [4-1] Update Metadata : {} ===========================", metadataId);

        // ## update data tag
        // todo 추후 연결후 삭제..
        datasetTagService.saveDatasetTag(request.getTags(), metadataId);

        // 2. 원본데이터 파일 업로드
        if( realFileData != null && !realFileData.isEmpty() ) {
            
            /*UploadRawdataRequest uploadRequest = new UploadRawdataRequest(Long.toString(metadataId), realFileData.getContentType());
            Path realFilePath = this.convertToPath(realFileData);
            ApiResponse<UploadRawdataResponse> apiResponse2 = apiService.uploadRawdata(uploadRequest, realFilePath);*/
            
            String rawdataId = "raw_" + metadataId + "_" + StringUtil.random(3); //apiResponse2.getResult().getRawdata_id();
            CetusDatasetFile realFile = request.getRealFile();
            realFile.setMetadataId(metadataId);
            realFile.setDataTpCd(DataFileTpCd.RAWDATA.name());
            realFile.setRawdataId(rawdataId);
            datasetFileService.processAddFile(realFile);
            log.info("======================= [4-2] Upload Realdata : {} ===========================", rawdataId);
        }

        // 3. 만일 수정된 메타데이터 정보가 진열관리 중이라면, search_data 업데이트
        // todo 추후 체크 필요 
        MetadataView metadataView = this.viewMetadata(
                new SearchMetadataView(metadataId), false, false, true
        );
        approvedDatasetService2.updateDatasetSearchData(metadataId, metadataView.getTitle(), metadataView.getTags());
        log.info("======================= [4-3] Update Search Data : {} ===========================", metadataId);
    }

    /**
     * @method      [5] deleteMetadatas
     * @author      dahyeon
     * @date        2025-10-29
     * @deacription 메타데이터 여러건 삭제
     *
     * @param request 삭제할 메타데이터 ID 리스트 DTO
    **/
    @Transactional
    public void deleteMetadatas(DeleteMetadatas request) {

        // 1. 메타데이터 여러건 삭제
        List<String> uidList = request.getUids().stream().map(String::valueOf).collect(Collectors.toList());
        log.info("======================= [5] Delete Metadata's : {} ===========================", uidList);
       /*DeleteMetadatasRequest deleteRequest = new DeleteMetadatasRequest(uidList);
        ApiResponse<DeleteMetadataResponse> apiResponse = apiService.deleteSeveralMetadata(deleteRequest);*/

        for (String metadataId: uidList) {

            // todo 추후 삭제
            CetusMobigenDataset bean = new CetusMobigenDataset(Long.parseLong(metadataId));
            mobigenDatasetService.deleteMobigenDataset(bean);

            // 2. {metadataId} 데이터셋이 kware 포탈 시스템에서 관리중인 값이라면 포탈 시스템에서도 삭제 (논리삭제)
            mobigenDatasetService.ifExistDeleteApprovedDataset(metadataId);

            // 3. 해당 파일 정보도 삭제
            datasetFileService.processDelFile(DeleteDatasetFile.deleteMetadata(metadataId));
        }
    }

    /**
     * @method      [6] deleteRawdatas
     * @author      dahyeon
     * @date        2025-10-29
     * @deacription 원본데이터 여러건 삭제
     *
     * @param request 삭제할 원본데이터 ID 리스트 DTO
    **/
    @Transactional
    public void deleteRawdatas(DeleteRawdatas request) {

        // 1. 원본데이터 여러건 삭제
        log.info("======================= [6] Delete Rawdata's : {} ===========================", request.getRawdataIds());
        /*DeleteRawdatasRequest deleteRequest = new DeleteRawdatasRequest(Long.toString(request.getMetadataId()), request.getRawdataIds());
        ApiResponse<DeleteRawdatasResponse> apiResponse = apiService.deleteSeveralRawdata(deleteRequest);
        int deletedCount = apiResponse.getResult().getDeleted_count();*/

        // 2. 원본데이터 파일 정보도 삭제
        for (String rawdataId: request.getRawdataIds()) {
            datasetFileService.processDelFile(DeleteDatasetFile.deleteRawdata(rawdataId));
        }
    }

    /**
     * @method      [7] updateRawdata
     * @author      dahyeon
     * @date        2025-10-29
     * @deacription 원본데이터 정보 수정
     *
     * @param request 수정할 원본데이터 정보 DTO
    **/
    @Transactional
    public void updateRawdata(ChangeRawdata request) {
        log.info("======================= [7] Update Rawdata : {} ===========================", request.getRawdataId());
        /*ChangeRawdataRequest updateRequest = new ChangeRawdataRequest(
                request.getRawdataId(),
                request.getMetadataId(),
                new ChangeRawdataRequest.ChangeRawdataFieldRequest(request.getDescription(), request.getTags())
        );
        ApiResponse<ChangeRawdataResponse> apiResponse = apiService.changeRawdata(updateRequest);*/
    }

    /**
     * @method      [8] viewMetadata
     * @author      dahyeon
     * @date        2025-10-29
     * @deacription 메타데이터 상세 정보 조회
     *
     * @param search 메타데이터 ID
    **/
    @Transactional(readOnly = true)
    public MetadataView viewMetadata( SearchMetadataView search, boolean useRegistrant, boolean useFile, boolean useTag ) {

        log.info("======================= [8] View Metadata : {} ===========================", search.getMetadataId());
        String metadataId = search.getMetadataId();

        /*SearchMetadataViewRequest searchRequest = new SearchMetadataViewRequest(metadataId);
        ApiResponse<ViewMetadataResponse> apiResponse = apiService.findMetadataById(searchRequest);
        ViewMetadataResponse result = apiResponse.getResult();*/

        // ## 우선은 데이터 원본 정보들을 kware 포탈 시스템에서 가져오기
        // todo 추후 연결후 삭제..
        MetadataView metadataView = mobigenDatasetService.findMobigenDatasetByMetadataId(metadataId);

        // 1. 모비젠으로부터 얻어온 데이터 정보들로 세팅
        metadataView.setMetadataResponse(null);

        // 2. 데이터 등록자가 있다면 세팅
        if(useRegistrant) {
            MobigenRegistrantView registrantView = registrantService.findMobigenRegistrant(metadataId);
            metadataView.setRegistrantId((registrantView != null) ? registrantView.getRegistrantId() : null);
        }

        // 3. 데이터에 대한 파일 등록 정보가 KWARE 포탈 시스템에 있다면 세팅
        if(useFile) {
            List<CetusDatasetFileView> packagedataFiles = datasetFileService.findDataFileList(new SearchDatasetFile(metadataId, null, DataFileTpCd.PACKAGE.name()));
            metadataView.setPackagedataFile((packagedataFiles != null && !packagedataFiles.isEmpty()) ? packagedataFiles.get(0) : null);

            List<CetusDatasetFileView> metadataFiles = datasetFileService.findDataFileList(new SearchDatasetFile(metadataId, null, DataFileTpCd.METADATA.name()));
            metadataView.setMetadataFile((metadataFiles != null && !metadataFiles.isEmpty()) ? metadataFiles.get(0) : null);

            List<CetusDatasetFileView> rawdataFiles = datasetFileService.findDataFileList(new SearchDatasetFile(metadataId, null, DataFileTpCd.RAWDATA.name()));
            metadataView.setRawdataFiles(rawdataFiles);
        }

        // 4. 데이터에 대한 태그 정보 (추후 삭제할 예정)
        // todo 추후 연결후 삭제..
        if(useTag) {
            List<TagList> tags = datasetTagService.findMobigenDatasetTagListByMetadataId(metadataId);
            metadataView.setTags(tags);
        }

        return metadataView;
    }

    /**
     * @method      [9] viewRawdata
     * @author      dahyeon
     * @date        2025-10-29
     * @deacription 원본데이터 정보 상세 조회
     *
     * @param search 원본데이터, 메타데이터 ID
    **/
    @Transactional(readOnly = true)
    public RawdataView viewRawdata(SearchRawdataView search) {

        String metadataId = search.getMetadataId();
        String rawdataId = search.getRawdataId();
        RawdataView rawdataView = new RawdataView(metadataId, rawdataId);
        log.info("======================= [9] View Rawdata : {} ===========================", rawdataId);

        /*SearchRawdataViewRequest searchRequest = new SearchRawdataViewRequest(metadataId, rawdataId);
        ApiResponse<ViewRawdataResponse> apiResponse = apiService.findRawdataById(searchRequest);
        ViewRawdataResponse result = apiResponse.getResult();*/

        // mobigen 에서 얻은 정보 세팅
        rawdataView.setRawdataResponse(null);

        // 우선은 원본데이터 파일 정보들 세팅
        SearchDatasetFileView fileViewSearch = new SearchDatasetFileView(metadataId, rawdataId);
        CetusDatasetFileView rawdataFile = datasetFileService.findRawdataFileView(fileViewSearch);
        rawdataView.setRawdataFile(rawdataFile);

        return rawdataView;
    }

    /**
     * @method      [10] pageMetadata
     * @author      dahyeon
     * @date        2025-10-29
     * @deacription 메타데이터 목록 조회 + 페이징
     *
     * @param search 메타데이터 목록 조회 요청 DTO
    **/
    @Transactional
    public Page<MetadataList> pageMetadata(SearchMetadataPage search) {

        log.info("======================= [10] Page Metadata ===========================");

        /*SearchMetadataListRequest pageSearchRequest = new SearchMetadataListRequest(
                search.getPublisher(), search.getTheme(), search.getPageNumber(), search.getSize(),
                search.getDateRangeStart(), search.getDateRangeEnd(), search.getSortOrder()
        );
        ApiResponse<MetadataListResponse> apiResponse = apiService.findMetadataList(pageSearchRequest);
        MetadataListResponse metadataListResponse = apiResponse.getResult();
        log.info(">> apiResponse.getCode() : {} ", apiResponse.getCode());
        log.info(">> apiResponse.getMessage() : {} ", apiResponse.getMessage());
        log.info(">> apiResponse.getResult() : {} ", apiResponse.getResult());*/

        int totalCount = mobigenDatasetService.findAllMobigenDatasetListCount(); // totalCount = metadataListResponse.getTotal_count();
        int page = search.getPageNumber(); // page  = metadataListResponse.getPage();
        int limit = search.getSize(); // limit = metadataListResponse.getLimit();
        /*List<MetadataResultResponse> items = // items = metadataListResponse.getItems();*/
        List<MetadataResultResponse> items = mobigenDatasetService.findAllMobigenDatasetList(
                new SearchMobigenDataset(search.getPageNumber(), search.getSize())
        );

        List<MetadataList> metadataList = new ArrayList<>();
        items.forEach(data -> {

            String metadataId = data.getUid();
            MetadataList metadataDto = new MetadataList(metadataId);
            metadataDto.setMetadataView(data);

            // 등록자 정보
            MobigenRegistrantView registrantView = registrantService.findMobigenRegistrant(metadataId);
            if(registrantView != null) metadataDto.setRegistrantInfo(registrantView.isRegistered(), registrantView.getRegistrantId());
            else metadataDto.setRegistrantInfo(false, null);

            // 이미 진열관리 중인 데이터셋인지 여부
            Boolean isApproved = approvedDatasetService2.findMetadataIsApproved(new SearchMetadataApproved(metadataId, UserUtil.getUserWorkplaceUid()));
            metadataDto.setApproved(isApproved);

            metadataList.add(metadataDto);
        });
        return new Page<>(metadataList, totalCount, new Pageable(limit, page, limit));
    }

    /**
     * @method      [11] pageRawdata
     * @author      dahyeon
     * @date        2025-10-29
     * @deacription 원본데이터 목록 조회 + 페이징
     *
     * @param search 원본데이터 목록 조회 요청 DTO
    **/
    @Transactional(readOnly = true)
    public Page<RawdataList> pageRawdata(SearchRawdataPage search) {

        String metadataId = search.getMetadataId();
        log.info("======================= [11] Page Rawdata : {} ===========================", metadataId);

        /*SearchRawdataListRequest searchRequest = new SearchRawdataListRequest(metadataId, new PaginationRequest(search.getPageNumber(), search.getSize()));
        ApiResponse<RawdataListResponse> apiResponse = apiService.findRawdataList(searchRequest);
        RawdataListResponse rawdataListResponse = apiResponse.getResult();*/

        SearchDatasetFilePage searchFilePage = new SearchDatasetFilePage(
                search.getMetadataId(), null, DataFileTpCd.RAWDATA.name(),
                search.getPageNumber(), search.getSize());

        int totalCount = datasetFileService.findDataFilePageCount(searchFilePage); // totalCount = rawdataListResponse.getTotal_count();
        /*List<RawdataListItemResponse> items = rawdataListResponse.getItems();*/
        List<RawdataListItemResponse> items = datasetFileService.findDataFilePage(searchFilePage);

        List<RawdataList> rawdataList = new ArrayList<>();
        items.forEach(item -> {

            String rawdataId = item.getRawdataId(); // rawdataId = item.getRawdata_id();

            RawdataList rawdata = new RawdataList(metadataId, rawdataId);
            rawdata.setRawdataView(item);

            SearchDatasetFileView fileViewSearch = new SearchDatasetFileView(metadataId, rawdataId);
            CetusDatasetFileView rawdataFile = datasetFileService.findRawdataFileView(fileViewSearch);
            rawdata.setRawdataFile(rawdataFile);

            rawdataList.add(rawdata);
        });
        return new Page<>(rawdataList, totalCount, new Pageable(search.getSize(), search.getPageNumber(), search.getSize()));
    }

    /**
     * @method      [12] pageRelations
     * @author      dahyeon
     * @date        2025-10-29
     * @deacription 관계 메타데이터 정보 목록 조회 + 페이징
     * 
     * @param search 관계데이터 목록 조회 요청 DTO
    **/
    @Transactional(readOnly = true)
    public Page<RelationsList> pageRelations(SearchRelationsPage search) {

        log.info("======================= [12] Relation Metadata ===========================");

        /*SearchRelationListRequest listRequest = new SearchRelationListRequest(
                search.getPublisher(), search.getTheme(), search.getScoreMin(), search.getScoreMax(),
                search.getPageNumber(), search.getSize(), search.getSortOrder()
        );
        ApiResponse<RelationListResponse> apiResponse = apiService.findRelationDataList(listRequest);
        RelationListResponse relationListResponse = apiResponse.getResult();*/

        int totalCount =  mobigenDatasetService.findAllRelationMobigenDatasetListCount(); // totalCount = relationListResponse.getTotal_count();
        int page =  search.getPageNumber(); // page = relationListResponse.getPage();
        int limit = search.getSize(); // limit = relationListResponse.getLimit();
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

    /**
     * @method      [13] pageRecommendation
     * @author      dahyeon
     * @date        2025-10-29
     * @deacription 추천 메타데이터 정보 목록 조회 + 페이징
     * 
     * @param search 추천데이터 목록 조회 요청 DTO
    **/
    @Transactional(readOnly = true)
    public Page<RecommendationList> pageRecommendation(SearchRecommendationPage search) {

        log.info("======================= [13] Recommendation Metadata ===========================");

        SearchRecommendationListRequest pageRequest = new SearchRecommendationListRequest(
                search.getRecommendationType(), search.getPublisher(), search.getTheme(),
                search.getPageNumber(), search.getSize(), search.getSortOrder()
        );
        ApiResponse<RecommendationListResponse> apiResponse = apiService.findRecommendationDataList(pageRequest);
        RecommendationListResponse recommendationListResponse = apiResponse.getResult();

        int totalCount =  mobigenDatasetService.findAllRecommendationMobigenDatasetListCount(); // totalCount = recommendationListResponse.getTotal_count();
        int page =  search.getPageNumber(); // page = recommendationListResponse.getPage();
        int limit = search.getSize(); // limit = recommendationListResponse.getLimit();
        String metadata_id = null; // metadata_id = recommendationListResponse.getMetadata_id();
        /*List<RecommendationsResponse> items = recommendationListResponse.getRecommendations();*/
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

    /**
     * @method      [14] downloadPackage
     * @author      dahyeon
     * @date        2025-10-29
     * @deacription 패키지 데이터셋 다운로드
     *
     * @param request 메타데이터 ID & 원본데이터 ID 목록
    **/
    @Transactional
    public ResponseEntity downloadPackage(DownloadPackageDataset request) {

        log.info("======================= [14] Download Package Data : {}({}) ===========================", request.getMetadataId(), request.getRawdataIds());

        // todo API 체크해보고 더 로직 구현하기
        /*PackageExportRequest exportRequest = new PackageExportRequest(request.getMetadataId(), request.getRawdataIds());
        ApiResponse<PackageExportResponse> apiResponse = apiService.packageExport(exportRequest);*/
        return null;
    }

    /**
     * @method      [15] downloadMetadata
     * @author      dahyeon
     * @date        2025-10-29
     * @deacription 메타데이터 파일 다운로드
     *
     * @param request 다운로드할 메타데이터 ID & 해당 메타데이터의 파일 ID
    **/
    @Transactional
    public ResponseEntity downloadMetadata(DownloadMetadata request, HttpServletRequest req) throws IOException {

        log.info("======================= [15] Download Metadata : {}({}) ===========================", request.getMetadataId(), request.getMetadataFileId());

        /*DownloadMetadataFileRequest downloadRequest = new DownloadMetadataFileRequest(request.getMetadataId());
        apiService.downloadMetadataFile(downloadRequest);*/
        // todo 추후에는 > 로그insert & down_count++ 만 진행
        return datasetFileService.downloadMetaFile(request.getMetadataFileId(), req);
    }

    /**
     * @method      [16] downloadRawdata
     * @author      dahyeon
     * @date        2025-10-29
     * @deacription 원본데이터 파일 다운로드
     *
     * @param request 다운로드할 원본데이터 ID (+메타데이터 ID) & 해당 원본데이터의 파일 ID
    **/
    @Transactional
    public ResponseEntity downloadRawdata(DownloadRawdata request, HttpServletRequest req) throws IOException {

        log.info("======================= [16] Download Rawdata : {}({}) ===========================", request.getRawdataId(), request.getRawdataFileId());

        /*DownloadRawdataRequest downloadRequest = new DownloadRawdataRequest(request.getMetadataId(), request.getRawdataId());
        apiService.downloadRawdata(downloadRequest);*/
        // todo 추후에는 > 로그insert & down_count++ 만 진행
        return datasetFileService.downloadMetaFile(request.getRawdataFileId(), req);
    }

    /**
     * @method      [17] findMetaKeyList
     * @author      dahyeon
     * @date        2025-10-29
     * @deacription 메타데이터 검색을 위한 필터 키 목록 조회
    **/
    @Transactional(readOnly = true)
    public MetaKeyList findMetaKeyList() {
        log.info("======================= [17] Search Meta Key List ===========================");
        /*ApiResponse<MetaKeysListResponse> apiResponse = apiService.findMetaKeysList();
        MetaKeysListResponse result = apiResponse.getResult();
        List<String> filterList = result.getFilters();
        return new MetaKeyList(filterList);*/
        return new MetaKeyList(Arrays.asList("category", "tag", "date"));
    }

    /**
     * @method      [18] findMetaKeyValueList
     * @author      dahyeon
     * @date        2025-10-29
     * @deacription 메타데이터 검색을 위한 필터 Value 목록 조회
     *
     * @param search 필터 키값
     *               => 해당 필터 키 하위에 해당하는 Value 목록이 반환된다.
    **/
    @Transactional(readOnly = true)
    public MetaKeyValueList findMetaKeyValueList(SearchMetaValues search) {
        log.info("======================= [18] Search Meta Key Value List : {} ===========================", search.getKey());
        /*SearchMetaValuesRequest searchKeyValueRequest = new SearchMetaValuesRequest(search.getKey());
        ApiResponse<MetaValuesListResponse> apiResponse = apiService.findMetaValuesList(searchKeyValueRequest);
        MetaValuesListResponse valuesListResponse = apiResponse.getResult();*/
        String filter = search.getKey(); // filter = valuesListResponse.getFilter();
        List<String> values = new ArrayList<>(); // values = valuesListResponse.getValues();

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
