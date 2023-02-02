package com.company

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AliveController {

    @GetMapping
    fun alive(): String {
        return "alive"
    }
}