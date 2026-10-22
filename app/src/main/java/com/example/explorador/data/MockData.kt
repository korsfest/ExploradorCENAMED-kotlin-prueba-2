package com.example.explorador.data

import com.example.explorador.model.*

object MockData {
    val lineas = listOf(
        LineaInvestigacion(
            id = "L1",
            titulo = "Construcción Sustentable en Madera",
            descripcion = "Desarrollo de sistemas constructivos eficientes e industrializados.",
            investigadores = listOf(
                Investigador("I1", "Dra. Ana Silva", "Ingeniería Estructural"),
                Investigador("I2", "Dr. Carlos Ruiz", "Arquitectura Sustentable")
            ),
            proyectos = listOf(
                Proyecto("P1", "Prototipo de vivienda modular", "En ejecución", 2025),
                Proyecto("P2", "Análisis sísmico de uniones", "Finalizado", 2024)
            ),
            publicaciones = listOf(
                Publicacion("PUB1", "Guía de diseño estructural", "Policy Brief"),
                Publicacion("PUB2", "Resistencia al fuego en CLT", "Artículo Científico")
            )
        ),
        LineaInvestigacion(
            id = "L2",
            titulo = "Bioproductos y Biomateriales",
            descripcion = "Innovación en el uso de la fibra de madera y derivados.",
            investigadores = listOf(
                Investigador("I3", "Dr. Luis Morales", "Química de Materiales")
            ),
            proyectos = listOf(
                Proyecto("P3", "Adhesivos libres de formaldehído", "En ejecución", 2026)
            ),
            publicaciones = listOf(
                Publicacion("PUB3", "Nuevos biomateriales chilenos", "Artículo Científico")
            )
        )
    )
}