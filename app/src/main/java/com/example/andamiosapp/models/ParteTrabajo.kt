package com.example.andamiosapp.models

data class ParteTrabajoRequest(
    val id_user: Int,
    val id_obra: Int,
    val fecha_parte: String
    )
data class ParteTrabajoResponse(
    val id: Int,
    val id_user: Int,
    val id_obra: Int,
    val fecha_parte: String

)