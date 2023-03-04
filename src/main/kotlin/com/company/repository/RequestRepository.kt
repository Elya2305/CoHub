package com.company.repository

import com.company.entity.Request
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RequestRepository: JpaRepository<Request, String> {

}