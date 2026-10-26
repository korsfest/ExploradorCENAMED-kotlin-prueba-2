package com.example.explorador.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Factory
import androidx.compose.material.icons.filled.Forest
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.explorador.model.LineaInvestigacion

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExplorarScreen(onLineaClick: (LineaInvestigacion) -> Unit) {
    val lineas = listOf(
        LineaInvestigacion(
            id = "1",
            titulo = "Construcción Sustentable en Madera",
            descripcion = "Desarrollo de sistemas constructivos avanzados, diseño estructural y eficiencia energética.",
            investigadores = emptyList(),
            proyectos = emptyList(),
            publicaciones = emptyList()
        ),
        LineaInvestigacion(
            id = "2",
            titulo = "Bioproductos y Biomateriales",
            descripcion = "Explora el potencial de la madera y sus componentes para desarrollar materiales de origen renovable.",
            investigadores = emptyList(),
            proyectos = emptyList(),
            publicaciones = emptyList()
        ),
        LineaInvestigacion(
            id = "3",
            titulo = "Gestión y Economía Forestal",
            descripcion = "Análisis de cadenas de valor, mercados de productos madereros y sostenibilidad territorial.",
            investigadores = emptyList(),
            proyectos = emptyList(),
            publicaciones = emptyList()
        ),
        LineaInvestigacion(
            id = "4",
            titulo = "Innovación en Procesos Industriales",
            descripcion = "Automatización, robótica y digitalización en la transformación industrial de la madera.",
            investigadores = emptyList(),
            proyectos = emptyList(),
            publicaciones = emptyList()
        )
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("CENAMAD Explorador", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1F1A24))
                    Text("Explorador de conocimiento", fontSize = 14.sp, color = Color.Gray)
                }
                IconButton(onClick = {}) {
                    Icon(Icons.Outlined.Search, contentDescription = "Buscar", tint = Color(0xFF1F1A24))
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF2B2042)),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text("CIENCIA, TECNOLOGÍA Y MADERA", color = Color(0xFFD0BCFF), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Text(
                        "Conocimiento que construye futuro.",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp),
                        lineHeight = 30.sp
                    )
                    Text("Conecta líneas, personas y hallazgos de la industria de la madera.", color = Color(0xFFE8DEF8), fontSize = 14.sp)
                }
            }
        }

        item {
            Text("Líneas de investigación", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1F1A24), modifier = Modifier.padding(top = 8.dp))
        }

        items(lineas.size) { index ->
            val linea = lineas[index]
            LineaInvestigacionCard(
                titulo = linea.titulo,
                detalle = when(index) {
                    0 -> "2 investigadores · 2 proyectos"
                    1 -> "3 investigadores · 1 proyectos"
                    2 -> "2 investigadores · 1 proyectos"
                    else -> "2 investigadores · 2 proyectos"
                },
                icono = when(index) {
                    0 -> Icons.Default.Build
                    1 -> Icons.Default.Science
                    2 -> Icons.Default.Forest
                    else -> Icons.Default.Factory
                },
                onClick = { onLineaClick(linea) }
            )
        }
    }
}

@Composable
fun LineaInvestigacionCard(titulo: String, detalle: String, icono: ImageVector, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0xFFE5E5E5))
    ) {
        Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(48.dp).background(Color(0xFFF3EDF7), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icono, contentDescription = null, tint = Color(0xFF4A3780))
            }
            Column(modifier = Modifier.weight(1f).padding(start = 16.dp)) {
                Text(titulo, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF1F1A24))
                Text(detalle, fontSize = 13.sp, color = Color.Gray, modifier = Modifier.padding(top = 4.dp))
            }
            Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = Color.LightGray)
        }
    }
}