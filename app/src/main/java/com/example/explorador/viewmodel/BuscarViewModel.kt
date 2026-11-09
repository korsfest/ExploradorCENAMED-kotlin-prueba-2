package com.example.explorador.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.explorador.model.SearchItem

class BuscarViewModel : ViewModel() {
    var searchText by mutableStateOf("")
        private set
    var selectedLinea by mutableStateOf("Todas las líneas")
        private set
    var selectedFilter by mutableStateOf("Todos")
        private set
    var expandedDropdown by mutableStateOf(false)
        private set

    val lineas = listOf(
        "Todas las líneas",
        "Construcción Sustentable en Madera",
        "Bioproductos y Biomateriales",
        "Gestión y Economía Forestal",
        "Innovación en Procesos Industriales"
    )

    val filters = listOf("Todos", "Investigadores", "Proyectos", "Publicaciones")

    private val allItems = listOf(
        SearchItem("INVESTIGADOR", "Dra. Ana Silva", "Ingeniería Estructural", "Construcción Sustentable en Madera", "AS"),
        SearchItem("INVESTIGADOR", "Dr. Carlos Ruiz", "Arquitectura Sustentable", "Construcción Sustentable en Madera", "CR"),
        SearchItem("PROYECTO", "Prototipo de vivienda modular", "2025 · En ejecución", "Construcción Sustentable en Madera"),
        SearchItem("PROYECTO", "Análisis sísmico de uniones", "2024 · Finalizado", "Construcción Sustentable en Madera"),
        SearchItem("PUBLICACIÓN", "Guía de diseño estructural", "Policy Brief · 2025", "Construcción Sustentable en Madera"),
        SearchItem("PUBLICACIÓN", "Resistencia al fuego en CLT", "Artículo científico · 2024", "Construcción Sustentable en Madera"),
        SearchItem("INVESTIGADOR", "Dr. Luis Morales", "Química de Materiales", "Bioproductos y Biomateriales", "LM"),
        SearchItem("INVESTIGADOR", "Dra. Sofía Valdés", "Nanotecnología de la Madera", "Bioproductos y Biomateriales", "SV"),
        SearchItem("INVESTIGADOR", "Dr. Mateo Rojas", "Gestión Ambiental", "Bioproductos y Biomateriales", "MR"),
        SearchItem("PROYECTO", "Biopolímeros forestales avanzados", "2025 · En ejecución", "Bioproductos y Biomateriales"),
        SearchItem("PROYECTO", "Nanocompuestos estructurales", "2025 · En ejecución", "Bioproductos y Biomateriales"),
        SearchItem("PROYECTO", "Evaluación de Ciclo de Vida en CLT", "2024 · Finalizado", "Bioproductos y Biomateriales"),
        SearchItem("PUBLICACIÓN", "Caracterización de biopolímeros", "Artículo científico · 2025", "Bioproductos y Biomateriales"),
        SearchItem("PUBLICACIÓN", "Nanocompuestos de celulosa", "Artículo científico · 2025", "Bioproductos y Biomateriales"),
        SearchItem("PUBLICACIÓN", "Impacto ambiental del CLT", "Artículo científico · 2024", "Bioproductos y Biomateriales"),
        SearchItem("INVESTIGADOR", "Dr. Gabriel Fuentes", "Economía Forestal", "Gestión y Economía Forestal", "GF"),
        SearchItem("INVESTIGADOR", "Dra. Marcela Parra", "Sostenibilidad Territorial", "Gestión y Economía Forestal", "MP"),
        SearchItem("PROYECTO", "Mercados de Exportación Maderera", "2025 · En ejecución", "Gestión y Economía Forestal"),
        SearchItem("PROYECTO", "Desarrollo Local y Territorio", "2025 · En ejecución", "Gestión y Economía Forestal"),
        SearchItem("PUBLICACIÓN", "Tendencias del mercado maderero", "Policy Brief · 2025", "Gestión y Economía Forestal"),
        SearchItem("PUBLICACIÓN", "Desarrollo Local Sostenible", "Policy Brief · 2025", "Gestión y Economía Forestal"),
        SearchItem("INVESTIGADOR", "Dr. Ignacio Becker", "Automatización Industrial", "Innovación en Procesos Industriales", "IB"),
        SearchItem("INVESTIGADOR", "Dra. Valentina Soto", "Manufactura Avanzada", "Innovación en Procesos Industriales", "VS"),
        SearchItem("PROYECTO", "Robótica en Aserraderos", "2025 · En ejecución", "Innovación en Procesos Industriales"),
        SearchItem("PROYECTO", "Celdas Robotizadas de Producción", "2025 · En ejecución", "Innovación en Procesos Industriales"),
        SearchItem("PUBLICACIÓN", "Automatización en aserraderos", "Policy Brief · 2025", "Innovación en Procesos Industriales"),
        SearchItem("PUBLICACIÓN", "Manufactura y Robótica Avanzada", "Policy Brief · 2025", "Innovación en Procesos Industriales")
    )

    val filteredItems: List<SearchItem>
        get() = allItems.filter { item ->
            val matchesSearch = searchText.isBlank() ||
                    item.titulo.contains(searchText, ignoreCase = true) ||
                    item.subtitulo.contains(searchText, ignoreCase = true)
            val matchesLinea = selectedLinea == "Todas las líneas" || item.linea == selectedLinea
            val matchesType = when (selectedFilter) {
                "Investigadores" -> item.tipo == "INVESTIGADOR"
                "Proyectos" -> item.tipo == "PROYECTO"
                "Publicaciones" -> item.tipo == "PUBLICACIÓN"
                else -> true
            }
            matchesSearch && matchesLinea && matchesType
        }

    fun onSearchTextChanged(newText: String) { searchText = newText }
    fun onLineaSelected(linea: String) { selectedLinea = linea; expandedDropdown = false }
    fun onFilterSelected(filter: String) { selectedFilter = filter }
    fun toggleDropdown() { expandedDropdown = !expandedDropdown }
    fun dismissDropdown() { expandedDropdown = false }
}