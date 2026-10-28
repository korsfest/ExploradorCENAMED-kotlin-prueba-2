package com.example.explorador.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProyectosScreen(onVolver: () -> Unit, onVerProyecto: (String) -> Unit) {
    val proyectos = listOf(
        "Prototipo de vivienda modular",
        "Análisis sísmico de uniones",
        "Biopolímeros forestales avanzados",
        "Nanocompuestos estructurales",
        "Evaluación de Ciclo de Vida en CLT",
        "Mercados de Exportación Maderera",
        "Desarrollo Local y Territorio",
        "Robótica en Aserraderos",
        "Celdas Robotizadas de Producción"
    )

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF8F9FA))) {
        TopAppBar(
            title = { Text("Proyectos", fontSize = 18.sp, fontWeight = FontWeight.Bold) },
            navigationIcon = {
                IconButton(onClick = onVolver) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver") }
            },
            actions = {
                IconButton(onClick = { }) { Icon(Icons.Default.Search, contentDescription = "Buscar") }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
        )

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(proyectos) { titulo ->
                val esConstruccion = titulo.contains("vivienda modular", ignoreCase = true) || titulo.contains("Análisis sísmico", ignoreCase = true)
                val esBioproductos = titulo.contains("Biopolímeros", ignoreCase = true) || titulo.contains("Nanocompuestos", ignoreCase = true) || titulo.contains("Ciclo de Vida", ignoreCase = true)
                val esGestion = titulo.contains("Mercados de Exportación", ignoreCase = true) || titulo.contains("Desarrollo Local", ignoreCase = true)

                val colorPrincipal = when {
                    esConstruccion -> Color(0xFFE91E63)
                    esBioproductos -> Color(0xFF4CAF50)
                    esGestion -> Color(0xFFFF9800)
                    else -> Color(0xFF03A9F4)
                }

                val colorFondoLigero = when {
                    esConstruccion -> Color(0xFFFCE4EC)
                    esBioproductos -> Color(0xFFE8F5E9)
                    esGestion -> Color(0xFFFFF3E0)
                    else -> Color(0xFFE1F5FE)
                }

                val estado = if (titulo.contains("Análisis sísmico") || titulo.contains("Ciclo de Vida")) "2024 · Finalizado" else "2025 · En ejecución"

                Card(
                    modifier = Modifier.fillMaxWidth().clickable { onVerProyecto(titulo) },
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, Color(0xFFE5E5E5))
                ) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier.size(48.dp).background(colorFondoLigero, RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Build, contentDescription = null, tint = colorPrincipal)
                        }
                        Column(modifier = Modifier.weight(1f).padding(start = 16.dp)) {
                            Text(estado, fontSize = 10.sp, color = colorPrincipal, fontWeight = FontWeight.Bold)
                            Text(titulo, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF1F1A24))
                        }
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.LightGray)
                    }
                }
            }
        }
    }
}