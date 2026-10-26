package com.example.explorador.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Info
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
fun FichaPublicacionScreen(
    nombrePublicacion: String,
    onVolver: () -> Unit,
    onInvestigadorClick: (String) -> Unit,
    onProyectoClick: (String) -> Unit
) {
    val esGuia = nombrePublicacion.contains("Guía de diseño", ignoreCase = true)

    val tipo = if (esGuia) "POLICY BRIEF" else "ARTÍCULO CIENTÍFICO"
    val anio = if (esGuia) "2025" else "2024"
    val titulo = if (esGuia) "Guía de diseño estructural" else "Resistencia al fuego en CLT"
    val subtitulo = if (esGuia) "Diseño de sistemas modulares en madera" else "Análisis sísmico de uniones"
    val resumen = if (esGuia)
        "Síntesis de criterios para diseñar módulos habitacionales de madera. Aborda organización estructural, uniones y montaje, y conecta estas decisiones con el prototipo de vivienda modular."
    else
        "Revisión exploratoria del desempeño al fuego de paneles de madera contralaminada y su comportamiento en uniones estructurales."
    val proyectoRelacionado = if (esGuia) "Prototipo de vivienda modular" else "Análisis sísmico de uniones"
    val proyectoEstado = if (esGuia) "2025 · En ejecución" else "2024 · Finalizado"

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            TopAppBar(
                title = { Text("Ficha de publicación", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onVolver) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver") }
                },
                actions = {
                    IconButton(onClick = { }) { Icon(Icons.Default.Search, contentDescription = "Buscar") }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF2B2042)),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text(tipo, color = Color(0xFFD0BCFF), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Text(anio, color = Color(0xFFD0BCFF), fontSize = 14.sp, fontWeight = FontWeight.Medium)
                    }
                    Text(
                        titulo,
                        color = Color.White,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 12.dp),
                        lineHeight = 32.sp
                    )
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 4.dp)) {
                        Icon(Icons.Default.Book, contentDescription = null, tint = Color(0xFFD0BCFF), modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(subtitulo, color = Color(0xFFE8DEF8), fontSize = 14.sp)
                    }
                }
            }
        }

        item {
            Text("Resumen", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF32224A))
            Text(resumen, fontSize = 14.sp, color = Color.DarkGray, modifier = Modifier.padding(top = 8.dp), lineHeight = 20.sp)
            Text("Resumen de demostración · no es un documento técnico real.", fontSize = 12.sp, color = Color(0xFF7A6B94), modifier = Modifier.padding(top = 8.dp))
        }

        item {
            Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
                Text("Autoría", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("2 autores", color = Color(0xFF4A3780), fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }

        item {
            FichaInvestigadorCard(
                iniciales = "AS",
                especialidad = "Ingeniería Estructural",
                nombre = "Dra. Ana Silva",
                onClick = { onInvestigadorClick("Dra. Ana Silva") }
            )
        }

        item {
            FichaInvestigadorCard(
                iniciales = "CR",
                especialidad = "Arquitectura Sustentable",
                nombre = "Dr. Carlos Ruiz",
                onClick = { onInvestigadorClick("Dr. Carlos Ruiz") }
            )
        }

        item {
            Text("Proyecto relacionado", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(top = 8.dp))
            Card(
                modifier = Modifier.fillMaxWidth().clickable { onProyectoClick(proyectoRelacionado) },
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Color(0xFFE5E5E5))
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(40.dp).background(Color(0xFFF3EDF7), RoundedCornerShape(8.dp)), contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Build, contentDescription = null, tint = Color(0xFF4A3780), modifier = Modifier.size(20.dp))
                    }
                    Column(modifier = Modifier.weight(1f).padding(start = 12.dp)) {
                        Text(proyectoEstado, fontSize = 10.sp, color = Color(0xFF4A3780), fontWeight = FontWeight.Bold)
                        Text(proyectoRelacionado, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    }
                    Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = Color.LightGray)
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF3EDF7)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Build, contentDescription = null, tint = Color(0xFF4A3780), modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Construcción Sustentable en Madera", color = Color(0xFF4A3780), fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                    Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = Color(0xFF4A3780))
                }
            }
        }

        item {
            Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 32.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.Info, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Muestra académica · datos e indicadores sintéticos", fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}