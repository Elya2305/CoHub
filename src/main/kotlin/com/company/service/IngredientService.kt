package com.company.service

import com.company.domain.CategoryDto
import com.company.domain.IngredientDto
import com.company.domain.UserContext.getUserUuid
import com.company.entity.Category
import com.company.entity.Ingredient
import com.company.repository.CategoryRepository
import com.company.repository.IngredientRepository
import org.springframework.stereotype.Service

@Service
class IngredientService(
    private val ingredientRepository: IngredientRepository,
    private val categoryRepository: CategoryRepository,
) {

    fun save(ingredient: IngredientDto): IngredientDto {
        val category = getCategoryEntity(ingredient.category)

        return map(
            ingredientRepository.save(
                Ingredient(
                    title = ingredient.title,
                    amount = ingredient.amount,
                    category = category,
                    userId = getUserUuid()
                )
            )
        )
    }

    fun delete(id: String) {
        ingredientRepository.deleteById(id)
    }

    fun all(): List<IngredientDto> {
        return ingredientRepository.findAllByUserId(getUserUuid())
            .map { map(it) }
    }

    private fun map(ingredient: Ingredient): IngredientDto {
        return IngredientDto(
            id = ingredient.id,
            title = ingredient.title,
            amount = ingredient.amount,
            category = CategoryDto(
                id = ingredient.category.id,
                title = ingredient.category.title,
            ),
        )
    }

    // todo check by title
    private fun getCategoryEntity(category: CategoryDto): Category {
        if (category.id == null) {
            return categoryRepository.save(Category(title = category.title, userId = getUserUuid()))
        }
        return Category(category.id, category.title, getUserUuid())
    }
}
