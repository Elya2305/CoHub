package com.company.controller

import com.company.domain.RejectionReasonRequest
import com.company.domain.RequestForProjectResponse
import com.company.domain.RequestForUserResponse
import com.company.domain.UserContext
import com.company.service.RequestService
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
@RequestMapping("/requests")
class RequestController(
    private val requestService: RequestService
) {

    private val log = Logger.getLogger(this.javaClass.name)

    @GetMapping("/for_project")
    fun getForProject(@RequestParam("project_id") projectId: String): List<RequestForProjectResponse> {
        log.info("Get requests by project")
        return requestService.getForProject(projectId)
    }

    @PostMapping("/for_project")
    fun createForProject(@RequestParam("project_id") projectId: String) {
        log.info("Create request for project $projectId")
        requestService.createForProject(projectId)
    }

    @GetMapping("/for_user")
    fun getForUser(): List<RequestForUserResponse> {
        log.info("Get requests by user ${UserContext.getUserUuid()}")
        return requestService.getForUser()
    }

    @PutMapping("/{id}/accept")
    fun accept(@PathVariable id: String) {
        log.info("Accept request $id")
        requestService.accept(id)
    }

    @PutMapping("/{id}/reject")
    fun reject(@PathVariable id: String, @RequestBody request: RejectionReasonRequest) {
        log.info("Regect request $id")
        requestService.reject(id, request)
    }
}
