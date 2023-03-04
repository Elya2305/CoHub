package com.company.controller

import com.company.service.RequestService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.logging.Logger

@RestController
@RequestMapping("/requests")
class RequestController(
    private val requestService: RequestService
) {

    private val log = Logger.getLogger(this.javaClass.name)

    @PostMapping("/for_project")
    fun create(@RequestParam("project_id") projectId: String) {
        log.info("Create request for project $projectId")
        requestService.create(projectId)
    }

    @GetMapping("/for_user")
    fun getForUser() {

    }


    @GetMapping("/for_project")
    fun getForProject() {

    }
}