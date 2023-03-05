package com.company.util

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import java.util.concurrent.TimeUnit

@Component
class AliveUtil(
    private val webclient: WebClient
) {

    /**
     * Free render server shuts down instance if it's not called
     * */
    @Scheduled(fixedDelay = 5, timeUnit = TimeUnit.SECONDS)
    fun aliveScheduler() {
        webclient.get()
            .uri("/alive")
            .retrieve()
            .bodyToMono(String::class.java)
            .block()
    }
}
