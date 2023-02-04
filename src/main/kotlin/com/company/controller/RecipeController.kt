package com.company.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/recipes")
class RecipeController {

    @GetMapping
    fun getRecipes(): List<String> {
        print("Request on getting recipes")
        return emptyList()
    }
}