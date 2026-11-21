package com.example.explorador.view

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.explorador.model.Proyecto
import com.example.explorador.ui.theme.*
import com.example.explorador.viewmodel.ExploradorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProyectosScreen(
    viewModel: ExploradorViewModel,
    lineaFiltro: String? = null,
    onVolver: () -> Unit,
    onVerProyecto: (String) -> Unit
) {
    val context = LocalContext.current
    val todos by viewModel.proyectos.collectAsState()
    val proyectos = if (!lineaFiltro.isNullOrBlank() && lineaFiltro != "Búsqueda") {
        todos.filter { it.lineaNombre.equals(lineaFiltro, ignoreCase = true) }
    } else {
        todos
    }

    var mostrarDialogo by remember { mutableStateOf(false) }
    var proyectoEnEdicion by remember { mutableStateOf<Proyecto?>(null) }
    var titulo by remember { mutableStateOf("") }
    var subtitulo by remember { mutableStateOf("") }
    var anio by remember { mutableStateOf("2026") }
    var estado by remember { mutableStateOf("En ejecución") }
    var investigador by remember { mutableStateOf("") }
    var fotoEvidencia by remember { mutableStateOf<Bitmap?>(null) }

    val camaraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            fotoEvidencia = bitmap
        }
    }

    val permisoCamaraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { concedido ->
        if (concedido) {
            camaraLauncher.launch()
        } else {
            Toast.makeText(context, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Proyectos", fontSize = 18.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Blanco)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    proyectoEnEdicion = null
                    titulo = ""
                    subtitulo = ""
                    anio = "2026"
                    estado = "En ejecución"
                    investigador = ""
                    fotoEvidencia = null
                    mostrarDialogo = true
                },
                containerColor = AzulPrimario,
                contentColor = Blanco
            ) {
                Icon(Icons.Default.Add, contentDescription = "Nuevo Proyecto")
            }
        },
        containerColor = FondoApp
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(proyectos) { proyecto ->
                val esConstruccion = proyecto.lineaNombre.contains("Construcción", ignoreCase = true)
                val esBioproductos = proyecto.lineaNombre.contains("Bioproductos", ignoreCase = true)
                val esGestion = proyecto.lineaNombre.contains("Gestión", ignoreCase = true)

                val colorPrincipal = when {
                    esConstruccion -> RosaLinea
                    esBioproductos -> VerdeLinea
                    esGestion -> NaranjaLinea
                    else -> CelesteLinea
                }

                val colorFondoLigero = when {
                    esConstruccion -> RosaLineaFondo
                    esBioproductos -> VerdeLineaFondo
                    esGestion -> NaranjaLineaFondo
                    else -> CelesteLineaFondo
                }

                Card(
                    modifier = Modifier.fillMaxWidth().clickable { onVerProyecto(proyecto.titulo) },
                    colors = CardDefaults.cardColors(containerColor = Blanco),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, BordeTarjeta)
                ) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier.size(48.dp).background(colorFondoLigero, RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Build, contentDescription = null, tint = colorPrincipal)
                        }
                        Column(modifier = Modifier.weight(1f).padding(start = 16.dp)) {
                            Text("${proyecto.anio} · ${proyecto.estado}".uppercase(), fontSize = 10.sp, color = colorPrincipal, fontWeight = FontWeight.Bold)
                            Text(proyecto.titulo, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextoOscuro)
                            if (proyecto.investigadorNombre.isNotBlank()) {
                                Text(proyecto.investigadorNombre, fontSize = 12.sp, color = GrisTexto)
                            }
                        }
                        IconButton(onClick = {
                            proyectoEnEdicion = proyecto
                            titulo = proyecto.titulo
                            subtitulo = proyecto.subtitulo
                            anio = proyecto.anio
                            estado = proyecto.estado
                            investigador = proyecto.investigadorNombre
                            fotoEvidencia = null
                            mostrarDialogo = true
                        }) {
                            Icon(Icons.Default.Edit, contentDescription = "Editar", tint = AzulPrimario)
                        }
                        IconButton(onClick = { viewModel.eliminarProyecto(proyecto.id) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = GrisTexto)
                        }
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = GrisClaro)
                    }
                }
            }
        }

        if (mostrarDialogo) {
            val esEdicion = proyectoEnEdicion != null
            AlertDialog(
                onDismissRequest = {
                    fotoEvidencia = null
                    proyectoEnEdicion = null
                    mostrarDialogo = false
                },
                title = { Text(if (esEdicion) "Editar Proyecto CENAMAD" else "Registrar Proyecto en CENAMAD") },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(value = titulo, onValueChange = { titulo = it }, label = { Text("Título del proyecto") })
                        OutlinedTextField(value = subtitulo, onValueChange = { subtitulo = it }, label = { Text("Subtítulo / Descripción") })
                        OutlinedTextField(value = anio, onValueChange = { anio = it }, label = { Text("Año") })
                        if (esEdicion) {
                            OutlinedTextField(value = estado, onValueChange = { estado = it }, label = { Text("Estado") })
                        }
                        OutlinedTextField(value = investigador, onValueChange = { investigador = it }, label = { Text("Investigador responsable") })

                        OutlinedButton(
                            onClick = {
                                val tienePermiso = ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.CAMERA
                                ) == PackageManager.PERMISSION_GRANTED

                                if (tienePermiso) {
                                    camaraLauncher.launch()
                                } else {
                                    permisoCamaraLauncher.launch(Manifest.permission.CAMERA)
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.CameraAlt, contentDescription = "Cámara")
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(if (fotoEvidencia == null) "Capturar Evidencia (Cámara)" else "Volver a tomar foto")
                        }

                        fotoEvidencia?.let { bitmap ->
                            Image(
                                bitmap = bitmap.asImageBitmap(),
                                contentDescription = "Evidencia capturada",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp)
                                    .clip(RoundedCornerShape(10.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        if (titulo.isNotBlank() && investigador.isNotBlank()) {
                            val detalleConFoto = if (fotoEvidencia != null) "$subtitulo (Con evidencia fotográfica)" else subtitulo
                            val editando = proyectoEnEdicion
                            if (editando != null) {
                                viewModel.actualizarProyecto(editando.id, titulo, detalleConFoto, anio, estado, investigador)
                            } else {
                                viewModel.crearProyecto(titulo, detalleConFoto, anio, "En ejecución", investigador)
                            }
                            titulo = ""; subtitulo = ""; investigador = ""; fotoEvidencia = null; proyectoEnEdicion = null
                            mostrarDialogo = false
                        }
                    }) { Text(if (esEdicion) "Actualizar" else "Guardar") }
                },
                dismissButton = {
                    TextButton(onClick = {
                        fotoEvidencia = null
                        proyectoEnEdicion = null
                        mostrarDialogo = false
                    }) { Text("Cancelar") }
                }
            )
        }
    }
}
