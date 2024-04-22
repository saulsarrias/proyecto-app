package com.example.andamiosapp.models

data class UsuarioRequest(
    val email: String,
    val password: String
)
data class Usuario(
    val name: String,
    val email: String,
    val password: String
)