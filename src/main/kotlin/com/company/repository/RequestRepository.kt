package com.company.repository

import com.company.entity.Project
import com.company.entity.Request
import com.company.entity.RequestStatus
import com.company.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RequestRepository: JpaRepository<Request, String> {
    fun findAllByUser(user: User): List<Request>

    fun findAllByProjectAndStatus(project: Project, status: RequestStatus): List<Request>
}