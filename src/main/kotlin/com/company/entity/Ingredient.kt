package com.company.entity

import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

//@Entity
//@Table(name = "ingredients")
//data class Ingredient(
//    @Id
//    val id: String = UUID.randomUUID().toString(),
//    val title: String,
//    val amount: String,
//    @Column(name = "category_id")
//    val categoryId: String,
//    @Column(name = "user_id")
//    val userId: String,
//)


@Entity
@Table(name = "ingredients")
data class Ingredient(
    @Id
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val amount: String,
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    val category: Category,
    @Column(name = "user_id")
    val userId: String,
)
