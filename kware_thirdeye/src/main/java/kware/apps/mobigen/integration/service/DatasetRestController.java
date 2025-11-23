package kware.apps.mobigen.integration.service;

import cetus.bean.Page;
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
import kware.apps.mobigen.mobigen.dto.response.ApiResponse;
import kware.apps.mobigen.mobigen.dto.response.metadata.MetadataFilePreviewResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/portal/integration-dataset")
public class DatasetRestController {

    private final DatasetService service;

    /**
     *
     * [PACKAGE_02] 패키지 데이터셋 업로드
     *
     * @api         [POST] /api/portal/integration-dataset/import-package
     * @author      dahyeon
     * @date        2025-10-23
    **/
    @PostMapping("/import-package")
    public ResponseEntity savPackageDataset( @RequestPart("request") SavePackageDataset request,
                                             @RequestPart("packageFileData") MultipartFile packageFileData ) throws IOException {
        if(packageFileData.isEmpty()) {
            return ResponseEntity.badRequest().body("no file founded");
        }
        service.savePackageDataset(request, packageFileData);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * [METADATA_07] 특정 메타데이터 파일 정보 미리보기
     *
     * @api         [POST] /api/portal/integration-dataset/metadata-preview
     * @author      dahyeon
     * @date        2025-10-23
    **/
    @PostMapping("/metadata-preview")
    public ResponseEntity metadataPreview(@RequestParam("file") MultipartFile file) throws IOException {
        if(file.isEmpty()) {
            return ResponseEntity.badRequest().body("no file founded");
        }
        ApiResponse<MetadataFilePreviewResponse> apiResponse = service.previewMetadata(file);
        return ResponseEntity.ok(apiResponse);
    }

    /**
     *
     * [METADATA_03] 메타데이터 생성
     * (+) 원본데이터 파일 업로드  [선택]
     *
     *
     * @api         [POST] /api/portal/integration-dataset/create-metadata
     * @author      dahyeon
     * @date        2025-10-23
    **/
    @PostMapping("/create-metadata")
    public ResponseEntity createMetadata( @RequestPart("request") SaveMetadata request,
                                          @RequestPart(name = "realFileData", required = false) MultipartFile realFileData ) throws IOException {
        service.createMetadata(request, realFileData);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * [METADATA_05] 메타데이터 정보 수정
     * (+) [RAWDATA_02] 원본데이터 파일 추가 업로드 [선택]
     *
     * @api         [POST] /api/portal/integration-dataset/update-metadata
     * @author      dahyeon
     * @date        2025-10-23
    **/
    @PostMapping("/update-metadata")
    public ResponseEntity updateMetadata( @RequestPart("request") ChangeMetadata request,
                                          @RequestPart(name = "realFileData", required = false) MultipartFile realFileData ) throws IOException {
        service.updateMetadata(request, realFileData);
        return ResponseEntity.ok().build();
    }

    /**
     *
     *  [METADATA_02] 여러건의 메타데이터 삭제 (물리 삭제)
     *  => 만일 해당 메타데이터가 KWARE 포탈 시스템에서 진열관리중인 메타데이터라면,
     *     KWARE 포탈 시스템에서도 삭제한다 (논리 삭제)
     *
     * @api         [POST] /api/portal/integration-dataset/delete-metadatas
     * @author      dahyeon
     * @date        2025-10-23
    **/
    @PostMapping("/delete-metadatas")
    public ResponseEntity deleteMetadatas(@RequestBody DeleteMetadatas request) {
        service.deleteMetadatas(request);
        return ResponseEntity.ok().build();
    }

    /**
     * 
     * [RAWDATA_03] 여러건의 원본데이터 삭제
     * => 해당 원본데이터파일에 대해서 kware 포탈 시스템에서도 삭제
     * 
     * @api         [POST] /api/portal/integration-dataset/delete-rawdatas
     * @author      dahyeon
     * @date        2025-10-23
    **/
    @PostMapping("/delete-rawdatas")
    public ResponseEntity deleteRawdatas(@RequestBody DeleteRawdatas request) {
        service.deleteRawdatas(request);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * [RAWDATA_05] 원본데이터 단건 수정
     *
     * @api         [POST] /api/portal/integration-dataset/update-rawdata
     * @author      dahyeon
     * @date        2025-10-23
    **/
    @PostMapping("/update-rawdata")
    public ResponseEntity updateRawdata(@RequestBody ChangeRawdata request) {
        service.updateRawdata(request);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * [METADATA_04] 메타데이터 상세 조회
     *
     * @api         [POST] /api/portal/integration-dataset/view-metadata
     * @author      dahyeon
     * @date        2025-10-24
    **/
    @PostMapping("/view-metadata")
    public ResponseEntity viewMetadata(@RequestBody SearchMetadataView search) {
        MetadataView view = service.viewMetadata(search, true, true, true);
        return ResponseEntity.ok(view);
    }

    /**
     *
     * [RAWDATA_04] 원본데이터 파일 정보 상세 조회
     *
     * @api         [POST] /api/portal/integration-dataset/view-rawdata
     * @author      dahyeon
     * @date        2025-10-24
    **/
    @PostMapping("/view-rawdata")
    public ResponseEntity viewRawdata(@RequestBody SearchRawdataView search) {
        RawdataView view = service.viewRawdata(search);
        return ResponseEntity.ok(view);
    }

    /**
     *
     * [METADATA_01] 메타데이터 목록 조회 (+) 페이징
     *
     * @api         [GET] /api/portal/integration-dataset/metadata-page
     * @author      dahyeon
     * @date        2025-10-24
    **/
    @GetMapping("/metadata-page")
    public ResponseEntity pageMetadata(SearchMetadataPage search) {
        Page<MetadataList> page = service.pageMetadata(search);
        return ResponseEntity.ok(page);
    }


    /**
     *
     * [RAWDATA_01] 원본데이터 목록 조회 (+) 페이징
     *
     * @api         [GET] /api/portal/integration-dataset/rawdata-page
     * @author      dahyeon
     * @date        2025-10-24
    **/
    @GetMapping("/rawdata-page")
    public ResponseEntity pageRawdata(SearchRawdataPage search) {
        Page<RawdataList> page = service.pageRawdata(search);
        return ResponseEntity.ok(page);
    }

    /**
     * 
     * [RELATION_01] 연관 메타데이터 목록 조회 (+) 페이징
     * 
     * @api         [GET] /api/portal/integration-dataset/relations-page
     * @author      dahyeon
     * @date        2025-10-24
    **/
    @GetMapping("/relations-page")
    public ResponseEntity pageRelations(SearchRelationsPage search) {
        Page<RelationsList> page = service.pageRelations(search);
        return ResponseEntity.ok(page);
    }

    /**
     *
     * [RECOMMENDATION_01] 추천 메타데이터 목록 조회 (+) 페이징
     *
     * @api         [GET] /api/portal/integration-dataset/recommendation-page
     * @author      dahyeon
     * @date        2025-10-27
    **/
    @GetMapping("/recommendation-page")
    public ResponseEntity pageRecommendation(SearchRecommendationPage search) {
        Page<RecommendationList> page = service.pageRecommendation(search);
        return ResponseEntity.ok(page);
    }

    /**
     *
     * [PACKAGE_01] 패키지 파일 다운로드
     *
     * @api         [POST] /api/portal/integration-dataset/download-package
     * @author      dahyeon
     * @date        2025-10-24
    **/
    @PostMapping("/download-package")
    public ResponseEntity<Resource> downloadPackage(@RequestBody DownloadPackageDataset request) {
        return service.downloadPackage(request);
    }

    /**
     *
     *  [METADATA_08] 메타데이터 파일 다운로드
     *
     * @api         [GET] /api/portal/integration-dataset/download-metadata
     * @author      dahyeon
     * @date        2025-10-24
    **/
    @GetMapping("/download-metadata")
    public ResponseEntity<Resource> downloadMetadata(DownloadMetadata request, final HttpServletRequest req) throws IOException {
        return service.downloadMetadata(request, req);
    }

    /**
     *
     * [RAWDATA_07] 원본데이터 파일 다운로드
     *
     * @api         [GET] /api/portal/integration-dataset/download-rawdata
     * @author      dahyeon
     * @date        2025-10-24
    **/
    @GetMapping("/download-rawdata")
    public ResponseEntity<Resource> downloadRawdata(DownloadRawdata request, final HttpServletRequest req) throws IOException {
        return service.downloadRawdata(request, req);
    }

    /**
     *
     * [META_01] 메타데이터로 사용되는 필터 KEY 값의 목록 정보 조회
     *
     * @api         [GET] /api/portal/integration-dataset/metakey-list
     * @author      dahyeon
     * @date        2025-10-27
    **/
    @GetMapping("/metakey-list")
    public ResponseEntity findMetaKeyList() {
        MetaKeyList keyList = service.findMetaKeyList();
        return ResponseEntity.ok(keyList);
    }

    /**
     * 
     * [META_02] 메타데이터로 사용되는 필터 KEY 값의 VALUE 목록 정보 조회
     *
     * @api         [GET] /api/portal/integration-dataset/metakey-value-list
     * @author      dahyeon
     * @date        2025-10-27
    **/
    @GetMapping("/metakey-value-list")
    public ResponseEntity findMetaKeyValueList(SearchMetaValues search) {
        MetaKeyValueList keyValueList = service.findMetaKeyValueList(search);
        return ResponseEntity.ok(keyValueList);
    }

}