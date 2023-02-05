package com.company.controller

import com.company.domain.RecipeDto
import com.company.service.RecipeService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.logging.Logger

@RestController
@RequestMapping("/recipes")
class RecipeController(private val recipeService: RecipeService) {
    private val log = Logger.getLogger(this.javaClass.name)

    @PostMapping
    fun save(@RequestBody recipe: RecipeDto): RecipeDto {
        log.info("Request on saving recipe $recipe")
        return recipeService.save(recipe)
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: String): RecipeDto {
        log.info("Request on getting recipe by id $id")
        return recipeService.get(id)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String) {
        log.info("Request on deleting recipe by id $id")
        recipeService.delete(id)
    }

    @GetMapping()
    fun all(): List<RecipeDto> {
        log.info("Request on getting all recipes")
        return recipeService.all()
    }
}
