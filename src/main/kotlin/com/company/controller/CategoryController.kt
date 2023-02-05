package com.company.controller

import com.company.domain.CategoryDto
import com.company.service.CategoryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/categories")
class CategoryController(
    private val categoryService: CategoryService
) {

    @GetMapping
    fun all(): List<CategoryDto> {
        println("Request on getting all categories")
        return categoryService.all()
    }
}
