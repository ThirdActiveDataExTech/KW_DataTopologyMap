package kware.apps.mobigen.integration.service;

import cetus.util.StringUtil;
import kware.apps.mobigen.cetus.dataset.domain.CetusMobigenDataset;
import kware.apps.mobigen.cetus.dataset.service.CetusMobigenDatasetService;
import kware.apps.mobigen.cetus.tag.service.CetusMobigenDatasetTagService;
import kware.apps.mobigen.integration.dto.request.metadata.ChangeMetadata;
import kware.apps.mobigen.integration.dto.request.metadata.DeleteMetadatas;
import kware.apps.mobigen.integration.dto.request.metadata.SaveMetadata;
import kware.apps.mobigen.integration.dto.request.pckg.SavePackageDataset;
import kware.apps.mobigen.integration.dto.request.rawdata.ChangeRawdata;
import kware.apps.mobigen.integration.dto.request.rawdata.DeleteRawdatas;
import kware.apps.mobigen.mobigen.dto.request.rawdata.ChangeRawdataRequest;
import kware.apps.mobigen.mobigen.dto.request.rawdata.DeleteRawdatasRequest;
import kware.apps.mobigen.mobigen.dto.response.ApiResponse;
import kware.apps.mobigen.mobigen.dto.response.metadata.MetadataFilePreviewResponse;
import kware.apps.mobigen.mobigen.dto.response.rawdata.ChangeRawdataResponse;
import kware.apps.mobigen.mobigen.dto.response.rawdata.DeleteRawdatasResponse;
import kware.apps.mobigen.mobigen.service.MobigenExternalApiService;
import kware.apps.thirdeye.mobigen.datasetfile.domain.CetusDatasetFile;
import kware.apps.thirdeye.mobigen.datasetfile.enumcd.DataFileTpCd;
import kware.apps.thirdeye.mobigen.datasetfile.service.CetusDatasetFileService;
import kware.apps.thirdeye.mobigen.mobigenregistrant.service.CetusMobigenRegistrantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

/**
* @fileName     DatasetSendService
* @author       dahyeon
* @version      1.0.0
* @date         2025-10-23
* @summary      { kware 백엔드 -> 모비젠 백엔드 } 접근을 위한 서비스
**/

@Slf4j
@Service
@RequiredArgsConstructor
public class DatasetService {

    private final MobigenExternalApiService apiService;
    private final CetusDatasetFileService datasetFileService;
    private final CetusMobigenRegistrantService registrantService;

    // 추후 삭제
    private final CetusMobigenDatasetService mobigenDatasetService;
    private final CetusMobigenDatasetTagService tagService;

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
        Long datasetId = bean.getUid();
        log.info(">>>> [savePackageDataset] datasetId : {} ", datasetId);

        String metadataId = Long.toString(datasetId); //apiResponse.getResult().getMetadata().getMetadata_id();
        String rawdataId = "raw_" + datasetId + "_" + StringUtil.random(3); //apiResponse.getResult().getRawdata().getRawdata_id();

        // 1. save [PACKAGE] data file
        CetusDatasetFile metaFile = request.getPackageFile();
        metaFile.setMetadataId(metadataId);
        metaFile.setRawdataId(rawdataId);
        metaFile.setDataTpCd(DataFileTpCd.PACKAGE.name());
        datasetFileService.processAddFile(metaFile);

        // 3. 모비젠 등록자 정보 저장
        registrantService.saveMobigenRegistrant(datasetId); //apiResponse.getResult().getMetadata().getMetadata_id();
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
        Long datasetId = bean.getUid();
        log.info(">>>> [createMetadata] datasetId : {} ", datasetId);

        String metadataId = Long.toString(datasetId); //apiResponse.getResult().getMetadata().getMetadata_id();
        String rawdataId = "raw_" + datasetId + "_" + StringUtil.random(3); //apiResponse.getResult().getRawdata().getRawdata_id();

        // 1. save metadata file
        CetusDatasetFile metaFile = request.getMetaFile();
        metaFile.setMetadataId(metadataId);
        metaFile.setDataTpCd(DataFileTpCd.METADATA.name());
        datasetFileService.processAddFile(metaFile);

        if( !realFileData.isEmpty() ) {
            // 2. save realdata file
            CetusDatasetFile realFile = request.getRealFile();
            realFile.setMetadataId(metadataId);
            realFile.setDataTpCd(DataFileTpCd.RAWDATA.name());
            realFile.setRawdataId(rawdataId);
            datasetFileService.processAddFile(realFile);
        }

        // 3. save tag
        tagService.saveDatasetTag(request.getTags(), datasetId);

        // 4. 모비젠 등록자 정보 저장
        registrantService.saveMobigenRegistrant(datasetId);
    }

    @Transactional
    public void updateMetadata( ChangeMetadata request, MultipartFile realFileData ) throws IOException {

        Long datasetId = request.getDatasetId();

        /*ChangeMetadataRequest changeRequest = new ChangeMetadataRequest(
                Long.toString(datasetId), new ChangeMetadataRequest.ChangeMetadataFieldRequest(request.getTitle())
        );
        ApiResponse<ChangeMetadataResponse> apiResponse1 = apiService.changeMetadata(changeRequest);*/

        // >> update mobigen data
        CetusMobigenDataset bean = new CetusMobigenDataset(datasetId, request);
        mobigenDatasetService.update(bean);

        // >> save tag
        tagService.saveDatasetTag(request.getTags(), datasetId);


        // 2. if not empty upload real-file-data
        if(!realFileData.isEmpty()) {
            /*UploadRawdataRequest uploadRequest = new UploadRawdataRequest(Long.toString(datasetId), realFileData.getContentType());
            Path realFilePath = this.convertToPath(realFileData);
            ApiResponse<UploadRawdataResponse> apiResponse2 = apiService.uploadRawdata(uploadRequest, realFilePath);*/
            String rawdataId = "raw_" + datasetId + "_" + StringUtil.random(3); //apiResponse2.getResult().getRawdata_id();

            CetusDatasetFile realFile = request.getRealFile();
            realFile.setMetadataId(Long.toString(datasetId));
            realFile.setDataTpCd(DataFileTpCd.RAWDATA.name());
            realFile.setRawdataId(rawdataId);
            datasetFileService.processAddFile(realFile);
        }
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

            // {datasetId} 데이터셋이 kware 포탈 시스템에서 관리중인 값이라면 포탈 시스템에서도 삭제 (논리삭제)
            mobigenDatasetService.ifExistDeleteApprovedDataset(Long.parseLong(metadataId));

            // 해당 파일 정보도 삭제
            datasetFileService.processDelFileByMetadataId(metadataId);
        }
    }

    @Transactional
    public void deleteRawdatas(DeleteRawdatas request) {

        /*DeleteRawdatasRequest deleteRequest = new DeleteRawdatasRequest(Long.toString(request.getMetadataId()), request.getRawdataIds());
        ApiResponse<DeleteRawdatasResponse> apiResponse = apiService.deleteSeveralRawdata(deleteRequest);
        int deletedCount = apiResponse.getResult().getDeleted_count();*/

        for (String rawdataId: request.getRawdataIds()) {
            datasetFileService.processDelFileByRawdataId(rawdataId);
        }
    }

    @Transactional
    public void updateRawdata(ChangeRawdata request) {
        /*ChangeRawdataRequest updateRequest = new ChangeRawdataRequest(request.getRawdataId(), new ChangeRawdataRequest.ChangeRawdataFieldRequest(request.getDescription(), request.getTags()));
        ApiResponse<ChangeRawdataResponse> apiResponse = apiService.changeRawdata(updateRequest);*/
    }
}
