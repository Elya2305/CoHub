package com.company.controller

import com.company.domain.RecipeDto
import com.company.service.RecipeService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/recipes")
class RecipeController(private val recipeService: RecipeService) {

    @GetMapping
    fun getRecipes(): List<String> {
        print("Request on getting recipes")
        return emptyList()
    }

    @PostMapping
    fun save(@RequestBody recipe: RecipeDto): RecipeDto {
        print("Request on saving recipe $recipe")
        return recipeService.save(recipe)
    }
}