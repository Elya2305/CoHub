package com.company.repository

import com.company.entity.Ingredient
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface IngredientRepository: JpaRepository<Ingredient, String> {

    @Query("select * from ingredients join categories c on ingredients.category_id = c.id", nativeQuery = true)
    fun findAllWithCategories()

    fun findAllByUserId(userId: String): List<Ingredient>
}