package com.company.controller

import com.company.domain.TagResponse
import com.company.service.TagService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.logging.Logger

@RestController
@RequestMapping("/tags")
class TagController(
    private val tagService: TagService
) {
    private val log = Logger.getLogger(this.javaClass.name)

    @GetMapping
    fun all(): List<TagResponse> {
        log.info("Request on getting tags")
        return tagService.all()
    }
}
