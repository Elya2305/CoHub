package com.company.entity

import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "categories")
data class Category(
    @Id
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    @Column(name = "user_id")
    val userId: String,
)
