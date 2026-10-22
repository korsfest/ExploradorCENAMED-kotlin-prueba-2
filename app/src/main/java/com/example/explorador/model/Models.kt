package com.example.explorador.model

data class LineaInvestigacion(
    val id: String,
    val titulo: String,
    val descripcion: String,
    val investigadores: List<Investigador>,
    val proyectos: List<Proyecto>,
    val publicaciones: List<Publicacion>
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