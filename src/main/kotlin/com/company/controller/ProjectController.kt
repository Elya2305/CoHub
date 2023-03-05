package com.company.controller

import com.company.domain.IdResponse
import com.company.domain.ProjectRequest
import com.company.domain.ProjectResponse
import com.company.entity.ProjectStatus
import com.company.service.ProjectService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.logging.Logger

@RestController
@RequestMapping("/projects")
class ProjectController(
    private val projectService: ProjectService
) {
    private val log = Logger.getLogger(this.javaClass.name)

    @PostMapping
    fun create(@RequestBody projectRequest: ProjectRequest): IdResponse {
        log.info("Request on creating project: $projectRequest")
        val response = projectService.create(projectRequest)
        log.info("Response on creating project: $response")
        return response
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: String): ProjectResponse {
        log.info("Request on getting project: $id")
        val response = projectService.get(id)
        log.info("Response on getting project: $response")
        return response
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: String, @RequestBody projectRequest: ProjectRequest): ProjectResponse {
        log.info("Request on updating project: $id, $projectRequest")
        val response = projectService.update(id, projectRequest)
        log.info("Response on updating project: $response")
        return response
    }

    @GetMapping
    fun allForUser(
        @RequestParam(value = "status", required = false) status: ProjectStatus?,
    ): List<ProjectResponse> {
        log.info("Request on getting all projects: status = $status")
        return projectService.allForUser(status)
    }

    @GetMapping("/open")
    fun allOpen(
        @RequestParam(value = "tags", required = false) tags: List<String>?
    ): List<ProjectResponse> {
        log.info("Request on getting all projects: tags = $tags")
        return projectService.allOpen(tags)
    }

    @GetMapping("/all")
    fun allByUserAndStatus(
        @RequestParam("user_id", required = false) userId: String?,
        @RequestParam("status", required = false) status: ProjectStatus?
    ): List<ProjectResponse> {
        log.info("Request on getting all projects: userId = $userId, status = $status")
        return projectService.allByUserAndStatus(userId, status)
    }

    @PutMapping("/{id}/start")
    fun startProject(@PathVariable id: String) {
        log.info("Request on starting project: $id")
        projectService.startProject(id)
    }

    @PutMapping("/{id}/finish")
    fun finishProject(@PathVariable id: String) {
        log.info("Request on finishing project: $id")
        projectService.finishProject(id)
    }
}