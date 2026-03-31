package kware.apps.mobigen.integration.service;

import cetus.bean.Page;
import cetus.bean.Pageable;
import cetus.user.UserUtil;
import kware.apps.mobigen.cetus.dataset.service.CetusMobigenDatasetService;
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
import kware.apps.mobigen.mobigen.dto.request.meta.SearchMetaValuesRequest;
import kware.apps.mobigen.mobigen.dto.request.metadata.DeleteMetadatasRequest;
import kware.apps.mobigen.mobigen.dto.request.metadata.DownloadMetadataFileRequest;
import kware.apps.mobigen.mobigen.dto.request.metadata.SearchMetadataViewRequest;
import kware.apps.mobigen.mobigen.dto.request.metadata.change.ChangeMetadataFieldRequest;
import kware.apps.mobigen.mobigen.dto.request.metadata.change.ChangeMetadataRequest;
import kware.apps.mobigen.mobigen.dto.request.metadata.create.CreateMetadataFieldRequest;
import kware.apps.mobigen.mobigen.dto.request.metadata.create.CreateMetadataRequest;
import kware.apps.mobigen.mobigen.dto.request.metadata.search.SearchMetadataListRequest;
import kware.apps.mobigen.mobigen.dto.request.pckge.PackageExportRequest;
import kware.apps.mobigen.mobigen.dto.request.rawdata.*;
import kware.apps.mobigen.mobigen.dto.request.rawdata.change.ChangeRawdataFieldRequest;
import kware.apps.mobigen.mobigen.dto.request.rawdata.change.ChangeRawdataRequest;
import kware.apps.mobigen.mobigen.dto.request.recommendation.SearchRecommendationListRequest;
import kware.apps.mobigen.mobigen.dto.request.relation.SearchRelationListRequest;
import kware.apps.mobigen.mobigen.dto.response.ApiResponse;
import kware.apps.mobigen.mobigen.dto.response.common.MetadataResultResponse;
import kware.apps.mobigen.mobigen.dto.response.meta.MetaKeysListResponse;
import kware.apps.mobigen.mobigen.dto.response.meta.MetaValuesListResponse;
import kware.apps.mobigen.mobigen.dto.response.metadata.*;
import kware.apps.mobigen.mobigen.dto.response.pckge.PackageImportResponse;
import kware.apps.mobigen.mobigen.dto.response.rawdata.*;
import kware.apps.mobigen.mobigen.dto.response.recommendation.RecommendationListResponse;
import kware.apps.mobigen.mobigen.dto.response.recommendation.RecommendationsResponse;
import kware.apps.mobigen.mobigen.dto.response.relation.FieldRelationsResponse;
import kware.apps.mobigen.mobigen.dto.response.relation.RelatedMetadataResponse;
import kware.apps.mobigen.mobigen.dto.response.relation.RelationListResponse;
import kware.apps.mobigen.mobigen.service.MobigenExternalApiService;
import kware.apps.thirdeye.bookmark.service.CetusBookMarkService2;
import kware.apps.thirdeye.mobigen.approveddataset.service.CetusApprovedDatasetService2;
import kware.apps.thirdeye.mobigen.category.service.CetusDatasetCategoryService;
import kware.apps.thirdeye.mobigen.datasetfile.domain.file.CetusDatasetFile;
import kware.apps.thirdeye.mobigen.datasetfile.dto.request.DeleteDatasetFile;
import kware.apps.thirdeye.mobigen.datasetfile.dto.request.SearchDatasetFile;
import kware.apps.thirdeye.mobigen.datasetfile.dto.request.SearchDatasetFileView;
import kware.apps.thirdeye.mobigen.datasetfile.dto.response.CetusDatasetFileView;
import kware.apps.thirdeye.mobigen.datasetfile.enumcd.DataFileTpCd;
import kware.apps.thirdeye.mobigen.datasetfile.service.CetusDatasetFileService;
import kware.apps.thirdeye.mobigen.thirdeyeregistrant.dto.response.ThirdeyeRegistrantView;
import kware.apps.thirdeye.mobigen.thirdeyeregistrant.service.CetusThirdeyeRegistrantService;
import kware.common.exception.SimpleException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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
    private final CetusThirdeyeRegistrantService registrantService;
    private final CetusApprovedDatasetService2 approvedDatasetService2;
    private final CetusBookMarkService2 bookMarkService2;

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
     * @method      [PACKAGE_01] downloadPackage
     * @author      dahyeon
     * @date        2025-10-29
     * @deacription 패키지 데이터셋 다운로드
     *
     * @param request 메타데이터 ID & 원본데이터 ID 목록
     **/
    @Transactional
    public ResponseEntity<Resource> downloadPackage(DownloadPackageDataset request) {
        List<String> rawdataIds = request.getRawdataIds();
        if (rawdataIds == null) {
            rawdataIds = new ArrayList<>();
        }
        log.info("=== [PACKAGE_01][14] Download Package Data : {}({}) ===", request.getMetadataId(), rawdataIds);
        PackageExportRequest exportRequest = new PackageExportRequest(request.getMetadataId(), rawdataIds);

        // 외부 API 호출
        ResponseEntity<Resource> apiResponse = apiService.packageExport(exportRequest);

        if (apiResponse == null || apiResponse.getBody() == null) {
            return ResponseEntity.noContent().build();
        }

        // 파일 다운로드를 위한 헤더 설정 (필요 시 파일명 커스텀)
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, apiResponse.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION))
                .body(apiResponse.getBody());
    }

    /**
     * @method      [PACKAGE_02] savePackageDataset
     * @author      dahyeon
     * @date        2025-10-29
     * @deacription 패키지 데이터셋 등록
     *
     * @param request           KWARE 포탈 시스템에  파일 정보 저장을 위한 DTO
     * @param packageFileData   모비젠 측에 전달할 파일
    **/
    @Transactional
    public PackageImportResponse savePackageDataset( SavePackageDataset request, MultipartFile packageFileData ) throws IOException {

        log.info("=== [PACKAGE_02][1] Save Package Metadata ===");
        Path path = this.convertToPath(packageFileData);
        ApiResponse<PackageImportResponse> apiResponse = apiService.packageImport(path);

        // 1. 응답 코드 체크
        if (apiResponse != null && !apiResponse.isSuccess()) {
            log.error("=== [PACKAGE_02] Error - message: {} ===", apiResponse.getMessage());
            throw new SimpleException(apiResponse.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("=== [PACKAGE_02] Success - message: {} ===", apiResponse.getMessage());

        // 2. 성공 시 로직 수행
        PackageImportResponse result = apiResponse.getResult();
        String metadataId = result.getMetadata().getMetadata_id();
        String rawdataId = apiResponse.getResult().getRawdata().getRawdata_id();

        // 2-1. [PACKAGE] data file
        CetusDatasetFile metaFile = request.getPackageFile();
        metaFile.setPackageDto(metadataId, rawdataId, DataFileTpCd.PACKAGE);
        datasetFileService.processAddFile(metaFile);

        // 2-2. 모비젠 등록자 정보 저장 등 후속 절차
        registrantService.saveThirdeyeRegistrant(metadataId);

        log.info("패키지 데이터셋 저장 완료: metadataId = {}", metadataId);

        return apiResponse.getResult();
    }

    /**
     * @method      [METADATA_01] pageMetadata
     * @author      dahyeon
     * @date        2025-10-29
     * @deacription 메타데이터 목록 조회 + 페이징
     *
     * @param search 메타데이터 목록 조회 요청 DTO
     **/
    @Transactional
    public Page<MetadataList> pageMetadata(SearchMetadataPage search) {

        SearchMetadataListRequest pageSearchRequest = new SearchMetadataListRequest(
                search.getPublisher(), search.getKeyword(), search.getTheme(),
                search.getPageNumber(), search.getSize(),
                search.getDateRangeStart(), search.getDateRangeEnd(),
                search.getSortField(), search.getSortOrder()
        );

        log.info("=== [METADATA_01] Page Metadata: {} ===", pageSearchRequest);

        // 1. 외부 API 호출
        ApiResponse<MetadataListResponse> apiResponse = apiService.findMetadataList(pageSearchRequest);

        // 2. 응답 성공 여부 체크
        if (apiResponse != null && !apiResponse.isSuccess()) {
            log.error("=== [METADATA_01] Error - message: {} ===", apiResponse.getMessage());
            throw new SimpleException(apiResponse.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("=== [METADATA_01] Success - message: {} ===", apiResponse.getMessage());

        // 3. 성공 시 이후 로직 진행
        int totalCount = apiResponse.getResult().getTotal_count();
        int page = apiResponse.getResult().getPage() + 1;
        int limit = apiResponse.getResult().getLimit();
        List<MetadataResultResponse> items = apiResponse.getResult().getItems();
        log.info("=== [METADATA_01] Success - totalCount: {}, pageNumber: {}, size(limit): {} ===", totalCount, page, limit);

        // 4. 리턴
        List<MetadataList> metadataList = new ArrayList<>();
        items.forEach(data -> {

            String metadataId = data.getMetadata_id();
            MetadataList metadataDto = new MetadataList(data, metadataId);
            metadataDto.setMetadataView(data);

            // 등록자 정보
            ThirdeyeRegistrantView registrantView = registrantService.findThirdeyeRegistrant(metadataId);
            if(registrantView != null) metadataDto.setRegistrantInfo(registrantView.isRegistered(), registrantView.getRegistrantId());
            else metadataDto.setRegistrantInfo(false, null);

            // 이미 진열관리 중인 데이터셋인지 여부
            Boolean isApproved = approvedDatasetService2.findMetadataIsApproved(metadataId, UserUtil.getUserWorkplaceUid());
            metadataDto.setApproved(isApproved);

            metadataList.add(metadataDto);
        });
        return new Page<>(metadataList, totalCount, new Pageable(limit, page, limit));
    }

    /**
     * @method      [METADATA_02] deleteMetadatas
     * @author      dahyeon
     * @date        2025-10-29
     * @deacription 메타데이터 여러건 삭제
     *
     * @param request 삭제할 메타데이터 ID 리스트 DTO
     **/
    @Transactional
    public void deleteMetadatas(DeleteMetadatas request) {

        // 1. 메타데이터 여러건 삭제
        List<String> metadataIds = request.getUids();
        log.info("=== [METADATA_02] Delete Metadata's : {} ===", metadataIds);

        DeleteMetadatasRequest deleteRequest = new DeleteMetadatasRequest(metadataIds);

        // 1. 외부 API 호출
        ApiResponse<DeleteMetadataResponse> apiResponse = apiService.deleteSeveralMetadata(deleteRequest);

        // 2. 응답 성공 여부 체크
        if (!apiResponse.isSuccess()) {
            log.error("=== [METADATA_02] Error - message: {} ===", apiResponse.getMessage());
            throw new SimpleException(apiResponse.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("=== [METADATA_02] Success - message: {} ===", apiResponse.getMessage());

        // 3. 추가 정보 삭제
        for (String metadataId: metadataIds) {

            // 1.{metadataId} 데이터셋이 북마크 등록됐다면 삭제
            bookMarkService2.deleteBookMarkByMetadataId(metadataId);

            // 2. {metadataId} 데이터셋이 kware 포탈 시스템에서 관리중인 값이라면 포탈 시스템에서도 삭제 (논리삭제)
            mobigenDatasetService.ifExistDeleteApprovedDataset(metadataId);

            // 3. 해당 파일 정보도 삭제
            datasetFileService.processDelFile(DeleteDatasetFile.deleteMetadata(metadataId));
        }
    }

    /**
     * @method      [METADATA_03] createMetadata
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

        log.info("=== [METADATA_03] Create Metadata ===");

        Path path = this.convertToPath(realFileData);

        // 1. 외부 API: 메타데이터 기본 정보 등록
        CreateMetadataRequest metadataRequest = new CreateMetadataRequest(
                new CreateMetadataFieldRequest(request.getExtdata())
        );

        ApiResponse<CreateMetadataResponse> apiResponse = apiService.createMetadata(metadataRequest, path);

        // 성공 체크
        if (!apiResponse.isSuccess()) {
            log.error("=== [METADATA_03] Error - message: {} ===", apiResponse.getMessage());
            throw new SimpleException("메타데이터 외부 등록 실패: " + apiResponse.getMessage(), HttpStatus.BAD_REQUEST);
        }
        log.info("=== [METADATA_03] Success - message: {} ===", apiResponse.getMessage());

        String metadataId = apiResponse.getResult().getMetadata().getMetadata_id();

        // 2. 메타데이터 파일 저장
        CetusDatasetFile metaFile = request.getMetaFile();
        metaFile.setMetadataDto(metadataId, DataFileTpCd.METADATA);
        datasetFileService.processAddFile(metaFile);

        // 3. 원본데이터 파일 저장
        if( realFileData != null && !realFileData.isEmpty() ) {
            String rawdataId = apiResponse.getResult().getRawdata().getRawdata_id();
            CetusDatasetFile realFile = request.getRealFile();
            realFile.setRawdataDto(metadataId, rawdataId, DataFileTpCd.RAWDATA);
            datasetFileService.processAddFile(realFile);
            log.info("=== [METADATA_03] Upload Realdata : {} ===", rawdataId);
        }

        // 4. 모비젠 등록자 정보 저장
        registrantService.saveThirdeyeRegistrant(metadataId);
    }

    /**
     * @method      [METADATA_04] viewMetadata
     * @author      dahyeon
     * @date        2025-10-29
     * @deacription 메타데이터 상세 정보 조회
     *
     * @param search 메타데이터 ID
     **/
    @Transactional(readOnly = true)
    public MetadataView viewMetadata( SearchMetadataView search ) {

        log.info("=== [METADATA_04] View Metadata : {} ===", search.getMetadataId());
        String metadataId = search.getMetadataId();

        // 1. 외부 API 호출
        SearchMetadataViewRequest searchRequest = new SearchMetadataViewRequest(metadataId);
        ApiResponse<ViewMetadataResponse> apiResponse = apiService.findMetadataById(searchRequest);

        // 2. 응답 성공 여부 체크 (실패 시 null 반환)
        if (!apiResponse.isSuccess()) {
            log.error("=== [METADATA_04] Error - message: {} ===", apiResponse.getMessage());
            return null;
        }
        log.info("=== [METADATA_04] Success - message: {} ===", apiResponse.getMessage());

        ViewMetadataResponse metadataResponse = apiResponse.getResult();
        MetadataView metadataView = new MetadataView(metadataResponse, metadataId);

        // 2. 데이터 등록자가 있다면 세팅
        ThirdeyeRegistrantView registrantView = registrantService.findThirdeyeRegistrant(metadataId);
        metadataView.setRegistrantId((registrantView != null) ? registrantView.getRegistrantId() : null);

        // 3. 데이터에 대한 파일 등록 정보가 KWARE 포탈 시스템에 있다면 세팅
        List<CetusDatasetFileView> packagedataFiles = datasetFileService.findDataFileList(new SearchDatasetFile(metadataId, null, DataFileTpCd.PACKAGE.name()));
        metadataView.setPackagedataFile((packagedataFiles != null && !packagedataFiles.isEmpty()) ? packagedataFiles.get(0) : null);

        List<CetusDatasetFileView> metadataFiles = datasetFileService.findDataFileList(new SearchDatasetFile(metadataId, null, DataFileTpCd.METADATA.name()));
        metadataView.setMetadataFile((metadataFiles != null && !metadataFiles.isEmpty()) ? metadataFiles.get(0) : null);

        List<CetusDatasetFileView> rawdataFiles = datasetFileService.findDataFileList(new SearchDatasetFile(metadataId, null, DataFileTpCd.RAWDATA.name()));
        metadataView.setRawdataFiles(rawdataFiles);

        return metadataView;
    }

    /**
     * @method      [METADATA_05] updateMetadata
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
        log.info("=== [METADATA_05] Update Metadata : {} ===", metadataId);

        ChangeMetadataRequest changeRequest = new ChangeMetadataRequest(
                metadataId, new ChangeMetadataFieldRequest(request.getExtdata())
        );
        ApiResponse<ChangeMetadataResponse> apiResponse1 = apiService.changeMetadata(changeRequest);

        // [성공 체크] 실패 시 SimpleException 던지기
        if (apiResponse1 == null || !apiResponse1.isSuccess()) {
            String message = (apiResponse1.getMessage() != null) ? apiResponse1.getMessage() : HttpStatus.BAD_REQUEST.getReasonPhrase();
            log.error("=== [METADATA_05] Error - message: {} ===", message);
            throw new SimpleException("메타데이터 정보 수정 실패: " + message);
        }
        log.info("=== [METADATA_04] Success - message: {} ===", apiResponse1.getMessage());

        // 2. 원본데이터 파일 업로드
        if( realFileData != null && !realFileData.isEmpty() ) {
            this.uploadRawData(metadataId, realFileData, request.getRealFile());
        }
        log.info("=== [4-3] Update Search Data : {} ===", metadataId);
    }

    /**
     * @method      [METADATA_07] previewMetadata
     * @author      dahyeon
     * @date        2025-10-29
     * @deacription 메타데이터 파일 정보 미리보기
     *
     * @param file  미리보기로 읽어올 메타데이터 파일
     *              => 해당 파일 내부의 파일 정보를 읽어서 응답 결과로 보내준다.
     **/
    @Transactional(readOnly = true)
    public ApiResponse<MetadataFilePreviewResponse> previewMetadata(MultipartFile file) throws IOException {
        log.info("=== [METADATA_07] Preview Read Metadata : {} ===", file.getOriginalFilename());

        try {

            // 1. 파일을 임시 경로로 변환 (기존에 작성하신 convertToPath 활용)
            Path path = this.convertToPath(file);

            // 2. 외부 API 호출
            ApiResponse<MetadataFilePreviewResponse> apiResponse = apiService.previewMetadataFile(path);

            // 3. 성공 여부 체크
            if (apiResponse != null && !apiResponse.isSuccess()) {
                log.error("=== [METADATA_07] Error - message: {} ===", apiResponse.getMessage());
                throw new SimpleException(apiResponse.getMessage(), HttpStatus.BAD_REQUEST);
            }
            log.info("=== [METADATA_07] Success - message: {} ===", apiResponse.getMessage());

            // 4. 성공 시 ApiResponse 객체 그대로 반환
            return apiResponse;

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @method      [METADATA_08] downloadMetadata
     * @author      dahyeon
     * @date        2025-10-29
     * @deacription 메타데이터 파일 다운로드
     *
     * @param request 다운로드할 메타데이터 ID & 해당 메타데이터의 파일 ID
     **/
    @Transactional
    public ResponseEntity<Resource> downloadMetadata(DownloadMetadata request, HttpServletRequest req) throws IOException {

        log.info("=== [METADATA_08] Download Metadata : {}({}) ===", request.getMetadataId(), request.getMetadataFileId());

        // 외부 API 호출
        DownloadMetadataFileRequest downloadRequest = new DownloadMetadataFileRequest(request.getMetadataId());
        ResponseEntity<Resource> apiResponse = apiService.downloadMetadataFile(downloadRequest);

        if (apiResponse == null || apiResponse.getBody() == null) {
            return ResponseEntity.noContent().build();
        }

        String fileId = request.getMetadataFileId();
        if(fileId != null && !"null".equals(fileId) && !"undefined".equals(fileId)) datasetFileService.updateDownCntAndLog(fileId, req);

        // 파일 다운로드를 위한 헤더 설정 (필요 시 파일명 커스텀)
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, apiResponse.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION))
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION)
                .body(apiResponse.getBody());
    }

    /**
     * @method      [RAWDATA_01] pageRawdata
     * @author      dahyeon
     * @date        2025-10-29
     * @deacription 원본데이터 목록 조회 + 페이징
     *
     * @param search 원본데이터 목록 조회 요청 DTO
     **/
    @Transactional(readOnly = true)
    public Page<RawdataList> pageRawdata(SearchRawdataPage search) {

        String metadataId = search.getMetadataId();
        log.info("=== [RAWDATA_01] Page Rawdata : {} ===", metadataId);

        SearchRawdataListRequest searchRequest = new SearchRawdataListRequest(
                metadataId, search.getPageNumber(), search.getSize()
        );
        ApiResponse<RawdataListResponse> apiResponse = apiService.findRawdataList(searchRequest);

        // 2. 응답 성공 여부 체크
        if (apiResponse != null && !apiResponse.isSuccess()) {
            log.error("=== [RAWDATA_01] Error - message: {} ===", apiResponse.getMessage());
            throw new SimpleException(apiResponse.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("=== [RAWDATA_01] Success - message: {} ===", apiResponse.getMessage());

        // 3. 성공 시 이후 로직 진행
        int totalCount = apiResponse.getResult().getTotal_count();
        int page = search.getPageNumber();
        int limit = search.getSize();
        List<RawdataListItemResponse> items = apiResponse.getResult().getItems();
        log.info("=== [METADATA_01] Success - totalCount: {}, pageNumber: {}, size(limit): {} ===", totalCount, page, limit);

        List<RawdataList> rawdataList = new ArrayList<>();
        items.forEach(item -> {

            String rawdataId = item.getRawdata_id();
            RawdataList rawdata = new RawdataList(item, metadataId, rawdataId);
            rawdata.setRawdataView(item);

            SearchDatasetFileView fileViewSearch = new SearchDatasetFileView(metadataId, rawdataId);
            CetusDatasetFileView rawdataFile = datasetFileService.findRawdataFileView(fileViewSearch);
            rawdata.setRawdataFile(rawdataFile);

            rawdataList.add(rawdata);
        });
        return new Page<>(rawdataList, totalCount, new Pageable(search.getSize(), page, limit));
    }

    /**
     * @method      [RAWDATA_02] uploadRawData
     * @author      dahyeon
     * @date        2026-02-12
     * @deacription 원본데이터 파일 업로드
     **/
    public void uploadRawData(String metadataId, MultipartFile realFileData, CetusDatasetFile realFile) throws IOException {
        log.info("=== [RAWDATA_02] Upload Realdata ===");

        UploadRawdataRequest uploadRequest = new UploadRawdataRequest(metadataId);
        Path realFilePath = this.convertToPath(realFileData);

        ApiResponse<UploadRawdataResponse> apiResponse2 = apiService.uploadRawdata(uploadRequest, realFilePath);

        // [성공 체크] 파일 업로드 실패 시 SimpleException 던지기
        if (apiResponse2 == null || !apiResponse2.isSuccess()) {
            String message = (apiResponse2.getMessage() != null) ? apiResponse2.getMessage() : HttpStatus.BAD_REQUEST.getReasonPhrase();
            log.error("=== [RAWDATA_02] Error - message: {} ===", message);
            throw new SimpleException("원본 데이터 파일 업로드 실패: " + message);
        }
        log.info("=== [RAWDATA_02] Success - message: {} ===", apiResponse2.getMessage());

        String rawdataId = apiResponse2.getResult().getRawdata_id();
        if (realFile != null) {
            realFile.setMetadataId(metadataId);
            realFile.setDataTpCd(DataFileTpCd.RAWDATA.name());
            realFile.setRawdataId(rawdataId);
            datasetFileService.processAddFile(realFile);
        }
    }

    /**
     * @method      [RAWDATA_03] deleteRawdatas
     * @author      dahyeon
     * @date        2025-10-29
     * @deacription 원본데이터 여러건 삭제
     *
     * @param request 삭제할 원본데이터 ID 리스트 DTO
     **/
    @Transactional
    public void deleteRawdatas(DeleteRawdatas request) {

        // 1. 원본데이터 여러건 삭제
        log.info("=== [RAWDATA_03] Delete Rawdata's : {} ===", request.getRawdataIds());

        DeleteRawdatasRequest deleteRequest = new DeleteRawdatasRequest(request.getMetadataId(), request.getRawdataIds());
        ApiResponse<DeleteRawdatasResponse> apiResponse = apiService.deleteSeveralRawdata(deleteRequest);
        if (apiResponse != null && !apiResponse.isSuccess()) {
            log.error("=== [RAWDATA_03] Error - message: {} ===", apiResponse.getMessage());
            throw new SimpleException(apiResponse.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("=== [RAWDATA_03] Success - message: {} ===", apiResponse.getMessage());

        // 2. 원본데이터 파일 정보도 삭제
        for (String rawdataId: request.getRawdataIds()) {
            datasetFileService.processDelFile(DeleteDatasetFile.deleteRawdata(rawdataId));
        }
    }

    /**
     * @method      [RAWDATA_04] viewRawdata
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
        log.info("=== [RAWDATA_04] View Rawdata : {} ===", rawdataId);

        SearchRawdataViewRequest searchRequest = new SearchRawdataViewRequest(metadataId, rawdataId);
        ApiResponse<ViewRawdataResponse> apiResponse = apiService.findRawdataById(searchRequest);
        if (apiResponse != null && !apiResponse.isSuccess()) {
            log.error("=== [RAWDATA_04] Error - message: {} ===", apiResponse.getMessage());
            throw new SimpleException(apiResponse.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("=== [RAWDATA_04] Success - message: {} ===", apiResponse.getMessage());

        RawdataView rawdataView = new RawdataView(metadataId, rawdataId);
        ViewRawdataResponse item = apiResponse.getResult();

        // mobigen 에서 얻은 정보 세팅
        rawdataView.setRawdataResponse(item);

        // 우선은 원본데이터 파일 정보들 세팅
        SearchDatasetFileView fileViewSearch = new SearchDatasetFileView(metadataId, rawdataId);
        CetusDatasetFileView rawdataFile = datasetFileService.findRawdataFileView(fileViewSearch);
        rawdataView.setRawdataFile(rawdataFile);

        return rawdataView;
    }

    /**
     * @method      [RAWDATA_05] updateRawdata
     * @author      dahyeon
     * @date        2025-10-29
     * @deacription 원본데이터 정보 수정
     *
     * @param request 수정할 원본데이터 정보 DTO
     **/
    @Transactional
    public void updateRawdata(ChangeRawdata request) {
        log.info("=== [RAWDATA_05] Update Rawdata : {} ===", request.getRawdataId());
        ChangeRawdataRequest updateRequest = new ChangeRawdataRequest(
                request.getRawdataId(),
                request.getMetadataId(),
                new ChangeRawdataFieldRequest(request.getExtdata())
        );
        ApiResponse<ChangeRawdataResponse> apiResponse = apiService.changeRawdata(updateRequest);
        if (apiResponse != null && !apiResponse.isSuccess()) {
            log.error("=== [RAWDATA_05] Error - message: {} ===", apiResponse.getMessage());
            throw new SimpleException(apiResponse.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("=== [RAWDATA_05] Success - message: {} ===", apiResponse.getMessage());
    }

    /**
     * @method      [RAWDATA_07] downloadRawdata
     * @author      dahyeon
     * @date        2025-10-29
     * @deacription 원본데이터 파일 다운로드
     *
     * @param request 다운로드할 원본데이터 ID (+메타데이터 ID) & 해당 원본데이터의 파일 ID
     **/
    @Transactional
    public ResponseEntity<Resource> downloadRawdata(DownloadRawdata request, HttpServletRequest req) throws IOException {

        log.info("=== [RAWDATA_07] Download Rawdata : {}({}) ===", request.getRawdataId(), request.getRawdataFileId());

        // 외부 API 호출
        DownloadRawdataRequest downloadRequest = new DownloadRawdataRequest(request.getMetadataId(), request.getRawdataId());
        ResponseEntity<Resource> apiResponse = apiService.downloadRawdata(downloadRequest);

        if (apiResponse == null || apiResponse.getBody() == null) {
            return ResponseEntity.noContent().build();
        }

        String fileId = request.getRawdataFileId();
        if(fileId != null && !"null".equals(fileId) && !"undefined".equals(fileId)) datasetFileService.updateDownCntAndLog(fileId, req);

        // 파일 다운로드를 위한 헤더 설정 (필요 시 파일명 커스텀)
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, apiResponse.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION))
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION)
                .body(apiResponse.getBody());
    }

    /**
     * @method      [META_01] findMetaKeyList
     * @author      dahyeon
     * @date        2025-10-29
     * @deacription 메타데이터 검색을 위한 필터 키 목록 조회
     **/
    @Transactional(readOnly = true)
    public MetaKeyList findMetaKeyList() {
        log.info("=== [META_01] Search Meta Key List ===");

        ApiResponse<MetaKeysListResponse> apiResponse = apiService.findMetaKeysList();
        if (apiResponse != null && !apiResponse.isSuccess()) {
            log.error("=== [META_01] Error - message: {} ===", apiResponse.getMessage());
            throw new SimpleException(apiResponse.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("=== [META_01] Success - message: {} ===", apiResponse.getMessage());

        List<String> filters = apiResponse.getResult().getFilters();
        return new MetaKeyList(filters);
    }

    /**
     * @method      [META_02] findMetaKeyValueList
     * @author      dahyeon
     * @date        2025-10-29
     * @deacription 메타데이터 검색을 위한 필터 Value 목록 조회
     *
     * @param search 필터 키값
     *               => 해당 필터 키 하위에 해당하는 Value 목록이 반환된다.
     **/
    @Transactional(readOnly = true)
    public MetaKeyValueList findMetaKeyValueList(SearchMetaValues search) {
        log.info("=== [META_02] Search Meta Key Value List : {} ===", search.getKey());

        String filterKey = search.getKey();
        SearchMetaValuesRequest searchKeyValueRequest = new SearchMetaValuesRequest(filterKey);
        ApiResponse<MetaValuesListResponse> apiResponse = apiService.findMetaValuesList(searchKeyValueRequest);

        if (apiResponse != null && !apiResponse.isSuccess()) {
            log.error("=== [META_02] Error - message: {} ===", apiResponse.getMessage());
            throw new SimpleException(apiResponse.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("=== [META_02] Success - message: {} ===", apiResponse.getMessage());

        List<String> filterValues = apiResponse.getResult().getValues();
        return new MetaKeyValueList(filterKey, filterValues);
    }

    /**
     * @method      [RELATION_01] pageRelations
     * @author      dahyeon
     * @date        2025-10-29
     * @deacription 관계 메타데이터 정보 목록 조회 + 페이징
     *
     * @param search 관계데이터 목록 조회 요청 DTO
     **/
    @Transactional(readOnly = true)
    public Page<RelationsList> pageRelations(SearchRelationsPage search) {

        SearchRelationListRequest listRequest = new SearchRelationListRequest(
                search.getPublisher(), search.getTheme(), search.getScoreMin(), search.getScoreMax(),
                search.getPageNumber(), search.getSize(), search.getSortOrder(), search.getSortField()
        );
        log.info("=== [RELATION_01] Relation Metadata: {} ===", listRequest);

        ApiResponse<RelationListResponse> apiResponse = apiService.findRelationDataList(listRequest);

        if (apiResponse != null && !apiResponse.isSuccess()) {
            log.error("=== [RELATION_01] Error - message: {} ===", apiResponse.getMessage());
            throw new SimpleException(apiResponse.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("=== [RELATION_01] Success - message: {} ===", apiResponse.getMessage());

        RelationListResponse reponseResult = apiResponse.getResult();
        int totalCount = reponseResult.getTotal_count();
        int page =  reponseResult.getPage();
        int limit = reponseResult.getLimit();
        List<RelatedMetadataResponse> items = reponseResult.getItems();

        List<RelationsList> relationsList = new ArrayList<>();
        items.forEach(data -> {
            String metadataId = reponseResult.getMetadata_id();
            List<FieldRelationsResponse> fieldRelations = reponseResult.getField_relations();
            RelationsList relationDto = new RelationsList(metadataId, data, fieldRelations);
            relationDto.setRelationMetadata(data);
            relationsList.add(relationDto);
        });
        return new Page<>(relationsList, totalCount, new Pageable(limit, page, limit));
    }

    /**
     * @method      [RECOMMENDATION_01] pageRecommendation
     * @author      dahyeon
     * @date        2025-10-29
     * @deacription 추천 메타데이터 정보 목록 조회 + 페이징
     *
     * @param search 추천데이터 목록 조회 요청 DTO
     **/
    @Transactional(readOnly = true)
    public Page<RecommendationList> pageRecommendation(SearchRecommendationPage search) {


        SearchRecommendationListRequest pageRequest = new SearchRecommendationListRequest(
                search.getRecommendationType(), search.getPublisher(), search.getTheme(),
                search.getPageNumber(), search.getSize(), search.getSortOrder(), search.getSortField()
        );
        log.info("=== [RECOMMENDATION_01] Recommendation Metadata: {} ===", pageRequest);

        ApiResponse<RecommendationListResponse> apiResponse = apiService.findRecommendationDataList(pageRequest);

        if (apiResponse != null && !apiResponse.isSuccess()) {
            log.error("=== [RECOMMENDATION_01] Error - message: {} ===", apiResponse.getMessage());
            throw new SimpleException(apiResponse.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("=== [RECOMMENDATION_01] Success - message: {} ===", apiResponse.getMessage());

        RecommendationListResponse apiResult = apiResponse.getResult();
        int totalCount =  apiResult.getTotal_count();
        int page =  apiResult.getPage();
        int limit = apiResult.getLimit();
        String recommendationMedataId = apiResult.getMetadata_id();
        List<RecommendationsResponse> items = apiResult.getItems();

        List<RecommendationList> recommendationList = new ArrayList<>();
        items.forEach(data -> {
            RecommendationList recommendationDto = new RecommendationList(recommendationMedataId, data);
            recommendationList.add(recommendationDto);
        });
        return new Page<>(recommendationList, totalCount, new Pageable(limit, page, limit));
    }

}
