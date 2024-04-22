package com.example.andamiosapp.services

import com.example.andamiosapp.models.InventarioCantidad
import com.example.andamiosapp.models.InventarioRequest
import com.example.andamiosapp.models.InventarioResponse
import com.example.andamiosapp.models.LoginRequest
import com.example.andamiosapp.models.LoginResponse
import com.example.andamiosapp.models.ParteTrabajoRequest
import com.example.andamiosapp.models.ParteTrabajoResponse
import com.example.andamiosapp.models.TareaRequest
import com.example.andamiosapp.models.TareaResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @POST("login")
    fun login(
        @Body request: LoginRequest
    ): Call<LoginResponse>

    @GET("inventario")
    fun getInventarios(@Header("Authorization") token: String): Call<List<InventarioResponse>>

    @PUT("inventario/{id}")
    fun updateCantidad(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body newCantidad: InventarioCantidad
    ): Call<InventarioResponse>

    @POST("inventario/store")
    fun saveMaterial(
        @Header("Authorization") token: String,
        @Body request: InventarioRequest
    ): Call<InventarioResponse>

    @DELETE("inventario/{id}")
    fun deleteMaterial(
        @Header("Authorization") token: String,
        @Path("id") id: String,
    ): Call<Void>


    @GET("partes/{id}")
    fun getPartes(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Call <List<ParteTrabajoResponse>>

    @POST("partes/store")
    fun saveParte(
        @Header("Authorization") token: String,
        @Body request: ParteTrabajoRequest
    ): Call <ParteTrabajoResponse>

    @DELETE("partes/{id}")
    fun deleteParte(
        @Header("Authorization") token: String,
        @Path("id") id: String,
    ): Call<Void>


    @GET("tareas/{id}")
    fun getTareas(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Call <List<TareaResponse>>

    @POST("tareas/store")
    fun saveTarea(
        @Header("Authorization") token: String,
        @Body request: TareaRequest
    ): Call <TareaResponse>

    @DELETE("tareas/{id}")
    fun deleteTarea(
        @Header("Authorization") token: String,
        @Path("id") id: String,
    ): Call<Void>


}