package kware.apps.mobigen.mobigen.service;


import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import kware.apps.mobigen.mobigen.dto.request.meta.SearchMetaValuesRequest;
import kware.apps.mobigen.mobigen.dto.request.metadata.*;
import kware.apps.mobigen.mobigen.dto.request.pckge.PackageExportRequest;
import kware.apps.mobigen.mobigen.dto.request.rawdata.*;
import kware.apps.mobigen.mobigen.dto.request.recommendation.SearchRecommendationListRequest;
import kware.apps.mobigen.mobigen.dto.request.relation.SearchRelationListRequest;
import kware.apps.mobigen.mobigen.dto.response.ApiResponse;
import kware.apps.mobigen.mobigen.dto.response.meta.MetaKeysListResponse;
import kware.apps.mobigen.mobigen.dto.response.meta.MetaValuesListResponse;
import kware.apps.mobigen.mobigen.dto.response.metadata.*;
import kware.apps.mobigen.mobigen.dto.response.pckge.PackageExportResponse;
import kware.apps.mobigen.mobigen.dto.response.pckge.PackageImportResponse;
import kware.apps.mobigen.mobigen.dto.response.rawdata.*;
import kware.apps.mobigen.mobigen.dto.response.recommendation.RecommendationListResponse;
import kware.apps.mobigen.mobigen.dto.response.relation.RelationListResponse;
import kware.apps.mobigen.mobigen.dto.url.ExternalUrl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class ExternalApiService {

    private final String BASE_URL = "https://api.example.com";

    private static final int CONNECT_TIMEOUT_MILLIS = 5000;   // 서버와의 TCP 연결 시도 시간. 5초 안에 서버 연결 안 되면 실패 처리
    private static final int READ_WRITE_TIMEOUT_MILLIS = 10000; // 연결 후 읽기/쓰기가 이 시간 안에 안 되면 실패
    private static final Duration RESPONSE_TIMEOUT = Duration.ofSeconds(10); // 연결된 후 전체 응답 대기 시간. 10초 안에 응답 없으면 실패 처리

    private final WebClient WEB_CLIENT = WebClient.builder()
            .baseUrl(BASE_URL)
            .clientConnector(new ReactorClientHttpConnector(    //  {WebClient}의 비동기 HTTP 클라이언트(Netty) 설정
                    HttpClient.create()
                            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, CONNECT_TIMEOUT_MILLIS)   // TCP 연결 자체가 몇 초 안에 안 되면 연결 실패 처리
                            .responseTimeout(RESPONSE_TIMEOUT)                                      // 서버와 연결은 됐지만, 서버가 느려서 응답이 10초 안에 안넘어오면 실패
                            .doOnConnected(conn ->
                                conn                                                                                          // 연결 후 동작을 정의 -> Netty ChannelHandler 추가를 통해 I/O 읽기 및 쓰기 타임아웃 설정
                                  .addHandlerLast(new ReadTimeoutHandler(READ_WRITE_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS))   // (읽기) 너무 읽는 시간이 오래 걸리면 실패 처리
                                  .addHandlerLast(new WriteTimeoutHandler(READ_WRITE_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS))  // (쓰기) 너무 쓰는데 오래 걸리면 실패 처리
                            )
            ))
            .build();

    private <T> ApiResponse<T> upload(String uri, Path filePath, ParameterizedTypeReference<ApiResponse<T>> typeRef) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", new FileSystemResource(filePath.toFile()));
        return WEB_CLIENT
                .post()
                .uri(uri)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .onStatus(HttpStatus::isError, response ->
                        response.bodyToMono(String.class)
                                .doOnNext(bodyStr ->
                                        log.error("[UPLOAD] API 오류: status={} body={}", response.statusCode(), bodyStr)
                                )
                                .then(Mono.empty())
                )
                .bodyToMono(typeRef)
                .block();
    }

    private <T> ApiResponse<T> post(String uri, Object body, ParameterizedTypeReference<ApiResponse<T>> typeRef) {
        return WEB_CLIENT
                .post()
                .uri(uri)
                .body(BodyInserters.fromValue(body))
                .retrieve()
                .onStatus(HttpStatus::isError, response ->
                        response.bodyToMono(String.class)
                                .doOnNext(bodyStr ->
                                        log.error("[POST] API 오류: status={} body={}", response.statusCode(), bodyStr)
                                )
                                .then(Mono.empty())
                )
                .bodyToMono(typeRef)
                .block();
    }

    public ApiResponse<?> uploadWithFile(String uri, MultipartBodyBuilder builder) {
        return WEB_CLIENT.post()
                .uri(uri)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<?>>() {})
                .block();
    }

    private byte[] download(String uri, Object body) {
        return WEB_CLIENT
                .post()
                .uri(uri)
                .body(BodyInserters.fromValue(body))
                .retrieve()
                .onStatus(HttpStatus::isError, response ->
                        response.bodyToMono(String.class)
                                .doOnNext(bodyStr ->
                                        log.error("[DOWNLOAD] API 오류: status={} body={}", response.statusCode(), bodyStr)
                                )
                                .then(Mono.empty())
                )
                .bodyToMono(byte[].class)
                .block();
    }

    /**
     * @method      [PACKAGE_01] packageExport
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 패키지 파일 다운로드
    **/
    public ApiResponse<PackageExportResponse> packageExport(PackageExportRequest request) {
        return post(ExternalUrl.PACKAGE_01, request, new ParameterizedTypeReference<ApiResponse<PackageExportResponse>>() {});
    }

    /**
     * @method      [PACKAGE_02] packageImport
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 패키지 파일 업로드 & 미리보기
     *
     * @request
     *      Content-Type: multipart/form-data
     *      Form fields:
     *      - file: binary (package.zip)
    **/
    public ApiResponse<PackageImportResponse> packageImport(Path filePath) {
        return upload(ExternalUrl.PACKAGE_02, filePath, new ParameterizedTypeReference<ApiResponse<PackageImportResponse>>() {});
    }


    /**
     * @method      [METADATA_01] findMetadataList
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 전체 메타데이터 목록 조회 + 필터링 조건
    **/
    public ApiResponse<MetadataListResponse> findMetadataList(SearchMetadataListRequest search) {
        return post(ExternalUrl.METADATA_01, search, new ParameterizedTypeReference<ApiResponse<MetadataListResponse>>() {});
    }

    /**
     * @method      [METADATA_02] deleteSeveralMetadata
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 메타데이터 삭제 (다수)
    **/
    public ApiResponse<DeleteMetadataResponse> deleteSeveralMetadata(DeleteMetadatasRequest request) {
        return post(ExternalUrl.METADATA_02, request, new ParameterizedTypeReference<ApiResponse<DeleteMetadataResponse>>() {});
    }

    /**
     * @method      [METADATA_03] createMetadata
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 메타데이터 생성
    **/
    public ApiResponse<CreateMetadataResponse> createMetadata(CreateMetadataRequest request) {
        return post(ExternalUrl.METADATA_03, request, new ParameterizedTypeReference<ApiResponse<CreateMetadataResponse>>() {});
    }


    /**
     * @method      [METADATA_04] findMetadataById
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 특정 메타데이터 상세 정보 조회
    **/
    public ApiResponse<ViewMetadataResponse> findMetadataById(SearchMetadataViewRequest search) {
        return post(ExternalUrl.METADATA_04, search, new ParameterizedTypeReference<ApiResponse<ViewMetadataResponse>>() {});
    }

    /**
     * @method      [METADATA_05] changeMetadata
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 특정 메타데이터 정보 업데이트
    **/
    public ApiResponse<ChangeMetadataResponse> changeMetadata(ChangeMetadataRequest request) {
        return post(ExternalUrl.METADATA_05, request, new ParameterizedTypeReference<ApiResponse<ChangeMetadataResponse>>() {});
    }

    /**
     * @method      [METADATA_07] previewMetadataFile
     * @author      dahyeon
     * @date        2025-10-15
     * @deacription 특정 메타데이터 파일 정보 미리보기
    **/
    public ApiResponse<MetadataFilePreviewResponse> previewMetadataFile(Path filePath) {
        return upload(ExternalUrl.METADATA_07, filePath, new ParameterizedTypeReference<ApiResponse<MetadataFilePreviewResponse>>() {});
    }

    /**
     * @method      [METADATA_08] downloadMetadataFile
     * @author      dahyeon
     * @date        2025-10-15
     * @deacription 특정 메타데이터 파일 다운로드
    **/
    public void downloadMetadataFile(DownloadMetadataFileRequest request) throws IOException {
        byte[] fileData = download(ExternalUrl.METADATA_08, request);
        Files.write(Paths.get("downloaded.zip"), fileData);
    }


    /**
     * @method      [RAWDATA_01] findRawdataList
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 특정 메타데이터 하위 전체 원본데이터 목록 조회
    **/
    public ApiResponse<RawdataListResponse> findRawdataList(SearchRawdataListRequest search) {
        return post(ExternalUrl.RAWDATA_01, search, new ParameterizedTypeReference<ApiResponse<RawdataListResponse>>() {});
    }

    /**
     * @method      [RAWDATA_02] uploadRawdata
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 메타데이터 하위 원본데이터 추가 (원본데이터 파일 업로드 진행)
    **/
    public ApiResponse<UploadRawdataResponse> uploadRawdata(UploadRawdataRequest request, Path filePath) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("action", request.getAction());
        builder.part("metadata_id", request.getMetadata_id());
        builder.part("rawdata_format", request.getRawdata_format());
        builder.part("file", new FileSystemResource(filePath))
                .filename(filePath.getFileName().toString())
                .contentType(MediaType.TEXT_PLAIN); // or MediaType.APPLICATION_OCTET_STREAM
        return upload(ExternalUrl.RAWDATA_02, filePath, new ParameterizedTypeReference<ApiResponse<UploadRawdataResponse>>() {});
    }
    
    /**
     * @method      [RAWDATA_03] deleteSeveralRawdata
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 메타데이터 하위 원본데이터 삭제 (다수)
    **/
    public ApiResponse<DeleteRawdatasResponse> deleteSeveralRawdata(DeleteRawdatasRequest request) {
        return post(ExternalUrl.RAWDATA_03, request, new ParameterizedTypeReference<ApiResponse<DeleteRawdatasResponse>>() {});
    }
    
    /**
     * @method      [RAWDATA_04] findRawdataById
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 메타데이터 하위 원본데이터 상세 정보 조회
    **/
    public ApiResponse<ViewRawdataResponse> findRawdataById(SearchRawdataViewRequest request) {
        return post(ExternalUrl.RAWDATA_04, request, new ParameterizedTypeReference<ApiResponse<ViewRawdataResponse>>() {});
    }

    /**
     * @method      [RAWDATA_05] changeRawdata
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 메타데이터 하위 원본데이터 단건 수정
    **/
    public ApiResponse<ChangeRawdataResponse> changeRawdata(ChangeRawdataRequest request) {
        return post(ExternalUrl.RAWDATA_05, request, new ParameterizedTypeReference<ApiResponse<ChangeRawdataResponse>>() {});
    }
    
    /**
     * @method      [RAWDATA_07] downloadRawdata
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 메타데이터 하위 원본데이터 파일 다운로드
    **/
    public void downloadRawdata(DownloadRawdataRequest request) throws IOException {
        byte[] fileData = download(ExternalUrl.RAWDATA_07, request);
        Files.write(Paths.get("downloaded.zip"), fileData);
    }


    /**
     * @method      [RELATION_01] findRelationDataList
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 메타데이터에 연관된 메타데이터 목록 조회
    **/
    public ApiResponse<RelationListResponse> findRelationDataList(SearchRelationListRequest request) {
        return post(ExternalUrl.RELATION_01, request, new ParameterizedTypeReference<ApiResponse<RelationListResponse>>() {});
    }

    /**
     * @method      [RECOMMENDATION_01] findRecommendationDataList
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 메타데이터에 대해 추천되는 메타데이터 목록 조회
    **/
    public ApiResponse<RecommendationListResponse> findRecommendationDataList(SearchRecommendationListRequest request) {
        return post(ExternalUrl.RECOMMENDATION_01, request, new ParameterizedTypeReference<ApiResponse<RecommendationListResponse>>() {});
    }

    /**
     * @method      [META_01] findMetaKeysList
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 메타데이터로 사용되는 key 값의 목록 정보 조회
    **/
    public ApiResponse<MetaKeysListResponse> findMetaKeysList() {
        return post(ExternalUrl.META_01, null, new ParameterizedTypeReference<ApiResponse<MetaKeysListResponse>>() {});
    }

    /**
     * @method      [META_02] findMetaValuesList
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 메타데이터로 사용되는 key 값의 value 정보 조회
    **/
    public ApiResponse<MetaValuesListResponse> findMetaValuesList(SearchMetaValuesRequest search) {
        return post(ExternalUrl.META_02, search, new ParameterizedTypeReference<ApiResponse<MetaValuesListResponse>>() {});
    }
}
