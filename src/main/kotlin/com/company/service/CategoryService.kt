package com.company.service

import com.company.domain.CategoryDto
import com.company.domain.UserContext.getUserUuid
import com.company.entity.Category
import com.company.repository.CategoryRepository
import org.springframework.stereotype.Service

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository
) {

    fun all(): List<CategoryDto> {
        return categoryRepository.findAllByUserId(getUserUuid())
            .map { map(it) }
    }

    private fun map(category: Category): CategoryDto {
        return CategoryDto(category.id, category.title)
    }
}