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
fun FichaInvestigadorScreen(
    nombreInvestigador: String,
    onVolver: () -> Unit,
    onProyectoClick: (String) -> Unit = {}
) {
    val esAnaSilva = nombreInvestigador.contains("Ana Silva", ignoreCase = true)

    val iniciales = if (esAnaSilva) "AS" else "CR"
    val especialidad = if (esAnaSilva) "Ingeniería Estructural" else "Arquitectura Sustentable"
    val descripcion = if (esAnaSilva)
        "Diseño y evaluación de sistemas modulares de madera para vivienda."
    else
        "Investiga el desempeño sísmico y la seguridad de soluciones constructivas en madera."

    val proyectoRelacionado = if (esAnaSilva) "Prototipo de vivienda modular" else "Análisis sísmico de uniones"
    val proyectoDetalle = if (esAnaSilva) "2025 · En ejecución" else "2024 · Finalizado"

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            TopAppBar(
                title = { Text("Ficha de investigador", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
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
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Color(0xFFE5E5E5))
            ) {
                Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier.size(56.dp).background(Color(0xFFEADDFF), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(iniciales, color = Color(0xFF4A3780), fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                    Column(modifier = Modifier.padding(start = 16.dp)) {
                        Text(especialidad, fontSize = 12.sp, color = Color(0xFF4A3780), fontWeight = FontWeight.Bold)
                        Text(nombreInvestigador, fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.padding(top = 2.dp))
                    }
                }
            }
        }

        item {
            Text(descripcion, fontSize = 14.sp, color = Color.DarkGray, modifier = Modifier.padding(horizontal = 4.dp))
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
                        Text(proyectoDetalle, fontSize = 10.sp, color = Color(0xFF4A3780), fontWeight = FontWeight.Bold)
                        Text(proyectoRelacionado, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    }
                    Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = Color.LightGray)
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