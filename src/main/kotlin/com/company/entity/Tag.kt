package com.company.entity

import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tags")
data class Tag(
    @Id
    val id: String = UUID.randomUUID().toString(),
    val title: String,
)