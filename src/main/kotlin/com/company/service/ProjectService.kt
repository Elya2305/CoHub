package com.company.service

import com.company.domain.IdResponse
import com.company.domain.ProjectRequest
import com.company.domain.ProjectResponse
import com.company.domain.ShortUserResponse
import com.company.domain.UserContext
import com.company.entity.Project
import com.company.entity.ProjectStatus
import com.company.exception.EntityNotFoundException
import com.company.repository.ProjectRepository
import org.springframework.stereotype.Service

@Service
class ProjectService(
    private val projectRepository: ProjectRepository,
    private val userService: UserService,
    private val tagService: TagService,
) {
    private val PICS = listOf(
        "https://i.ibb.co/8KvwDkf/pankaj-patel-Fi-GJa-LRGKc-unsplash.jpg",
        "https://i.ibb.co/vdSj47K/altumcode-d-MUt0-X3f59-Q-unsplash.jpg",
        "https://i.ibb.co/y0nywk6/clement-helardot-95-YRwf6-CNw8-unsplash.jpg",
        "https://i.ibb.co/44DY0dF/ferenc-almasi-e-Ypc-LDXHVb0-unsplash.jpg",
        "https://i.ibb.co/wSj7F2J/shahadat-rahman-Bfr-Qn-KBul-YQ-unsplash.jpg",
        "https://i.ibb.co/dcZvvLj/muha-ajjan-s-L2-BRR1cuv-M-unsplash.jpg",
        "https://i.ibb.co/G9fqxP4/juanjo-jaramillo-m-Znx9429i94-unsplash.jpg",
        "https://i.ibb.co/qk1SHCM/markus-spiske-hv-Sr-CVec-VI-unsplash.jpg",
        "https://i.ibb.co/gyPmK2j/altumcode-XMFZqr-Gy-V-Q-unsplash.jpg",
        "https://i.ibb.co/zF5WP31/fotis-fotopoulos-Du-HKo-V44prg-unsplash.jpg"
    )

    fun create(request: ProjectRequest): IdResponse {

        val project = Project(
            title = request.title,
            description = request.description,
            tags = request.tags,
            pic = PICS.random(),
            author = userService.getCurrentReference(),
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
        pic = project.pic,
        isAuthor = UserContext.getUserUuid() != null && project.author.id == UserContext.getUserUuid(),
        team = project.team + listOf(ShortUserResponse(project.author.id, project.author.pic))
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

    fun allForUser(status: ProjectStatus?): List<ProjectResponse> {
        if (status == null) {
            return projectRepository.findAllByAuthor(userService.getCurrentReference())
                .filter { o -> isUserParticipant(o) }
                .map { map(it) }
        }
        return projectRepository.findAllByAuthorAndStatus(userService.getCurrentReference(), status)
            .map { map(it) }
    }

    fun allOpen(tags: List<String>?): List<ProjectResponse> {
        return if (tags.isNullOrEmpty()) {
            projectRepository.findAllByStatus(ProjectStatus.OPEN).map { map(it) }
        } else {
            projectRepository.findAllByStatus(ProjectStatus.OPEN)
                .filter { project -> tags.all { project.tags.contains(it) } }
                .map { map(it) }
        }
    }

    fun getReferenceById(projectId: String): Project {
        return projectRepository.getReferenceById(projectId)
    }

    fun addUserToTeam(userId: String, projectId: String) {
        val project = fetchFromDb(projectId)
        val user = userService.get(userId)
        project.team.add(ShortUserResponse(userId, user.pic))
        projectRepository.save(project)
    }

    fun allByUserAndStatus(userId: String?, status: ProjectStatus?): List<ProjectResponse> {
        if (userId != null) {
            if (status != null) {
                return projectRepository.findAllByAuthorAndStatus(userService.getReferenceById(userId), status)
                    .map { map(it) }
            }
            return projectRepository.findAllByAuthor(userService.getReferenceById(userId)).map { map(it) }
        } else {
            if (status != null) {
                return projectRepository.findAllByStatus(status)
                    .map { map(it) }
            }
            return projectRepository.findAll().map { map(it) }
        }
    }

    private fun isUserParticipant(project: Project) =
        project.author.id == UserContext.getUserUuid() || project.team.map { it.id }.toList()
            .contains(UserContext.getUserUuid())

    private fun fetchFromDb(id: String) =
        projectRepository.findById(id).orElseThrow { EntityNotFoundException(id) }
}
