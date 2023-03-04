package com.company.exception

data class ErrorResponse(
    var status: Int,
    val message: String,
)
