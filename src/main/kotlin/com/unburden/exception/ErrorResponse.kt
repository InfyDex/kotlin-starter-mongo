package com.unburden.exception

data class ErrorResponse(
    val status: Int,
    val message: String = "",
    val errorType: String = ""
)