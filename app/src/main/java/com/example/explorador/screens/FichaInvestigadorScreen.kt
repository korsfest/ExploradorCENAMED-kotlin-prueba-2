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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Layers
import androidx.compose.material.icons.outlined.ViewInAr
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
fun FichaInvestigadorScreen(nombreInvestigador: String, onVolver: () -> Unit) {
    val isAna = nombreInvestigador == "Ana Silva"

    val iniciales = if (isAna) "AS" else "CR"
    val tipoLabel = if (isAna) "INVESTIGADORA" else "INVESTIGADOR"
    val nombreCompleto = if (isAna) "Dra. Ana Silva" else "Dr. Carlos Ruiz"
    val especialidad = if (isAna) "Ingeniería Estructural" else "Arquitectura Sustentable"
    val descripcion = if (isAna) {
        "Investiga el diseño de sistemas estructurales de madera y su aplicación en vivienda modular. En esta muestra, conecta la evaluación técnica con recomendaciones de diseño."
    } else {
        "Investiga el desempeño sísmico y la seguridad de soluciones constructivas en madera. En esta muestra, aporta criterios estructurales críticos para edificaciones sostenibles."
    }
    val tag1 = if (isAna) "Estructuras de madera" else "Sismología"
    val tag2 = if (isAna) "Vivienda modular" else "Seguridad estructural"

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            TopAppBar(
                title = { Text("Ficha de ${tipoLabel.lowercase()}", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onVolver) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver") }
                },
                actions = {
                    IconButton(onClick = { }) { Icon(Icons.Default.Search, contentDescription = "Buscar", tint = Color(0xFF4A3780)) }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }

        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(64.dp).background(Color(0xFFF3EDF7), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(iniciales, color = Color(0xFF4A3780), fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }
                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text(tipoLabel, fontSize = 10.sp, color = Color(0xFF4A3780), fontWeight = FontWeight.Bold)
                    Text(nombreCompleto, fontWeight = FontWeight.Bold, fontSize = 24.sp, color = Color(0xFF32224A))
                    Text(especialidad, fontSize = 14.sp, color = Color.Gray)
                }
            }
        }

        item {
            Text(text = descripcion, fontSize = 14.sp, color = Color.DarkGray, lineHeight = 20.sp)
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Surface(shape = RoundedCornerShape(16.dp), color = Color(0xFFF3EDF7)) {
                    Text(tag1, modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), fontSize = 12.sp, color = Color(0xFF4A3780))
                }
                Surface(shape = RoundedCornerShape(16.dp), color = Color(0xFFF3EDF7)) {
                    Text(tag2, modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), fontSize = 12.sp, color = Color(0xFF4A3780))
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF3EDF7)),
                shape = RoundedCornerShape(12.dp),
                onClick = { }
            ) {
                Row(modifier = Modifier.padding(12.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.Layers, contentDescription = null, tint = Color(0xFF4A3780), modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Construcción Sustentable en Madera", color = Color(0xFF4A3780), fontSize = 14.sp, modifier = Modifier.weight(1f))
                    Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = Color(0xFF4A3780))
                }
            }
        }

        item {
            Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
                Text("Proyecto relacionado", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF32224A))
                Text("1 proyecto", color = Color(0xFF4A3780), fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            ItemRelacionadoCard(
                tipo = "2025 · En ejecución",
                titulo = "Prototipo de vivienda modular",
                descripcion = "Diseño de módulos habitacionales en madera.",
                rol = if (isAna) "Rol en la muestra: investigadora responsable" else "Rol en la muestra: co-investigador",
                icon = { Icon(Icons.Outlined.ViewInAr, contentDescription = null, tint = Color(0xFF4A3780)) }
            )
        }

        item {
            Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
                Text("Publicación relacionada", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF32224A))
                Text("1 publicación", color = Color(0xFF4A3780), fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            ItemRelacionadoCard(
                tipo = "Policy Brief · 2025",
                titulo = "Guía de diseño estructural",
                descripcion = "Criterios de diseño para sistemas modulares.",
                rol = if (isAna) "Coautoría con Dr. Carlos Ruiz" else "Coautoría con Dra. Ana Silva",
                icon = { Icon(Icons.Outlined.Article, contentDescription = null, tint = Color(0xFF4A3780)) }
            )
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
fun ItemRelacionadoCard(tipo: String, titulo: String, descripcion: String, rol: String, icon: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0xFFE5E5E5))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(40.dp).background(Color(0xFFF3EDF7), RoundedCornerShape(8.dp)), contentAlignment = Alignment.Center) { icon() }
                Column(modifier = Modifier.weight(1f).padding(start = 12.dp)) {
                    Text(tipo, fontSize = 10.sp, color = Color(0xFF4A3780), fontWeight = FontWeight.Bold)
                    Text(titulo, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(vertical = 2.dp))
                }
                Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = Color.LightGray)
            }
            Text(descripcion, fontSize = 14.sp, color = Color.DarkGray, modifier = Modifier.padding(top = 12.dp))
            Text(rol, fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(top = 4.dp))
        }
    }
}