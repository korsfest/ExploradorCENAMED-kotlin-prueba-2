package com.example.explorador.model

import com.google.gson.annotations.SerializedName

data class LineaInvestigacion(
    val id: Int = 0,
    val titulo: String = "",
    val tag: String = "",
    val detalle: String = "",
    val descripcion: String = ""
)

data class Investigador(
    val id: Int = 0,
    val nombre: String = "",
    val especialidad: String = "",
    @SerializedName("linea_nombre") val lineaNombre: String = "",
    val correo: String = "",
    val bio: String = ""
)

data class Proyecto(
    val id: Int = 0,
    val titulo: String = "",
    val subtitulo: String = "",
    val objetivo: String = "",
    val anio: String = "",
    val estado: String = "",
    @SerializedName("linea_nombre") val lineaNombre: String = "",
    @SerializedName("investigador_nombre") val investigadorNombre: String = ""
)

data class ProyectoRequest(
    val titulo: String,
    val subtitulo: String,
    val anio: String,
    val estado: String,
    @SerializedName("investigador_nombre") val investigadorNombre: String
)

data class Publicacion(
    val id: Int = 0,
    val titulo: String = "",
    val tipo: String = "",
    val contexto: String = "",
    val resumen: String = "",
    val anio: String = "",
    val autor: String = "",
    @SerializedName("linea_nombre") val lineaNombre: String = "",
    @SerializedName("proyecto_titulo") val proyectoTitulo: String = ""
)

data class WeatherResponse(
    @SerializedName("current_weather") val currentWeather: CurrentWeather? = null
)

data class CurrentWeather(
    val temperature: Double = 0.0,
    val windspeed: Double = 0.0,
    val weathercode: Int = 0
)
