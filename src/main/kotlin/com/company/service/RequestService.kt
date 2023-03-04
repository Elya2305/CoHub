package com.company.service

import com.company.entity.Request
import com.company.repository.RequestRepository
import org.springframework.stereotype.Service

@Service
class RequestService(
    private val requestRepository: RequestRepository,
    private val userService: UserService,
    private val projectService: ProjectService,
) {
    fun create(projectId: String) {
        requestRepository.save(
            Request(
                user = userService.getCurrentReference(),
                project = projectService.getReferenceById(projectId)
            )
        )
    }
}
