package com.company.controller

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.TimeUnit.SECONDS
import java.util.logging.Logger

@RestController
class AliveController {
    private val log = Logger.getLogger(this.javaClass.name)

    @GetMapping("/alive")
    fun alive(): String {
        log.info("Alive!")
        return "alive"
    }

    /**
     * Free render server shuts down instance if it's not called
    * */
    @Scheduled(fixedDelay = 5, timeUnit = SECONDS)
    fun aliveScheduler() {
        println("Still alive!")
    }
}
