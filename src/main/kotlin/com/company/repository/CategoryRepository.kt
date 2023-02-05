package com.company.repository

import com.company.entity.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : JpaRepository<Category, String> {
    fun findByTitle(title: String): Category?

    fun findAllByUserId(userId: String): List<Category>
}