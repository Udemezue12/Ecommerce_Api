package com.uchechukwu.store.ping;


import com.uchechukwu.store.dtos.response.PingResult;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
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
    private final CircuitBreakerRegistry circuitBreakerRegistry;

    public PingResult ping(String url) {

        var start = System.nanoTime();

        try {
            var uri = URI.create(url);

            String breakerName = "website-ping-" + uri.getHost();

            var circuitBreaker = circuitBreakerRegistry.circuitBreaker(
                    breakerName,
                    "website-ping"
            );

            return circuitBreaker.executeSupplier(() -> doPing(uri, start));

        } catch (Exception ex) {
            return pingFallback(url, start, ex);
        }
    }

    private PingResult doPing(URI uri, long start) {

        return sharedRestClient
                .get()
                .uri(uri)
                .exchange((request, clientResponse) -> {
                    int status = clientResponse.getStatusCode().value();

                    long duration = TimeUnit.NANOSECONDS.toMillis(
                            System.nanoTime() - start
                    );
                    return new PingResult(
                            uri.toString(),
                            status,
                            duration,
                            status >= 200 && status < 300,
                            Instant.now(),
                            "HTTP response received"
                    );
                });
    }

    private PingResult pingFallback(
            String url,
            long start,
            Throwable exception
    ) {

        long duration = TimeUnit.NANOSECONDS.toMillis(
                System.nanoTime() - start
        );

        log.warn("Website ping failed for {}: {}", url,
                exception.toString());

        return new PingResult(
                url,
                0,
                duration,
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
                        connection -> connection.addHandlerFirst(new ReadTimeoutHandler(10, TimeUnit.SECONDS))
                );
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
