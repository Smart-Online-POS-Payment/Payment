package com.sopp.Payment.model

import org.springframework.http.HttpStatusCode

data class ResponseModel(
    val statusCode: String,
    val message: String
)