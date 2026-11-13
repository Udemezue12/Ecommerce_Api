package com.uchechukwu.store.ping;


import com.uchechukwu.store.core.TrimWhiteSpace;
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

import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebsitePingService {

    private final RestClient sharedRestClient;


    @CircuitBreaker(name = "website-ping")
    public PingResult ping(String url) {

        var start = System.currentTimeMillis();

        try {

            var uri = URI.create(normalizeUrl(url));

            var response = sharedRestClient.get()
                    .uri(uri)
                    .retrieve()
                    .toBodilessEntity();

            var duration = System.currentTimeMillis() - start;
            log.info("Ping Success for {}", url);

            return new PingResult(
                    uri.toString(),
                    response.getStatusCode().value(),
                    duration,
                    response.getStatusCode().is2xxSuccessful(),
                    Instant.now(),
                    "Success"
            );

        } catch (Exception ex) {

            var duration = System.currentTimeMillis() - start;

            log.error("Ping failed for {}", url, ex);

            return new PingResult(
                    url,
                    0,
                    duration,
                    false,
                    Instant.now(),
                    ex.getMessage()
            );
        }
    }

    private String normalizeUrl(String url) {

        if (url == null || url.isBlank()) {
            throw new IllegalArgumentException("URL cannot be blank");
        }

        url = TrimWhiteSpace.trimWhiteSpaceWithUpperCase(url, false);

        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "https://" + url;
        }

        return url;
    }


    private WebClient webClient() {
        var httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 20000)
                .responseTimeout(Duration.ofSeconds(30))
                .doOnConnected(
                        connection -> connection.addHandlerFirst(new ReadTimeoutHandler(10, TimeUnit.SECONDS))
                );
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
