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
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Science
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.explorador.model.LineaInvestigacion

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleLineaScreen(
    linea: LineaInvestigacion,
    onVolver: () -> Unit,
    onVerInvestigadores: () -> Unit,
    onVerProyectos: () -> Unit,
    onVerPublicaciones: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            TopAppBar(
                title = { Text("Línea de investigación", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = { IconButton(onClick = onVolver) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver") } },
                actions = { IconButton(onClick = { }) { Icon(Icons.Default.Search, contentDescription = "Buscar", tint = Color(0xFF4A3780)) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }

        item {
            Text("MATERIALES Y CIRCULARIDAD", fontSize = 10.sp, color = Color(0xFF4A3780), fontWeight = FontWeight.Bold)
            Text("Bioproductos y Biomateriales", fontWeight = FontWeight.Bold, fontSize = 28.sp, color = Color(0xFF32224A), lineHeight = 34.sp)
            Text("Explora el potencial de la madera y sus componentes para desarrollar materiales de origen renovable.", fontSize = 14.sp, color = Color.DarkGray, modifier = Modifier.padding(top = 8.dp))
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF3EDF7)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.Science, contentDescription = null, tint = Color(0xFF4A3780), modifier = Modifier.size(40.dp))
                    Column(modifier = Modifier.padding(start = 16.dp)) {
                        Text("Dar valor a lo renovable", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF32224A))
                        Text("Química de materiales y nuevas aplicaciones de la biomasa forestal.", fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(top = 2.dp))
                    }
                }
            }
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Surface(shape = RoundedCornerShape(16.dp), color = Color(0xFFF3EDF7)) { Text("Biomasa", modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), fontSize = 12.sp, color = Color(0xFF4A3780)) }
                Surface(shape = RoundedCornerShape(16.dp), color = Color(0xFFF3EDF7)) { Text("Materiales renovables", modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), fontSize = 12.sp, color = Color(0xFF4A3780)) }
            }
        }

        item {
            Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
                Text("Esta línea en la muestra", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF32224A))
                Text("Datos sintéticos", color = Color.Gray, fontSize = 10.sp)
            }
            Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                StatCard(numero = "1", etiqueta = "Investigadores", modifier = Modifier.weight(1f), onClick = onVerInvestigadores)
                StatCard(numero = "0", etiqueta = "Proyectos", modifier = Modifier.weight(1f), onClick = onVerProyectos)
                StatCard(numero = "0", etiqueta = "Publicaciones", modifier = Modifier.weight(1f), onClick = onVerPublicaciones)
            }
        }

        item {
            Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
                Text("Investigador de la línea", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF32224A))
                Text("1 investigador", color = Color(0xFF4A3780), fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = Modifier.fillMaxWidth().clickable { onVerInvestigadores() },
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Color(0xFFE5E5E5))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(40.dp).background(Color(0xFFEADDFF), CircleShape), contentAlignment = Alignment.Center) { Text("LM", color = Color(0xFF4A3780), fontWeight = FontWeight.Bold) }
                        Column(modifier = Modifier.weight(1f).padding(start = 12.dp)) {
                            Text("Química de Materiales", fontSize = 10.sp, color = Color(0xFF4A3780), fontWeight = FontWeight.Bold)
                            Text("Dr. Luis Morales", fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(vertical = 2.dp))
                        }
                        Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = Color.LightGray)
                    }
                    Text("Explora la transformación de componentes de la madera en materiales de origen biológico.", fontSize = 14.sp, color = Color.DarkGray, modifier = Modifier.padding(top = 12.dp))
                    Text("Enfoque sintético: valorización de biomasa", fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(top = 8.dp))
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF3EDF7)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Una muestra acotada, no el catálogo completo", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF32224A))
                    Text("Esta demostración incluye al Investigador de la línea. Aún no se incorporan proyectos ni publicaciones de Bioproductos y Biomateriales.", fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(top = 4.dp))
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
fun StatCard(numero: String, etiqueta: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        modifier = modifier.clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3EDF7)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(numero, fontSize = 24.sp, fontWeight = FontWeight.Light, color = Color(0xFF4A3780))
            Text(etiqueta, fontSize = 10.sp, color = Color.Gray, modifier = Modifier.padding(top = 4.dp))
        }
    }
}