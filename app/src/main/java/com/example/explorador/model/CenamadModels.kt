package com.example.explorador.model

import com.google.gson.annotations.SerializedName

data class LineaInvestigacion(
    val id: Int = 0,
    val titulo: String = "",
    val detalle: String = "",
    val descripcion: String = ""
)

data class Investigador(
    val id: Int = 0,
    val nombre: String = "",
    val especialidad: String = "",
    @SerializedName("linea_nombre") val lineaNombre: String = "",
    val correo: String = ""
)

data class Proyecto(
    val id: Int = 0,
    val titulo: String = "",
    val subtitulo: String = "",
    val anio: String = "",
    val estado: String = "",
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
    val autor: String = "",
    val anio: String = ""
)