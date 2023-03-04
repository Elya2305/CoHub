package com.company.repository

import com.company.entity.Project
import com.company.entity.ProjectStatus
import com.company.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProjectRepository : JpaRepository<Project, String> {
    fun findAllByStatus(status: ProjectStatus): List<Project>

    fun findAllByAuthor(author: User): List<Project>

    fun findAllByAuthorAndStatus(author: User, status: ProjectStatus): List<Project>
}