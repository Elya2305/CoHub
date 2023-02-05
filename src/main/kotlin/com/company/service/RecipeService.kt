package com.company.service

import com.company.domain.RecipeDto
import com.company.entity.Recipe
import com.company.repository.RecipeRepository
import org.springframework.stereotype.Service

@Service
class RecipeService(
    private val recipeRepository: RecipeRepository
) {

    fun save(recipe: RecipeDto): RecipeDto {
        val saved = recipeRepository.save(map(recipe))
        return map(saved)
    }

    private fun map(recipe: RecipeDto): Recipe {
        return Recipe(
            title = recipe.title,
            category = recipe.category,
            instructions = recipe.instructions,
            pic = recipe.pic,
            youtubeUrl = recipe.youtubeUrl,
            ingredients = recipe.ingredients
        )
    }

    private fun map(recipe: Recipe): RecipeDto {
        return RecipeDto(
            id = recipe.id,
            title = recipe.title,
            category = recipe.category,
            instructions = recipe.instructions,
            pic = recipe.pic,
            youtubeUrl = recipe.youtubeUrl,
            ingredients = recipe.ingredients
        )
    }
}