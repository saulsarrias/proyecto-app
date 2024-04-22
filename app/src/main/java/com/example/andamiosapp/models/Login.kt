package com.example.andamiosapp.models

data class LoginResponse(
    val token: String,
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class LogoutResponse(
    val message: String
)