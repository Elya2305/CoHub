package com.company.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AliveController {

    @GetMapping("/alive")
    fun alive(): String {
        print("Alive!")
        return "alive"
    }
}