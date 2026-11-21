package com.example.explorador.repository

import com.example.explorador.model.*
import retrofit2.Response
import retrofit2.http.*

interface CenamadApiService {

    @GET("lineas")
    suspend fun getLineas(): List<LineaInvestigacion>

    @GET("investigadores")
    suspend fun getInvestigadores(): List<Investigador>

    @GET("investigadores/{id}")
    suspend fun getInvestigadorDetalle(@Path("id") id: Int): Investigador

    @GET("proyectos")
    suspend fun getProyectos(): List<Proyecto>

    @POST("proyectos")
    suspend fun crearProyecto(@Body nuevoProyecto: ProyectoRequest): Proyecto

    @PUT("proyectos/{id}")
    suspend fun actualizarProyecto(
        @Path("id") id: Int,
        @Body proyectoActualizado: ProyectoRequest
    ): Proyecto

    @DELETE("proyectos/{id}")
    suspend fun eliminarProyecto(@Path("id") id: Int): Response<Unit>

    @GET("publicaciones")
    suspend fun getPublicaciones(): List<Publicacion>
}

interface ExternalApiService {

    @GET("forecast")
    suspend fun obtenerClimaSede(
        @Query("latitude") latitude: Double = -33.443018,
        @Query("longitude") longitude: Double = -70.653870,
        @Query("current_weather") currentWeather: Boolean = true
    ): WeatherResponse
}
