package com.example.explorador.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.explorador.model.LineaInvestigacion

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleLineaScreen(linea: LineaInvestigacion, onVolver: () -> Unit, onVerInvestigadores: () -> Unit, onVerProyectos: () -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            TopAppBar(
                title = { Text("Línea de investigación", fontSize = 16.sp) },
                navigationIcon = { IconButton(onClick = onVolver) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver") } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
        item {
            Text("CONSTRUCCIÓN Y HÁBITAT", color = Color(0xFF4A3780), fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Text(linea.titulo, fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 8.dp))
            Text("Explora soluciones en madera para una edificación eficiente, segura y de menor impacto ambiental.", color = Color.DarkGray)
        }
        item {
            Text("Esta línea en la muestra", fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 16.dp, bottom = 8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                StatCard(linea.investigadores.size.toString(), "Investigadores", Modifier.weight(1f))
                StatCard(linea.proyectos.size.toString(), "Proyectos", Modifier.weight(1f))
                StatCard(linea.publicaciones.size.toString(), "Publicaciones", Modifier.weight(1f))
            }
        }
        item { Text("Explora la línea", fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 16.dp)) }
        item {
            Card(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Column {
                    ListItem(
                        modifier = Modifier.clickable { onVerInvestigadores() },
                        headlineContent = { Text("Investigadores", fontWeight = FontWeight.Bold) },
                        supportingContent = { Text("Conoce a quienes investigan") },
                        leadingContent = { Icon(Icons.Default.Person, contentDescription = null) },
                        trailingContent = { Text(linea.investigadores.size.toString()) }
                    )
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                    ListItem(
                        modifier = Modifier.clickable { onVerProyectos() },
                        headlineContent = { Text("Proyectos", fontWeight = FontWeight.Bold) },
                        supportingContent = { Text("En ejecución y finalizados") },
                        leadingContent = { Icon(Icons.Default.Build, contentDescription = null) },
                        trailingContent = { Text(linea.proyectos.size.toString()) }
                    )
                }
            }
        }
    }
}

@Composable
fun StatCard(numero: String, etiqueta: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier, colors = CardDefaults.cardColors(containerColor = Color(0xFFF3EDF7)), elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(numero, fontSize = 24.sp, color = Color(0xFF4A3780))
            Text(etiqueta, fontSize = 10.sp, color = Color.Gray)
        }
    }
}