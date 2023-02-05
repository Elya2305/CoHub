package com.company.repository

import com.company.entity.Recipe
import org.springframework.data.jpa.repository.JpaRepository

interface RecipeRepository: JpaRepository<Recipe, String> {

    fun findAllByUserId(userId: String): List<Recipe>
}
