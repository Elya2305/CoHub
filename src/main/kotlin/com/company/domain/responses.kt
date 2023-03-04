package com.company.domain

import com.company.entity.ProjectStatus

data class ShortUserResponse(
    val id: String,
    val pic: String,
)

data class ProjectResponse(
    val id: String,
    val title: String,
    val description: String,
    val tags: List<String>,
    val resultLink: String?,
    val pic: String,
    val status: ProjectStatus,
    val isAuthor: Boolean,
    val team: List<ShortUserResponse>
)

data class IdResponse(
    val id: String,
)

data class TagResponse(
    val id: String,
   val title: String
)