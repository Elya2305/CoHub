package com.company.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.logging.Logger

@RestController
class AliveController {
    private val log = Logger.getLogger(this.javaClass.name)

    @GetMapping("/alive")
    fun alive(): String {
        log.info("Alive!")
        return "alive"
    }
}
