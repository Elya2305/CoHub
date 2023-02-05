package com.company.controller

import com.company.domain.CategoryDto
import com.company.service.CategoryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.logging.Logger

@RestController
@RequestMapping("/categories")
class CategoryController(
    private val categoryService: CategoryService
) {
    private val log = Logger.getLogger(this.javaClass.name)

    @GetMapping
    fun all(): List<CategoryDto> {
        log.info("Request on getting all categories")
        return categoryService.all()
    }
}
