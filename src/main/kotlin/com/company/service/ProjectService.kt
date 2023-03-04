package com.company.service

import com.company.domain.IdResponse
import com.company.domain.ProjectRequest
import com.company.domain.ProjectResponse
import com.company.domain.UserContext
import com.company.entity.Project
import com.company.entity.ProjectStatus
import com.company.repository.ProjectRepository
import org.springframework.stereotype.Service

@Service
class ProjectService(
    private val projectRepository: ProjectRepository,
    private val userService: UserService,
    private val tagService: TagService,
) {

    fun create(request: ProjectRequest): IdResponse {

        val project = Project(
            title = request.title,
            description = request.description,
            tags = request.tags,
            pic = "", // todo,
            author = userService.findById(UserContext.getUserUuid()),
        )
        tagService.save(request.tags)
        return IdResponse(projectRepository.save(project).id)
    }

    fun get(id: String): ProjectResponse {
        val project = fetchFromDb(id)

        return map(project)
    }

    fun update(id: String, request: ProjectRequest): ProjectResponse {
        val project = fetchFromDb(id)

        project.title = request.title
        project.description = request.description
        project.tags = request.tags

        projectRepository.save(project)
        return map(project)
    }

    private fun map(project: Project) = ProjectResponse(
        id = project.id,
        title = project.title,
        description = project.description,
        tags = project.tags,
        resultLink = project.resultLink,
        status = project.status,
        pic = "", // todo,
        isAuthor = project.author.id == UserContext.getUserUuid(),
        team = project.team
    )

    fun startProject(id: String) {
        val project = fetchFromDb(id)
        project.status = ProjectStatus.IN_PROGRESS
        projectRepository.save(project)
    }

    fun finishProject(id: String) {
        val project = fetchFromDb(id)
        project.status = ProjectStatus.FINISHED
        projectRepository.save(project)
    }

    fun all(status: ProjectStatus?, tags: List<String>?): List<ProjectResponse> {
        if (tags.isNullOrEmpty()) {
            if (status == null) {
                return projectRepository.findAll().map { map(it) }
            }
            return projectRepository.findAllByStatus(status).map { map(it) }
        } else {
            if (status == null) {
                return projectRepository.findAll()
                    .filter { o -> o.tags.any { i -> tags.contains(i) } }
                    .map { map(it) }
            }
            return projectRepository.findAllByStatus(status)
                .filter { o -> o.tags.any { i -> tags.contains(i) } }
                .map { map(it) }
        }
    }

    private fun fetchFromDb(id: String) = projectRepository.findById(id).orElseThrow()

}
