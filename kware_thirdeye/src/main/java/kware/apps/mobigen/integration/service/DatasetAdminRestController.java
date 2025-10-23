package kware.apps.mobigen.integration.service;

import kware.apps.mobigen.integration.dto.request.metadata.ChangeMetadata;
import kware.apps.mobigen.integration.dto.request.metadata.DeleteMetadatas;
import kware.apps.mobigen.integration.dto.request.metadata.SaveMetadata;
import kware.apps.mobigen.integration.dto.request.pckg.SavePackageDataset;
import kware.apps.mobigen.integration.dto.request.rawdata.ChangeRawdata;
import kware.apps.mobigen.integration.dto.request.rawdata.DeleteRawdatas;
import kware.apps.mobigen.mobigen.dto.response.ApiResponse;
import kware.apps.mobigen.mobigen.dto.response.metadata.MetadataFilePreviewResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/integration-dataset")
public class DatasetAdminRestController {

    private final DatasetService service;

    /**
     *
     * [PACKAGE_02] 패키지 데이터셋 업로드
     *
     * @api         [POST] /api/admin/integration-dataset/import-package
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
     * @api         [POST] /api/admin/integration-dataset/metadata-preview
     * @author      dahyeon
     * @date        2025-10-23
    **/
    @PostMapping("/metadata-preview")
    public ResponseEntity metadataPreview(@RequestParam("file") MultipartFile file) throws IOException {
        if(file.isEmpty()) {
            return ResponseEntity.badRequest().body("no file founded");
        }
        ApiResponse<MetadataFilePreviewResponse> apiResponse = service.previewMetadata(file);
        /*return ResponseEntity.ok(apiResponse);*/
        return ResponseEntity.ok("todo mobigen [METADATA_07 API] connect ...");
    }

    /**
     *
     * [METADATA_03] 메타데이터 생성
     * (+) 원본데이터 파일 업로드  [선택]
     *
     *
     * @api         [POST] /api/admin/integration-dataset/create-metadata
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
     * @api         [POST] /api/admin/integration-dataset/update-metadata
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
     * @api         [POST] /api/admin/integration-dataset/delete-metadatas
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
     * @api         [POST] /api/admin/integration-dataset/delete-rawdatas
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
     * @api         [POST] /api/admin/integration-dataset/update-rawdata
     * @author      dahyeon
     * @date        2025-10-23
    **/
    @PostMapping("/update-rawdata")
    public ResponseEntity updateRawdata(@RequestBody ChangeRawdata request) {
        service.updateRawdata(request);
        return ResponseEntity.ok().build();
    }

}
