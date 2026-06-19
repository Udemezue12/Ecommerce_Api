package com.uchechukwu.store.ping;


import com.uchechukwu.store.dtos.response.PingResult;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebsitePingService {
    private final RestClient restClient;


    @CircuitBreaker(name = "website-ping")
    public PingResult ping(String url) {
        var start = System.currentTimeMillis();
        try {
//            var status = webClient().
//                    get()
//                    .uri(url)
//                    .retrieve()
//                    .toBodilessEntity()
//                    .block()
//                    .getStatusCode();
            var response = restClient
                    .get()
                    .uri(url)
                    .retrieve()
                    .toBodilessEntity();

            var status = response.getStatusCode();
            var duration = System.currentTimeMillis() - start;
            var healthy = status.is2xxSuccessful();
            return new PingResult(
                    url,
                    status.value(),
                    duration,
                    healthy,
                    Instant.now(),
                    "Success"
            );
        } catch (Exception e) {
            var duration = System.currentTimeMillis() - start;

            log.error("Ping failed for {}", url, e);

            return new PingResult(
                    url,
                    0,
                    duration,
                    false,
                    Instant.now(),
                    e.getMessage());
        }
    }

    private WebClient webClient() {
        var httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 20000)
                .responseTimeout(Duration.ofSeconds(30))
                .doOnConnected(
                        connection -> {

                            connection.addHandlerFirst(new ReadTimeoutHandler(10, TimeUnit.SECONDS));
                        }
                );
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
