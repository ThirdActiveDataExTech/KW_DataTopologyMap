package kware.apps.mobigen.service;


import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

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

    public void createMetadata() {
        /*WEB_CLIENT
                .post()
                .uri("/create")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .bodyValue()*/
    }

    public void viewMetadata() {

    }

    public void changeMetadata() {

    }
}
