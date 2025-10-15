package kware.apps.mobigen.mobigen;


import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import kware.apps.mobigen.mobigen.dto.request.meta.SearchMetaValues;
import kware.apps.mobigen.mobigen.dto.request.metadata.*;
import kware.apps.mobigen.mobigen.dto.request.pckge.PackageExportRequest;
import kware.apps.mobigen.mobigen.dto.request.rawdata.*;
import kware.apps.mobigen.mobigen.dto.request.recommendation.SearchRecommendationList;
import kware.apps.mobigen.mobigen.dto.request.relation.SearchRelationList;
import kware.apps.mobigen.mobigen.dto.response.ApiResponse;
import kware.apps.mobigen.mobigen.dto.response.meta.MetaKeysList;
import kware.apps.mobigen.mobigen.dto.response.meta.MetaValuesList;
import kware.apps.mobigen.mobigen.dto.response.metadata.*;
import kware.apps.mobigen.mobigen.dto.response.pckge.PackageExportResponse;
import kware.apps.mobigen.mobigen.dto.response.pckge.PackageImport;
import kware.apps.mobigen.mobigen.dto.response.rawdata.CreateRawdataResponse;
import kware.apps.mobigen.mobigen.dto.response.rawdata.DeleteRawdatasResponse;
import kware.apps.mobigen.mobigen.dto.response.rawdata.RawdataList;
import kware.apps.mobigen.mobigen.dto.response.rawdata.ViewRawdata;
import kware.apps.mobigen.mobigen.dto.response.recommendation.RecommendationList;
import kware.apps.mobigen.mobigen.dto.response.relation.RelationList;
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
                                .doOnNext(bodyStr -> log.error("[upload] API 오류: status={} body={}", response.statusCode(), bodyStr))
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
                                .doOnNext(bodyStr -> log.error("[post] API 오류: status={} body={}", response.statusCode(), bodyStr))
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

    private byte[] downloadFile(String uri, Object body) {
        return WEB_CLIENT
                .post()
                .uri(uri)
                .body(BodyInserters.fromValue(body))
                .retrieve()
                .onStatus(HttpStatus::isError, response ->
                        response.bodyToMono(String.class)
                                .doOnNext(bodyStr -> log.error("[downloadFile] API 오류: status={} body={}", response.statusCode(), bodyStr))
                                .then(Mono.empty())
                )
                .bodyToMono(byte[].class)
                .block();
    }

    /**
     * @method      [1] packageExport
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 패키지 파일 다운로드
    **/
    public ApiResponse<PackageExportResponse> packageExport(PackageExportRequest request) {
        return post("/package/export", request, new ParameterizedTypeReference<ApiResponse<PackageExportResponse>>() {});
    }

    /**
     * @method      [2] packageImport
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 패키지 파일 업로드 & 미리보기
     *
     * @request
     *      Content-Type: multipart/form-data
     *      Form fields:
     *      - file: binary (package.zip)
    **/
    public ApiResponse<PackageImport> packageImport(Path filePath) {
        return upload("/package/import", filePath, new ParameterizedTypeReference<ApiResponse<PackageImport>>() {});
    }


    /**
     * @method      [3] findMetadataList
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 전체 메타데이터 목록 조회 + 필터링 조건
    **/
    public ApiResponse<MetadataList> findMetadataList(SearchMetadataList search) {
        return post("/metadata", search, new ParameterizedTypeReference<ApiResponse<MetadataList>>() {});
    }

    /**
     * @method      [4] deleteSeveralMetadata
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 메타데이터 삭제 (다수)
    **/
    public ApiResponse<DeleteMetadataResponse> deleteSeveralMetadata(DeleteMetadatas request) {
        return post("/metadata", request, new ParameterizedTypeReference<ApiResponse<DeleteMetadataResponse>>() {});
    }

    /**
     * @method      [5] createMetadata
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 메타데이터 생성
    **/
    public ApiResponse<CreateMetadataResponse> createMetadata(CreateMetadata request) {
        return post("/metadata", request, new ParameterizedTypeReference<ApiResponse<CreateMetadataResponse>>() {});
    }


    /**
     * @method      [6] findMetadataById
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 특정 메타데이터 상세 정보 조회
    **/
    public ApiResponse<ViewMetadata> findMetadataById(SearchMetadataView search) {
        return post("/metadata", search, new ParameterizedTypeReference<ApiResponse<ViewMetadata>>() {});
    }

    /**
     * @method      [7] changeMetadata
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 특정 메타데이터 정보 업데이트
    **/
    public ApiResponse<ChangeMetadataResponse> changeMetadata(ChangeMetadata request) {
        return post("/metadata", request, new ParameterizedTypeReference<ApiResponse<ChangeMetadataResponse>>() {});
    }

    /**
     * @method      [8] previewMetadataFile
     * @author      dahyeon
     * @date        2025-10-15
     * @deacription 특정 메타데이터 파일 정보 미리보기
    **/
    public ApiResponse<MetadataFilePreviewResponse> previewMetadataFile(Path filePath) {
        return upload("/metadata/preview", filePath, new ParameterizedTypeReference<ApiResponse<MetadataFilePreviewResponse>>() {});
    }

    /**
     * @method      [9] downloadMetadataFile
     * @author      dahyeon
     * @date        2025-10-15
     * @deacription 특정 메타데이터 파일 다운로드
    **/
    public void downloadMetadataFile(DownloadMetadataFile request) throws IOException {
        byte[] fileData = downloadFile("/metadata/download", request);
        Files.write(Paths.get("downloaded.zip"), fileData);
    }


    /**
     * @method      [10] findRawdataList
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 특정 메타데이터 하위 전체 원본데이터 목록 조회
    **/
    public ApiResponse<RawdataList> findRawdataList(SearchRawdataList search) {
        return post("/metadata/rawdata", search, new ParameterizedTypeReference<ApiResponse<RawdataList>>() {});
    }

    /**
     * @method      [11] createRawdata
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 메타데이터 하위 원본데이터 추가
    **/
    public ApiResponse<CreateRawdataResponse> createRawdata(UploadRawdata request, Path filePath) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("action", request.getAction());
        builder.part("metadata_id", request.getMetadata_id());
        builder.part("rawdata_format", request.getRawdata_format());
        builder.part("file", new FileSystemResource(filePath))
                .filename(filePath.getFileName().toString())
                .contentType(MediaType.TEXT_PLAIN); // or MediaType.APPLICATION_OCTET_STREAM
        return upload("/metadata/rawdata", filePath, new ParameterizedTypeReference<ApiResponse<CreateRawdataResponse>>() {});
    }
    
    /**
     * @method      [12] deleteSeveralRawdata
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 메타데이터 하위 원본데이터 삭제 (다수)
    **/
    public ApiResponse<DeleteRawdatasResponse> deleteSeveralRawdata(DeleteMetadatas request) {
        return post("/metadata/rawdata", request, new ParameterizedTypeReference<ApiResponse<DeleteRawdatasResponse>>() {});
    }
    
    /**
     * @method      [13] findRawdataById
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 메타데이터 하위 원본데이터 상세 정보 조회
    **/
    public ApiResponse<ViewRawdata> findRawdataById(SearchRawdataView request) {
        return post("/metadata/rawdata", request, new ParameterizedTypeReference<ApiResponse<ViewRawdata>>() {});
    }

    /**
     * @method      [14] changeRawdata
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 메타데이터 하위 원본데이터 단건 수정
    **/
    public ApiResponse<ChangeMetadataResponse> changeRawdata(ChangeRawdata request) {
        return post("/metadata/rawdata", request, new ParameterizedTypeReference<ApiResponse<ChangeMetadataResponse>>() {});
    }
    
    /**
     * @method      [15] downloadRawdata
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 메타데이터 하위 원본데이터 파일 다운로드
    **/
    public void downloadRawdata(DownloadRawdata request) throws IOException {
        byte[] fileData = downloadFile("/metadata/rawdata/download", request);
        Files.write(Paths.get("downloaded.zip"), fileData);
    }


    /**
     * @method      [16] findRelationDataList
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 메타데이터에 연관된 메타데이터 목록 조회
    **/
    public ApiResponse<RelationList> findRelationDataList(SearchRelationList request) {
        return post("/metadata/relations", request, new ParameterizedTypeReference<ApiResponse<RelationList>>() {});
    }

    /**
     * @method      [17] findRecommendationDataList
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 메타데이터에 대해 추천되는 메타데이터 목록 조회
    **/
    public ApiResponse<RecommendationList> findRecommendationDataList(SearchRecommendationList request) {
        return post("/metadata/recommendations", request, new ParameterizedTypeReference<ApiResponse<RecommendationList>>() {});
    }

    /**
     * @method      [18] findMetaKeysList
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 메타데이터로 사용되는 key 값의 목록 정보 조회
    **/
    public ApiResponse<MetaKeysList> findMetaKeysList() {
        return post(" /metadata/filter/key", null, new ParameterizedTypeReference<ApiResponse<MetaKeysList>>() {});
    }

    /**
     * @method      [19] findMetaValuesList
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 메타데이터로 사용되는 key 값의 value 정보 조회
    **/
    public ApiResponse<MetaValuesList> findMetaValuesList(SearchMetaValues search) {
        return post("/metadata/filter/value", search, new ParameterizedTypeReference<ApiResponse<MetaValuesList>>() {});
    }
}
