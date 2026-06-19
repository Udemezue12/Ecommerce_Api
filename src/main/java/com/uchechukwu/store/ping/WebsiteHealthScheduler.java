package com.uchechukwu.store.ping;

import com.uchechukwu.store.configProperties.PingerProperties;
import com.uchechukwu.store.dtos.response.PingResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebsiteHealthScheduler {


    private final WebsitePingService pingService;
    private final PingerProperties properties;


    @Scheduled(fixedDelay = 300000)
    public void monitorWebsites() {

        properties.pingUrls().forEach(url -> {

            PingResult result = pingService.ping(url);

            if (!result.healthy()) {

                log.error(
                        "Website DOWN: {} response={}ms",
                        url,
                        result.responseTimeMs()
                );

            } else {

                log.info(
                        "Website UP: {} status={} response={}ms",
                        url,
                        result.statusCode(),
                        result.responseTimeMs()
                );
            }
        });
    }
}


