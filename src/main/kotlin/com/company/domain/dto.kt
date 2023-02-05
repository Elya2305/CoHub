package com.company.domain

import com.company.entity.RecipeIngredient
import com.fasterxml.jackson.annotation.JsonProperty

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

data class RecipeDto(
    val id: String?,
    val title: String,
    val category: String,
    val instructions: String,
    val pic: String,
    @JsonProperty("youtube_url")
    val youtubeUrl: String,
    val ingredients: List<RecipeIngredient>,
)