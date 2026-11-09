package com.example.explorador.model

data class LineaInvestigacion(
    val id: String,
    val nombre: String,
    val descripcion: String,
    val investigadores: List<String>,
    val proyectos: List<String>,
    val publicaciones: List<String>
)

data class Investigador(
    val id: String,
    val nombre: String,
    val especialidad: String
)

data class Proyecto(
    val id: String,
    val titulo: String,
    val estado: String,
    val anio: Int
)

data class Publicacion(
    val id: String,
    val titulo: String,
    val tipo: String
)

data class SearchItem(
    val tipo: String,
    val titulo: String,
    val subtitulo: String,
    val linea: String,
    val tag: String? = null
)
