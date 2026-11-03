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
    private final RestClient sharedRestClient;

    @CircuitBreaker(
            name = "website-ping",
            fallbackMethod = "pingFallback"
    )
    public PingResult ping(String url) {
        var start = System.currentTimeMillis();
        var response = sharedRestClient
                .get()
                .uri(url)
                .retrieve()
                .toBodilessEntity();

        var duration = System.currentTimeMillis() - start;
        var status = response.getStatusCode();

        return new PingResult(
                url,
                status.value(),
                duration,
                status.is2xxSuccessful(),
                Instant.now(),
                "Website responded successfully"
        );
    }

    public PingResult pingFallback(String url, Throwable exception) {

        log.error("Website ping failed for {}: {}", url, exception.getMessage());

        return new PingResult(
                url,
                0,
                0,
                false,
                Instant.now(),
                exception.getMessage()
        );
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
