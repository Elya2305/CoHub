package com.company.repository

import com.company.entity.Tag
import org.springframework.data.jpa.repository.JpaRepository

interface TagRepository: JpaRepository<Tag, String> {
    fun findByTitle(title: String): Tag?
}
