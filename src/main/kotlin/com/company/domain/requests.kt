package com.company.domain

data class ProjectRequest(
    val title: String,
    val description: String,
    val tags: List<String>
)

data class RejectionReasonRequest(
    val rejectionReason: String
)

data class UserProfileRequest(
    val username:String,
    val jobTitle: String?,
    val skills: ArrayList<String>,
    val description: String?,
    val linkedinLink: String?,
    val githubLink: String?,
)
