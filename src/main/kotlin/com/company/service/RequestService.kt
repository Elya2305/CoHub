package com.company.service

import com.company.domain.RejectionReasonRequest
import com.company.domain.RequestForProjectResponse
import com.company.domain.RequestForUserResponse
import com.company.domain.ShortUserResponse
import com.company.domain.UserContext
import com.company.entity.ProjectStatus
import com.company.entity.Request
import com.company.entity.RequestStatus
import com.company.exception.EntityNotFoundException
import com.company.exception.ForbiddenActionException
import com.company.repository.RequestRepository
import org.springframework.stereotype.Service

@Service
class RequestService(
    private val requestRepository: RequestRepository,
    private val userService: UserService,
    private val projectService: ProjectService,
) {
    fun createForProject(projectId: String) {
        val project = projectService.get(projectId)
        if (project.isAuthor) {
            throw ForbiddenActionException("User can't create a request on project he owns")
        }
        if (project.status != ProjectStatus.OPEN) {
            throw ForbiddenActionException("Project is in ${project.status}")
        }
        requestRepository.save(
            Request(
                user = userService.getCurrentReference(),
                project = projectService.getReferenceById(projectId)
            )
        )
    }

    fun getForUser(): List<RequestForUserResponse> {
        return requestRepository.findAllByUser(userService.getCurrentReference())
            .map {
                RequestForUserResponse(
                    id = it.id,
                    title = it.project.title,
                    description = it.project.description,
                    tags = it.project.tags,
                    team = it.project.team + listOf(ShortUserResponse(it.project.author.id, it.project.author.pic)),
                    status = it.status,
                    rejectionReason = it.rejectionReason
                )
            }
    }

    fun getForProject(projectId: String): List<RequestForProjectResponse> {
        return requestRepository.findAllByProjectAndStatus(
            projectService.getReferenceById(projectId),
            RequestStatus.PENDING
        )
            .map {
                RequestForProjectResponse(
                    id = it.id,
                    userId = it.user.id,
                    username = it.user.username,
                    skills = it.user.skills
                )
            }
    }

    fun accept(id: String) {
        val request = fetchFromDb(id)
        if (request.project.author.id != UserContext.getUserUuid()) {
            throw ForbiddenActionException("User is not authorized to perform an action")
        }
        request.status = RequestStatus.ACCEPTED
        projectService.addUserToTeam(request.user.id, request.project.id)
        requestRepository.save(request)
    }

    fun reject(id: String, rejectionReasonRequest: RejectionReasonRequest) {
        val request = fetchFromDb(id)
        if (request.project.author.id != UserContext.getUserUuid()) {
            throw ForbiddenActionException("User is not authorized to perform an action")
        }
        request.status = RequestStatus.REJECTED
        request.rejectionReason = rejectionReasonRequest.rejectionReason
        requestRepository.save(request)
    }

    private fun fetchFromDb(id: String) = requestRepository.findById(id).orElseThrow { EntityNotFoundException(id) }
}
