package com.company.service

import com.company.domain.RecipeDto
import com.company.domain.UserContext.getUserUuid
import com.company.entity.Recipe
import com.company.exception.EntityNotFoundException
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

    fun get(id: String): RecipeDto {
        return map(recipeRepository.findById(id)
            .orElseThrow { EntityNotFoundException("Can't find recipe by id $id") })
    }

    fun delete(id: String) {
        if (recipeRepository.existsById(id)) {
            recipeRepository.deleteById(id)
        }
    }

    fun all(): List<RecipeDto> {
        return recipeRepository.findAllByUserId(getUserUuid()).map { map(it) }
    }

    private fun map(recipe: RecipeDto): Recipe {
        return Recipe(
            id = recipe.id,
            title = recipe.title,
            category = recipe.category,
            instructions = recipe.instructions,
            pic = recipe.pic,
            youtubeUrl = recipe.youtubeUrl,
            ingredients = recipe.ingredients,
            userId = getUserUuid(),
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