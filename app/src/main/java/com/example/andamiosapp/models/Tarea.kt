package com.example.andamiosapp.models

data class TareaRequest(
    val parte_trabajo_id: String,
    val descripcion: String,
    val personal_asignado: String,
    val precio_por_hora: Int,
    val horas_trabajadas: Int
)
data class TareaResponse(
    val id: Int,
    val parte_trabajo_id: Int,
    val descripcion: String,
    val personal_asignado: String,
    val precio_por_hora: Int,
    val horas_trabajadas: Int
)