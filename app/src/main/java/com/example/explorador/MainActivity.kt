package com.example.explorador

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.explorador.data.MockData
import com.example.explorador.model.LineaInvestigacion

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = Color(0xFF4A3780),
                    background = Color(0xFFF8F9FA)
                )
            ) {
                var pestañaActual by remember { mutableStateOf("explorar") }
                var lineaSeleccionada by remember { mutableStateOf<LineaInvestigacion?>(null) }

                Scaffold(
                    bottomBar = {
                        if (lineaSeleccionada == null) {
                            NavigationBar(containerColor = Color.White) {
                                NavigationBarItem(
                                    icon = { Icon(Icons.Outlined.Explore, contentDescription = "Explorar") },
                                    label = { Text("Explorar") },
                                    selected = pestañaActual == "explorar",
                                    onClick = { pestañaActual = "explorar" }
                                )
                                NavigationBarItem(
                                    icon = { Icon(Icons.Outlined.Search, contentDescription = "Buscar") },
                                    label = { Text("Buscar") },
                                    selected = pestañaActual == "buscar",
                                    onClick = { pestañaActual = "buscar" }
                                )
                            }
                        }
                    }
                ) { padding ->
                    Surface(
                        modifier = Modifier.fillMaxSize().padding(padding),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        if (lineaSeleccionada == null) {
                            if (pestañaActual == "explorar") {
                                ExplorarScreen(onLineaClick = { linea -> lineaSeleccionada = linea })
                            } else {
                                BuscarScreen() // Vista base para "image_e36628.png"
                            }
                        } else {
                            DetalleLineaScreen(
                                linea = lineaSeleccionada!!,
                                onVolver = { lineaSeleccionada = null }
                            ) // Vista base para "image_e36647.png"
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExplorarScreen(onLineaClick: (LineaInvestigacion) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            TopAppBar(
                title = {
                    Column {
                        Text("CENAMAD Explorador", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text("Explorador de conocimiento", fontSize = 12.sp, color = Color.Gray)
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(Icons.Default.Search, contentDescription = "Buscar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }

        // Banner Oscuro
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF32224A)),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text("CIENCIA, TECNOLOGÍA Y MADERA", color = Color.LightGray, fontSize = 10.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Conocimiento que\nconstruye futuro.",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 28.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        "Conecta líneas, personas y hallazgos de la industria de la madera.",
                        color = Color.LightGray,
                        fontSize = 14.sp
                    )
                }
            }
        }

        item {
            Text(
                "Líneas de investigación",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        items(MockData.lineas) { linea ->
            Card(
                modifier = Modifier.fillMaxWidth().clickable { onLineaClick(linea) },
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Build, // Placeholder para el icono de Figma
                        contentDescription = null,
                        modifier = Modifier.size(40.dp).padding(end = 12.dp),
                        tint = Color(0xFF4A3780)
                    )
                    Column {
                        Text(linea.titulo, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text(
                            "${linea.investigadores.size} investigadores · ${linea.proyectos.size} proyectos",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleLineaScreen(linea: LineaInvestigacion, onVolver: () -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            TopAppBar(
                title = { Text("Línea de investigación", fontSize = 16.sp) },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
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

        item {
            Text("Explora la línea", fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 16.dp))
        }

        // Navegación interna (Listas)
        item {
            Card(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column {
                    ListItem(
                        headlineContent = { Text("Investigadores", fontWeight = FontWeight.Bold) },
                        supportingContent = { Text("Conoce a quienes investigan") },
                        leadingContent = { Icon(Icons.Default.Person, contentDescription = null) },
                        trailingContent = { Text(linea.investigadores.size.toString()) }
                    )
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                    ListItem(
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
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3EDF7)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(numero, fontSize = 24.sp, color = Color(0xFF4A3780))
            Text(etiqueta, fontSize = 10.sp, color = Color.Gray)
        }
    }
}

@Composable
fun BuscarScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Pantalla de búsqueda en construcción", color = Color.Gray)
    }
}
// UI: Estructura base y tema principal configurado

// Feature: Implementacion de vista ExplorarScreen y MockData
