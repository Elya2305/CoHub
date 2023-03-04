package com.company.domain

import com.company.entity.ProjectStatus
import com.company.entity.RequestStatus

data class ShortUserResponse(
    val id: String,
    val pic: String?,
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

data class RequestForUserResponse(
    val id: String,
    val title: String,
    val description: String,
    val tags: List<String>,
    val team: List<ShortUserResponse>,
    val status: RequestStatus,
    val rejectionReason: String?,
)

data class RequestForProjectResponse(
    val id: String,
    val userId: String,
    val username: String,
    val skills: List<String>,
)

data class UserProfileResponse(
    val id: String,
    val pic: String?,
    val username:String,
    val jobTitle: String?,
    val skills: List<String>,
    val description: String?,
    val linkedinLink: String?,
    val githubLink: String?,
)
