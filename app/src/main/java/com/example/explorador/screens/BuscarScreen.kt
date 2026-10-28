package com.example.explorador.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class SearchItem(
    val tipo: String,
    val titulo: String,
    val subtitulo: String,
    val linea: String,
    val tag: String = ""
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuscarScreen(
    onInvestigadorClick: (String) -> Unit,
    onProyectoClick: (String) -> Unit,
    onPublicacionClick: (String) -> Unit
) {
    var searchText by remember { mutableStateOf("") }
    var selectedLinea by remember { mutableStateOf("Todas las líneas") }
    var selectedFilter by remember { mutableStateOf("Todos") }
    var expandedDropdown by remember { mutableStateOf(false) }

    val lineas = listOf(
        "Todas las líneas",
        "Construcción Sustentable en Madera",
        "Bioproductos y Biomateriales",
        "Gestión y Economía Forestal",
        "Innovación en Procesos Industriales"
    )

    val filters = listOf("Todos", "Investigadores", "Proyectos", "Publicaciones")

    val allItems = listOf(
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

    val filteredItems = allItems.filter { item ->
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

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF8F9FA))) {
        Column(modifier = Modifier.background(Color.White).padding(16.dp)) {
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Buscar proyectos, personas...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF2563EB),
                    unfocusedBorderColor = Color(0xFFE5E5E5)
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            ExposedDropdownMenuBox(
                expanded = expandedDropdown,
                onExpandedChange = { expandedDropdown = !expandedDropdown }
            ) {
                OutlinedTextField(
                    value = selectedLinea,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { Icon(Icons.Default.ArrowDropDown, contentDescription = null) },
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF2563EB),
                        unfocusedBorderColor = Color(0xFFE5E5E5)
                    )
                )
                ExposedDropdownMenu(
                    expanded = expandedDropdown,
                    onDismissRequest = { expandedDropdown = false }
                ) {
                    lineas.forEach { linea ->
                        DropdownMenuItem(
                            text = { Text(linea) },
                            onClick = {
                                selectedLinea = linea
                                expandedDropdown = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                filters.forEach { filter ->
                    val isSelected = selectedFilter == filter
                    Surface(
                        modifier = Modifier.clickable { selectedFilter = filter },
                        color = if (isSelected) Color(0xFFE0E7FF) else Color.Transparent,
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, if (isSelected) Color.Transparent else Color.LightGray)
                    ) {
                        Text(
                            text = filter,
                            color = if (isSelected) Color(0xFF2563EB) else Color.Gray,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 13.sp,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                }
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("${filteredItems.size} resultados", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("Por relevancia", fontSize = 14.sp, color = Color.Gray)
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(filteredItems) { item ->
                SearchItemCard(
                    item = item,
                    onClick = {
                        when (item.tipo) {
                            "INVESTIGADOR" -> onInvestigadorClick(item.titulo)
                            "PROYECTO" -> onProyectoClick(item.titulo)
                            "PUBLICACIÓN" -> onPublicacionClick(item.titulo)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun SearchItemCard(item: SearchItem, onClick: () -> Unit) {
    val colorPrincipal = when (item.linea) {
        "Construcción Sustentable en Madera" -> Color(0xFFE91E63)
        "Bioproductos y Biomateriales" -> Color(0xFF4CAF50)
        "Gestión y Economía Forestal" -> Color(0xFFFF9800)
        "Innovación en Procesos Industriales" -> Color(0xFF03A9F4)
        else -> Color(0xFF2563EB)
    }

    val colorFondo = when (item.linea) {
        "Construcción Sustentable en Madera" -> Color(0xFFFCE4EC)
        "Bioproductos y Biomateriales" -> Color(0xFFE8F5E9)
        "Gestión y Economía Forestal" -> Color(0xFFFFF3E0)
        "Innovación en Procesos Industriales" -> Color(0xFFE1F5FE)
        else -> Color(0xFFE0E7FF)
    }

    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0xFFE5E5E5))
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(48.dp).background(colorFondo, if (item.tipo == "INVESTIGADOR") CircleShape else RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                when (item.tipo) {
                    "INVESTIGADOR" -> Text(item.tag, color = colorPrincipal, fontWeight = FontWeight.Bold)
                    "PROYECTO" -> Icon(Icons.Default.Build, contentDescription = null, tint = colorPrincipal)
                    "PUBLICACIÓN" -> Icon(Icons.Outlined.Article, contentDescription = null, tint = colorPrincipal)
                }
            }
            Column(modifier = Modifier.weight(1f).padding(start = 16.dp)) {
                Text(item.tipo, fontSize = 10.sp, color = colorPrincipal, fontWeight = FontWeight.Bold)
                Text(item.titulo, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(vertical = 2.dp))
                Text(item.subtitulo, fontSize = 13.sp, color = Color.Gray)
            }
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.LightGray)
        }
    }
}