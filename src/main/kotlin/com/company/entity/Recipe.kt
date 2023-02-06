package com.company.entity

import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import org.hibernate.annotations.TypeDefs
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "recipes")
@TypeDefs(
    TypeDef(name = "jsonb", typeClass = JsonBinaryType::class)
)
data class Recipe(
    @Id
    val id: String,
    val title: String,
    val category: String,
    @Column(name = "instructions", columnDefinition = "text")
    val instructions: String,
    val pic: String,
    val youtubeUrl: String,
    @Type(type = "jsonb")
    @Column(name = "ingredients", columnDefinition = "jsonb")
    val ingredients: List<RecipeIngredient>,
    val userId: String,
)

data class RecipeIngredient(
    val title: String,
    val amount: String,
)
