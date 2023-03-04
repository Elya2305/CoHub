package com.company.domain

data class ProjectRequest(
    val title: String,
    val description: String,
    val tags: List<String>
)

data class RejectionReasonRequest(
    val rejectionReason: String
)