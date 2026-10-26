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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Factory
import androidx.compose.material.icons.filled.Forest
import androidx.compose.material.icons.filled.Science
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
import com.example.explorador.model.LineaInvestigacion

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleLineaScreen(
    linea: LineaInvestigacion,
    onVolver: () -> Unit,
    onVerInvestigadores: () -> Unit,
    onVerProyectos: () -> Unit,
    onVerPublicaciones: () -> Unit,
    onInvestigadorClick: (String) -> Unit
) {
    val esConstruccion = linea.titulo.contains("Construcción", ignoreCase = true)
    val esBioproductos = linea.titulo.contains("Bioproductos", ignoreCase = true)
    val esGestion = linea.titulo.contains("Gestión", ignoreCase = true)

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            TopAppBar(
                title = { Text("Línea de investigación", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
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
            Text(
                when {
                    esConstruccion -> "CONSTRUCCIÓN SUSTENTABLE"
                    esBioproductos -> "BIOMATERIALES Y CIRCULARIDAD"
                    esGestion -> "ECONOMÍA Y TERRITORIO"
                    else -> "TECNOLOGÍA Y MANUFACTURA"
                },
                color = Color(0xFF4A3780),
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
            Text(linea.titulo, fontSize = 28.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 8.dp), lineHeight = 32.sp)
            Text(linea.descripcion, color = Color.Gray, fontSize = 16.sp)
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF3EDF7)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(40.dp).background(Color(0xFFEADDFF), RoundedCornerShape(8.dp)), contentAlignment = Alignment.Center) {
                        Icon(
                            when {
                                esConstruccion -> Icons.Default.Build
                                esBioproductos -> Icons.Default.Science
                                esGestion -> Icons.Default.Forest
                                else -> Icons.Default.Factory
                            },
                            contentDescription = null,
                            tint = Color(0xFF4A3780),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Column(modifier = Modifier.weight(1f).padding(start = 12.dp)) {
                        Text(linea.titulo, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF4A3780))
                        Text(
                            when {
                                esConstruccion -> "2 investigadores · 2 proyectos"
                                esBioproductos -> "3 investigadores · 1 proyecto"
                                esGestion -> "2 investigadores · 1 proyecto"
                                else -> "2 investigadores · 1 proyecto"
                            },
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Color(0xFFE5E5E5))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Esta línea en la muestra", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF32224A))
                        Text("Datos sintéticos", fontSize = 12.sp, color = Color.Gray)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MetricBox(
                            valor = if (esConstruccion || esGestion || !esBioproductos) "2" else "3",
                            label = "Investigadores",
                            onClick = onVerInvestigadores
                        )
                        MetricBox(
                            valor = if (esConstruccion) "2" else "1",
                            label = "Proyectos",
                            onClick = onVerProyectos
                        )
                        MetricBox(
                            valor = if (esConstruccion) "2" else "1",
                            label = "Publicaciones",
                            onClick = onVerPublicaciones
                        )
                    }
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Investigadores de la línea", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(
                    when {
                        esConstruccion -> "2 investigadores"
                        esBioproductos -> "3 investigadores"
                        else -> "2 investigadores"
                    },
                    color = Color(0xFF4A3780),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        when {
            esConstruccion -> {
                item { InvestigadorCardMini("AS", "Ingeniería Estructural", "Dra. Ana Silva", "Diseño y evaluación de sistemas modulares de madera para vivienda.", onClick = { onInvestigadorClick("Dra. Ana Silva") }) }
                item { InvestigadorCardMini("CR", "Arquitectura Sustentable", "Dr. Carlos Ruiz", "Investiga el desempeño sísmico y la seguridad de soluciones constructivas en madera.", onClick = { onInvestigadorClick("Dr. Carlos Ruiz") }) }
            }
            esBioproductos -> {
                item { InvestigadorCardMini("LM", "Química de Materiales", "Dr. Luis Morales", "Explora la transformación de componentes de la madera en materiales de origen biológico.", onClick = { onInvestigadorClick("Dr. Luis Morales") }) }
                item { InvestigadorCardMini("SV", "Nanotecnología de la Madera", "Dra. Sofía Valdés", "Investigación sobre celulosa nanofibrilada para aplicaciones de alta resistencia.", onClick = { onInvestigadorClick("Dra. Sofía Valdés") }) }
                item { InvestigadorCardMini("MR", "Gestión Ambiental", "Dr. Mateo Rojas", "Análisis de huella de carbono y sostenibilidad en procesos de construcción.", onClick = { onInvestigadorClick("Dr. Mateo Rojas") }) }
            }
            esGestion -> {
                item { InvestigadorCardMini("GF", "Economía Forestal", "Dr. Gabriel Fuentes", "Análisis de cadenas de valor y mercados internacionales de productos madereros.", onClick = { onInvestigadorClick("Dr. Gabriel Fuentes") }) }
                item { InvestigadorCardMini("MP", "Sostenibilidad Territorial", "Dra. Marcela Parra", "Evaluación de impacto socioeconómico en territorios forestales.", onClick = { onInvestigadorClick("Dra. Marcela Parra") }) }
            }
            else -> {
                item { InvestigadorCardMini("IB", "Automatización Industrial", "Dr. Ignacio Becker", "Implementación de sistemas robóticos en la línea de producción de madera.", onClick = { onInvestigadorClick("Dr. Ignacio Becker") }) }
                item { InvestigadorCardMini("VS", "Manufactura Avanzada", "Dra. Valentina Soto", "Optimización de procesos de transformación industrial con control numérico.", onClick = { onInvestigadorClick("Dra. Valentina Soto") }) }
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
fun MetricBox(valor: String, label: String, onClick: () -> Unit) {
    Surface(
        modifier = Modifier.width(100.dp).clickable { onClick() },
        color = Color(0xFFF8F4FA),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(valor, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color(0xFF4A3780))
            Text(label, fontSize = 11.sp, color = Color.Gray, modifier = Modifier.padding(top = 2.dp))
        }
    }
}

@Composable
fun InvestigadorCardMini(iniciales: String, especialidad: String, nombre: String, descripcion: String, onClick: () -> Unit) {
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
                Text(nombre, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(vertical = 2.dp))
                Text(descripcion, fontSize = 13.sp, color = Color.DarkGray, maxLines = 2)
            }
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.LightGray)
        }
    }
}