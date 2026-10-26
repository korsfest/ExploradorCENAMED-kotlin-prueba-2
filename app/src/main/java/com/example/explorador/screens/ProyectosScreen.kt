package com.example.explorador.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check
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
fun ProyectosScreen(onVolver: () -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            TopAppBar(
                title = { Text("Proyectos de la línea", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = { IconButton(onClick = onVolver) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver") } },
                actions = { IconButton(onClick = { }) { Icon(Icons.Default.Search, contentDescription = "Buscar") } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
        item {
            Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color(0xFFF3EDF7)), shape = RoundedCornerShape(12.dp)) {
                Row(modifier = Modifier.padding(12.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Build, contentDescription = null, tint = Color(0xFF4A3780), modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Construcción Sustentable en Madera", color = Color(0xFF4A3780), fontSize = 14.sp, modifier = Modifier.weight(1f))
                    Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = Color(0xFF4A3780))
                }
            }
        }
        item {
            Text("De la investigación a la aplicación", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF32224A))
            Text("2 proyectos para explorar sistemas constructivos en madera.", fontSize = 14.sp, color = Color.Gray, modifier = Modifier.padding(top = 4.dp))
        }
        item {
            Text("Estado del proyecto", color = Color.Gray, fontSize = 12.sp, modifier = Modifier.padding(bottom = 8.dp))
            Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(selected = true, onClick = {}, label = { Text("Todos · 2") }, leadingIcon = { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp)) })
                FilterChip(selected = false, onClick = {}, label = { Text("En ejecución · 1") })
                FilterChip(selected = false, onClick = {}, label = { Text("Finalizado · 1") })
            }
        }
        item {
            Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Proyectos de muestra", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("Más recientes", color = Color(0xFF4A3780), fontSize = 14.sp, fontWeight = FontWeight.Medium)
            }
        }
        item { ProyectoCard("2025 · En ejecución", "Prototipo de vivienda modular", "Propuesta de módulos habitacionales en madera con foco en montaje y desempeño estructural.", "Dra. Ana Silva · 1 publicación relacionada") }
        item { ProyectoCard("2024 · Finalizado", "Análisis sísmico de uniones", "Evaluación de uniones ante sismos y revisión complementaria del desempeño al fuego en CLT.", "Dr. Carlos Ruiz · 1 publicación relacionada") }
        item {
            Card(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFF3EDF7)), shape = RoundedCornerShape(16.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Los estados pertenecen a la muestra", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF32224A))
                    Text("Se conservan los años y estados del caso académico; no representan seguimiento institucional en tiempo real.", fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(top = 4.dp))
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

@Composable
fun ProyectoCard(estado: String, titulo: String, descripcion: String, responsable: String) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0xFFE5E5E5))
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.Top) {
            Box(modifier = Modifier.size(48.dp).background(Color(0xFFF3EDF7), RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) { Icon(Icons.Default.Build, contentDescription = null, tint = Color(0xFF4A3780)) }
            Column(modifier = Modifier.weight(1f).padding(start = 12.dp, end = 8.dp)) {
                Text(estado, fontSize = 10.sp, color = Color(0xFF4A3780), fontWeight = FontWeight.Bold)
                Text(titulo, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(vertical = 2.dp))
                Text(descripcion, fontSize = 14.sp, color = Color.DarkGray, lineHeight = 18.sp)
                Text(responsable, fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(top = 8.dp))
            }
            Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = Color.LightGray, modifier = Modifier.align(Alignment.CenterVertically))
        }
    }
}