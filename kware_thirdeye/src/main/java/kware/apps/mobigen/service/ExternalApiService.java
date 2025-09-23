package kware.apps.mobigen.service;


import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import kware.apps.mobigen.dto.request.meta.SearchMetaValues;
import kware.apps.mobigen.dto.request.metadata.ChangeMetadata;
import kware.apps.mobigen.dto.request.metadata.CreateMetadata;
import kware.apps.mobigen.dto.request.metadata.DeleteMetadatas;
import kware.apps.mobigen.dto.request.metadata.SearchMetadataList;
import kware.apps.mobigen.dto.request.pckge.PackageExportRequest;
import kware.apps.mobigen.dto.request.rawdata.ChangeRawdata;
import kware.apps.mobigen.dto.request.rawdata.SearchRawdataList;
import kware.apps.mobigen.dto.response.ApiResponse;
import kware.apps.mobigen.dto.response.meta.MetaKeysList;
import kware.apps.mobigen.dto.response.meta.MetaValuesList;
import kware.apps.mobigen.dto.response.metadata.*;
import kware.apps.mobigen.dto.response.pckge.PackageExport;
import kware.apps.mobigen.dto.response.pckge.PackageImport;
import kware.apps.mobigen.dto.response.pckge.PackagePreview;
import kware.apps.mobigen.dto.response.rawdata.*;
import kware.apps.mobigen.dto.response.recommendation.RecommendationList;
import kware.apps.mobigen.dto.response.relation.RelationList;
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

