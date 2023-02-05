package com.company.domain

data class IngredientDto(
    val id: String?,
    val title: String,
    val category: CategoryDto,
    val amount: String,
)

data class CategoryDto(
    val id: String?,
    val title: String,
)