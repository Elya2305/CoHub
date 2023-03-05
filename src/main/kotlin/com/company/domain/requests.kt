package com.company.domain

import com.fasterxml.jackson.annotation.JsonProperty

data class ProjectRequest(
    val title: String,
    val description: String,
    val tags: List<String>
)

data class RejectionReasonRequest(
    @JsonProperty("rejection_reason")
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
