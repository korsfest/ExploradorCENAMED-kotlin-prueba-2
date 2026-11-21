package com.example.explorador.repository

import com.example.explorador.model.*
import retrofit2.Response
import retrofit2.http.*

interface CenamadApiService {

    // 1. GET todas las líneas
    @GET("lineas")
    suspend fun getLineas(): List<LineaInvestigacion>

    // 2. GET todos los investigadores
    @GET("investigadores")
    suspend fun getInvestigadores(): List<Investigador>

    // 3. GET detalle de un investigador por ID
    @GET("investigadores/{id}")
    suspend fun getInvestigadorDetalle(@Path("id") id: Int): Investigador

    // 4. GET todos los proyectos
    @GET("proyectos")
    suspend fun getProyectos(): List<Proyecto>

    // 5. POST crear un nuevo proyecto
    @POST("proyectos")
    suspend fun crearProyecto(@Body nuevoProyecto: ProyectoRequest): Proyecto

    // 6. DELETE eliminar un proyecto por ID
    @DELETE("proyectos/{id}")
    suspend fun eliminarProyecto(@Path("id") id: Int): Response<Unit>

    // 7. GET todas las publicaciones
    @GET("publicaciones")
    suspend fun getPublicaciones(): List<Publicacion>
}