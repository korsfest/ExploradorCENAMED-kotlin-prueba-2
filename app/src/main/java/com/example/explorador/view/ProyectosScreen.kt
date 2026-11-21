package com.example.explorador.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.explorador.viewmodel.ExploradorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProyectosScreen(
    viewModel: ExploradorViewModel,
    onVolver: () -> Unit,
    onVerProyecto: (String) -> Unit
) {
    val proyectos by viewModel.proyectos.collectAsState()
    var mostrarDialogo by remember { mutableStateOf(false) }

    var titulo by remember { mutableStateOf("") }
    var subtitulo by remember { mutableStateOf("") }
    var anio by remember { mutableStateOf("2026") }
    var investigador by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Proyectos", fontSize = 18.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { mostrarDialogo = true },
                containerColor = Color(0xFF2563EB),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Nuevo Proyecto")
            }
        },
        containerColor = Color(0xFFF8F9FA)
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(proyectos) { index, proyecto ->
                val colorPrincipal = when (index % 4) {
                    0 -> Color(0xFFE91E63)
                    1 -> Color(0xFF4CAF50)
                    2 -> Color(0xFFFF9800)
                    else -> Color(0xFF03A9F4)
                }

                val colorFondoLigero = when (index % 4) {
                    0 -> Color(0xFFFCE4EC)
                    1 -> Color(0xFFE8F5E9)
                    2 -> Color(0xFFFFF3E0)
                    else -> Color(0xFFE1F5FE)
                }

                Card(
                    modifier = Modifier.fillMaxWidth().clickable { onVerProyecto(proyecto.titulo) },
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, Color(0xFFE5E5E5))
                ) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier.size(48.dp).background(colorFondoLigero, RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Build, contentDescription = null, tint = colorPrincipal)
                        }
                        Column(modifier = Modifier.weight(1f).padding(start = 16.dp)) {
                            Text("${proyecto.anio} · ${proyecto.estado}", fontSize = 10.sp, color = colorPrincipal, fontWeight = FontWeight.Bold)
                            Text(proyecto.titulo, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF1F1A24))
                            if (proyecto.investigadorNombre.isNotBlank()) {
                                Text(proyecto.investigadorNombre, fontSize = 12.sp, color = Color.Gray)
                            }
                        }
                        IconButton(onClick = { viewModel.eliminarProyecto(proyecto.id) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Gray)
                        }
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.LightGray)
                    }
                }
            }
        }

        if (mostrarDialogo) {
            AlertDialog(
                onDismissRequest = { mostrarDialogo = false },
                title = { Text("Registrar Proyecto en CENAMAD") },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(value = titulo, onValueChange = { titulo = it }, label = { Text("Título del proyecto") })
                        OutlinedTextField(value = subtitulo, onValueChange = { subtitulo = it }, label = { Text("Subtítulo / Descripción") })
                        OutlinedTextField(value = anio, onValueChange = { anio = it }, label = { Text("Año") })
                        OutlinedTextField(value = investigador, onValueChange = { investigador = it }, label = { Text("Investigador responsable") })
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        if (titulo.isNotBlank() && investigador.isNotBlank()) {
                            viewModel.crearProyecto(titulo, subtitulo, anio, "En ejecución", investigador)
                            titulo = ""; subtitulo = ""; investigador = ""
                            mostrarDialogo = false
                        }
                    }) { Text("Guardar") }
                },
                dismissButton = {
                    TextButton(onClick = { mostrarDialogo = false }) { Text("Cancelar") }
                }
            )
        }
    }
}