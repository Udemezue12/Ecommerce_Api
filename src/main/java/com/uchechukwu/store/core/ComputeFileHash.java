package com.uchechukwu.store.core;


import com.uchechukwu.store.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

@Slf4j
@Service
@RequiredArgsConstructor
public class ComputeFileHash {

    private final WebClient.Builder webClientBuilder;

    public Mono<String> computeFileHashAsync(String fileUrl) {
        return Mono.defer(() -> {
            try {

                MessageDigest digest = MessageDigest.getInstance("SHA-256");

                return webClientBuilder.build()
                        .get()
                        .uri(fileUrl)
                        .retrieve()
                        .onStatus(HttpStatusCode::isError, response ->
                                Mono.error(new BadRequestException("Failed to fetch file from URL")))
                        .bodyToFlux(DataBuffer.class)
                        .doOnNext(dataBuffer -> {

                            digest.update(dataBuffer.asByteBuffer());
                            DataBufferUtils.release(dataBuffer);
                        })
                        .then(Mono.fromCallable(() -> HexFormat.of().formatHex(digest.digest())));

            } catch (NoSuchAlgorithmException e) {
                return Mono.error(e);
            }
        });
    }


    public String computeFileHashSync(String fileUrl) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            URL url = URI.create(fileUrl).toURL();


            try (InputStream is = url.openStream()) {
                byte[] buffer = new byte[8192]; // 8KB read window chunks
                int bytesRead;
                while ((bytesRead = is.read(buffer)) != -1) {
                    digest.update(buffer, 0, bytesRead);
                }
            }
            return HexFormat.of().formatHex(digest.digest());

        } catch (Exception e) {
            log.error("Sync file hash failed for URL: {}", fileUrl, e);
            throw new BadRequestException("Failed to fetch file");
        }
    }


    public Mono<String> computeFileHash(String fileUrl) {
        return Mono.fromCallable(() -> computeFileHashSync(fileUrl))

                .subscribeOn(Schedulers.boundedElastic())
                .onErrorResume(Exception.class, ex -> {
                    if (ex instanceof BadRequestException) {
                        return Mono.error(ex);
                    }
                    log.warn("Sync hashing failed, attempting fallback to Async Client Session: {}", ex.getMessage());
                    return computeFileHashAsync(fileUrl);
                });
    }
}