import java.nio.file.Path;
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

    private <T> ApiResponse<T> get(String uri, ParameterizedTypeReference<ApiResponse<T>> typeRef) {
        return WEB_CLIENT
                .get()
                .uri(uri)
                .retrieve()
                .onStatus(HttpStatus::isError, response ->
                        response.bodyToMono(String.class)
                                .doOnNext(bodyStr -> log.error("[get] API 오류: status={} body={}", response.statusCode(), bodyStr))
                                .then(Mono.empty())
                )
                .bodyToMono(typeRef)
                .block();
    }

    private <T> ApiResponse<T> delete(String uri, ParameterizedTypeReference<ApiResponse<T>> typeRef) {
        return WEB_CLIENT
                .delete()
                .uri(uri)
                .retrieve()
                .onStatus(HttpStatus::isError, response ->
                        response.bodyToMono(String.class)
                                .doOnNext(bodyStr -> log.error("[delete] API 오류: status={} body={}", response.statusCode(), bodyStr))
                                .then(Mono.empty())
                )
                .bodyToMono(typeRef)
                .block();
    }

    /**
     * @method      [1] packageExport
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 패키지 파일 다운로드
    **/
    public ApiResponse<PackageExport> packageExport(PackageExportRequest request) {
        return post("/package/export", request, new ParameterizedTypeReference<ApiResponse<PackageExport>>() {});
    }

    /**
     * @method      [2] packageImport
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 패키지 파일 업로드
    **/
    public ApiResponse<PackageImport> packageImport(Path filePath) {
        return upload("/package/import", filePath, new ParameterizedTypeReference<ApiResponse<PackageImport>>() {});
    }

    /**
     * @method      [3] findPackagePreview
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 패키지 파일 미리보기
     *              =>> 패키지 파일의 내용을 JSON 형태로 변경하여 리턴
    **/
    public ApiResponse<PackagePreview> findPackagePreview(Path filePath) {
        return upload("/package/preview", filePath, new ParameterizedTypeReference<ApiResponse<PackagePreview>>() {});
    }

    /**
     * @method      [4] findMetadataList
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 전체 메타데이터 목록 조회 + 필터링 조건
    **/
    public ApiResponse<MetadataList> findMetadataList(SearchMetadataList search) {
        return post("/metadata", search, new ParameterizedTypeReference<ApiResponse<MetadataList>>() {});
    }

    /**
     * @method      [5] deleteSeveralMetadata
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 메타데이터 삭제 (다수)
    **/
    public ApiResponse<DeleteMetadataResponse> deleteSeveralMetadata(DeleteMetadatas request) {
        return post("/metadata", request, new ParameterizedTypeReference<ApiResponse<DeleteMetadataResponse>>() {});
    }

    /**
     * @method      [6] createMetadata
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 메타데이터 생성
    **/
    public ApiResponse<CreateMetadataResponse> createMetadata(CreateMetadata request) {
        return post("/metadata", request, new ParameterizedTypeReference<ApiResponse<CreateMetadataResponse>>() {});
    }


    /**
     * @method      [7] findMetadataById
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 특정 메타데이터 상세 정보 조회
    **/
    public ApiResponse<ViewMetadata> findMetadataById(String metadata_id) {
        String uri = String.format("/metadata/%s", metadata_id);
        return get(uri, new ParameterizedTypeReference<ApiResponse<ViewMetadata>>() {});
    }

    /**
     * @method      [8] changeMetadata
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 특정 메타데이터 정보 업데이트
    **/
    public ApiResponse<ChangeMetadataResponse> changeMetadata(String metadata_id, ChangeMetadata request) {
        String uri = String.format("/metadata/%s", metadata_id);
        return post(uri, request, new ParameterizedTypeReference<ApiResponse<ChangeMetadataResponse>>() {});
    }

    /**
     * @method      [9] deleteSingleMetadata
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 특정 메타데이터 삭제 (단건)
    **/
    public ApiResponse<DeleteMetadataResponse> deleteSingleMetadata(String metadata_id) {
        String uri = String.format("/metadata/%s", metadata_id);
        return delete(uri, new ParameterizedTypeReference<ApiResponse<DeleteMetadataResponse>>() {});
    }

    /**
     * @method      [10] findRawdataList
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 특정 메타데이터 하위 전체 원본데이터 목록 조회
    **/
    public ApiResponse<RawdataList> findRawdataList(String metadata_id, SearchRawdataList search) {
        String uri = String.format("/metadata/%s/rawdata", metadata_id);
        return post(uri, search, new ParameterizedTypeReference<ApiResponse<RawdataList>>() {});
    }

    /**
     * @method      [11] createRawdata
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 메타데이터 하위 원본데이터 추가 
    **/
    public ApiResponse<CreateMetadata> createRawdata(String metadata_id, Path filePath) {
        String uri = String.format("/metadata/%s/rawdata", metadata_id);
        return upload(uri, filePath, new ParameterizedTypeReference<ApiResponse<CreateMetadata>>() {});
    }
    
    /**
     * @method      [12] deleteSeveralRawdata
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 메타데이터 하위 원본데이터 삭제 (다수)
    **/
    public ApiResponse<DeleteRawdatasResponse> deleteSeveralRawdata(String metadata_id, DeleteMetadatas request) {
        String uri = String.format("/metadata/%s/rawdata", metadata_id);
        return post(uri, request, new ParameterizedTypeReference<ApiResponse<DeleteRawdatasResponse>>() {});
    }
    
    /**
     * @method      [13] findRawdataById
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 메타데이터 하위 원본데이터 상세 정보 조회
    **/
    public ApiResponse<ViewRawdata> findRawdataById(String metadata_id, String rawdata_id) {
        String uri = String.format("/metadata/%s/rawdata/%s", metadata_id, rawdata_id);
        return get(uri, new ParameterizedTypeReference<ApiResponse<ViewRawdata>>() {});
    }

    /**
     * @method      [14] changeRawdata
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 메타데이터 하위 원본데이터 단건 수정
    **/
    public ApiResponse<ChangeMetadataResponse> changeRawdata(String metadata_id, String rawdata_id, ChangeRawdata request) {
        String uri = String.format("/metadata/%s/rawdata/%s", metadata_id, rawdata_id);
        return post(uri, request, new ParameterizedTypeReference<ApiResponse<ChangeMetadataResponse>>() {});
    }
    
    /**
     * @method      [15] deleteSingleRawdata
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 메타데이터 하위 원본데이터 삭제 (단건)
    **/
    public ApiResponse<DeleteRawdataResponse> deleteSingleRawdata(String metadata_id, String rawdata_id) {
        String uri = String.format("/metadata/%s/rawdata/%s", metadata_id, rawdata_id);
        return delete(uri, new ParameterizedTypeReference<ApiResponse<DeleteRawdataResponse>>() {});
    }
    
    /**
     * @method      [16] downloadRawdata
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 메타데이터 하위 원본데이터 파일 다운로드
    **/
    public ApiResponse<DownloadRawdataResponse> downloadRawdata(String metadata_id, String rawdata_id) {
        String uri = String.format("/metadata/%s/rawdata/%s/download", metadata_id, rawdata_id);
        return get(uri, new ParameterizedTypeReference<ApiResponse<DownloadRawdataResponse>>() {});
    }

    /**
     * @method      [17] findRelationDataList
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 메타데이터에 연관된 메타데이터 목록 조회
    **/
    public ApiResponse<RelationList> findRelationDataList(String metadata_id) {
        String uri = String.format("/metadata/%s/relations", metadata_id);
        return get(uri, new ParameterizedTypeReference<ApiResponse<RelationList>>() {});
    }

    /**
     * @method      [18] findRecommendationDataList
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 메타데이터에 대해 추천되는 메타데이터 목록 조회
    **/
    public ApiResponse<RecommendationList> findRecommendationDataList(String metadata_id) {
        String uri = String.format("/metadata/%s/recommendations", metadata_id);
        return get(uri, new ParameterizedTypeReference<ApiResponse<RecommendationList>>() {});
    }

    /**
     * @method      [19] findMetaKeysList
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 메타데이터로 사용되는 key 값의 목록 정보 조회
    **/
    public ApiResponse<MetaKeysList> findMetaKeysList() {
        return post("/meta/keys", null, new ParameterizedTypeReference<ApiResponse<MetaKeysList>>() {});
    }

    /**
     * @method      [20] findMetaValuesList
     * @author      dahyeon
     * @date        2025-09-23
     * @deacription 메타데이터로 사용되는 key 값의 value 정보 조회
    **/
    public ApiResponse<MetaValuesList> findMetaValuesList(SearchMetaValues search) {
        return post("/meta/values", search, new ParameterizedTypeReference<ApiResponse<MetaValuesList>>() {});
    }
}
