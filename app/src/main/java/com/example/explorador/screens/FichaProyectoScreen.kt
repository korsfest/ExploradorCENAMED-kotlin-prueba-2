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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.TrackChanges
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
fun FichaProyectoScreen(
    nombreProyecto: String,
    onVolver: () -> Unit,
    onInvestigadorClick: (String) -> Unit
) {
    val esModular = nombreProyecto.contains("vivienda modular", ignoreCase = true)

    val titulo = if (esModular) "Prototipo de vivienda modular" else "Análisis sísmico de uniones"
    val subtitulo = if (esModular)
        "Una propuesta para explorar la construcción de viviendas mediante módulos de madera."
    else
        "Evaluación de uniones ante sismos y revisión complementaria del desempeño al fuego en CLT."
    val estado = if (esModular) "En ejecución" else "Finalizado"
    val anio = if (esModular) "Año 2025" else "Año 2024"

    val investigadorNombre = if (esModular) "Dra. Ana Silva" else "Dr. Carlos Ruiz"
    val investigadorIniciales = if (esModular) "AS" else "CR"
    val investigadorEspecialidad = if (esModular) "Ingeniería Estructural" else "Arquitectura Sustentable"

    val pubTipo = if (esModular) "Policy Brief · 2025" else "Artículo científico · 2024"
    val pubTitulo = if (esModular) "Guía de diseño estructural" else "Resistencia al fuego en CLT"
    val pubAutoria = if (esModular) "Dra. Ana Silva y Dr. Carlos Ruiz" else "Dr. Carlos Ruiz · Análisis sísmico de uniones"

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            TopAppBar(
                title = { Text("Ficha de proyecto", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
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
            Text("PROYECTO DE INVESTIGACIÓN", color = Color(0xFF4A3780), fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Text(titulo, fontSize = 28.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 8.dp), lineHeight = 32.sp)
            Text(subtitulo, color = Color.Gray, fontSize = 16.sp)
        }

        item {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 8.dp)) {
                Surface(
                    color = Color(0xFFE6F4EA),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF1E8E3E), modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(estado, color = Color(0xFF1E8E3E), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(anio, color = Color.Gray, fontSize = 14.sp)
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF3EDF7)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Build, contentDescription = null, tint = Color(0xFF4A3780), modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Construcción Sustentable en Madera", color = Color(0xFF4A3780), fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                    Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = Color(0xFF4A3780))
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F4FA)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.TrackChanges, contentDescription = null, tint = Color(0xFF4A3780))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("¿Qué busca resolver?", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF32224A))
                    }
                    Text(
                        if (esModular)
                            "Evaluar una solución modular que integre diseño estructural, facilidad de montaje y uso eficiente de madera. La guía relacionada sintetiza criterios para su diseño."
                        else
                            "Analizar la respuesta estructural de uniones en construcciones de madera ante eventos sísmicos y validar su comportamiento al fuego.",
                        fontSize = 14.sp,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                    Text("Objetivo y relaciones sintéticos para el caso académico.", fontSize = 12.sp, color = Color(0xFF4A3780))
                }
            }
        }

        item {
            Text("Investigadora responsable", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(top = 8.dp, bottom = 4.dp))
            FichaInvestigadorCard(
                iniciales = investigadorIniciales,
                especialidad = investigadorEspecialidad,
                nombre = investigadorNombre,
                onClick = { onInvestigadorClick(investigadorNombre) }
            )
        }

        item {
            Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 4.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
                Text("Publicación del proyecto", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("1 publicación", color = Color(0xFF4A3780), fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            FichaPublicacionCard(
                tipo = pubTipo,
                titulo = pubTitulo,
                autoria = pubAutoria
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
fun FichaInvestigadorCard(iniciales: String, especialidad: String, nombre: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0xFFE5E5E5))
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(48.dp).background(Color(0xFFEADDFF), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(iniciales, color = Color(0xFF4A3780), fontWeight = FontWeight.Bold)
            }
            Column(modifier = Modifier.weight(1f).padding(start = 12.dp, end = 8.dp)) {
                Text(especialidad, fontSize = 10.sp, color = Color(0xFF4A3780), fontWeight = FontWeight.Bold)
                Text(nombre, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
            Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = Color.LightGray)
        }
    }
}

@Composable
fun FichaPublicacionCard(tipo: String, titulo: String, autoria: String) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0xFFE5E5E5))
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.Top) {
            Box(
                modifier = Modifier.size(48.dp).background(Color(0xFFF3EDF7), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Outlined.Article, contentDescription = null, tint = Color(0xFF4A3780))
            }
            Column(modifier = Modifier.weight(1f).padding(start = 12.dp, end = 8.dp)) {
                Text(tipo, fontSize = 10.sp, color = Color(0xFF4A3780), fontWeight = FontWeight.Bold)
                Text(titulo, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(vertical = 2.dp))
                Text(autoria, fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(top = 4.dp))
            }
            Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = Color.LightGray, modifier = Modifier.align(Alignment.CenterVertically))
        }
    }
}