package com.example.andamiosapp.models

data class InventarioResponse(

    val id: Int,
    val nombre: String,
    val descripcion: String,
    val cantidad: Int
)

data class Inventario(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val cantidad: Int
)

data class InventarioRequest(
    val nombre: String,
    val descripcion: String,
    val cantidad: Int
)

data class InventarioCantidad(
    val cantidad: Int
)

