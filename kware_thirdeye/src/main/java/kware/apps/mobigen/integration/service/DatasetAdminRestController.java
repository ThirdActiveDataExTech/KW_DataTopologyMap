package kware.apps.mobigen.integration.service;

import kware.apps.mobigen.cetus.dataset.dto.response2.resMetadata07;
import kware.apps.mobigen.integration.dto.request.pckg.SaveMetadata;
import kware.apps.mobigen.integration.dto.request.pckg.SavePackageDataset;
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

}
