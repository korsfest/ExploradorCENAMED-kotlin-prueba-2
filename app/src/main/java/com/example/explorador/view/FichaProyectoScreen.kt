package com.example.explorador.view

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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.TrackChanges
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
fun FichaProyectoScreen(
    viewModel: ExploradorViewModel,
    nombreProyecto: String,
    onVolver: () -> Unit,
    onInvestigadorClick: (String) -> Unit,
    onPublicacionClick: (String) -> Unit
) {
    val proyectos by viewModel.proyectos.collectAsState()
    val investigadores by viewModel.investigadores.collectAsState()
    val publicaciones by viewModel.publicaciones.collectAsState()

    val proyecto = proyectos.find { it.titulo.equals(nombreProyecto, ignoreCase = true) }
    val invNombre = proyecto?.investigadorNombre ?: "Investigador CENAMAD"
    val inv = investigadores.find { it.nombre.equals(invNombre, ignoreCase = true) }
    val pubRelacionada = publicaciones.find { it.autor.equals(invNombre, ignoreCase = true) } ?: publicaciones.firstOrNull()

    val colorPrincipal = Color(0xFF2563EB)
    val colorFondoLigero = Color(0xFFE0E7FF)

    val iniciales = invNombre.split(" ").let { parts ->
        if (parts.size >= 3) "${parts[1].first()}${parts[2].first()}" else invNombre.take(2).uppercase()
    }

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
            Text("PROYECTO DE INVESTIGACIÓN", color = colorPrincipal, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Text(proyecto?.titulo ?: nombreProyecto, fontSize = 28.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 8.dp), lineHeight = 32.sp)
            Text(proyecto?.subtitulo ?: "Investigación aplicada en madera.", color = Color.Gray, fontSize = 16.sp)
        }

        item {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 8.dp)) {
                Surface(color = Color(0xFFE6F4EA), shape = RoundedCornerShape(16.dp)) {
                    Row(modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF1E8E3E), modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(proyecto?.estado ?: "En ejecución", color = Color(0xFF1E8E3E), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text("Año ${proyecto?.anio ?: "2026"}", color = Color.Gray, fontSize = 14.sp)
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.TrackChanges, contentDescription = null, tint = colorPrincipal)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("¿Qué busca resolver?", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF1F1A24))
                    }
                    Text(
                        proyecto?.subtitulo ?: "Desarrollo tecnológico y sostenibilidad forestal.",
                        fontSize = 14.sp,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                }
            }
        }

        item {
            Text("Investigador responsable", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(top = 8.dp, bottom = 4.dp))
            FichaInvestigadorCard(
                iniciales = iniciales,
                especialidad = inv?.especialidad ?: "Investigador CENAMAD",
                nombre = invNombre,
                colorPrincipal = colorPrincipal,
                colorFondo = colorFondoLigero,
                onClick = { onInvestigadorClick(invNombre) }
            )
        }

        if (pubRelacionada != null) {
            item {
                Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 4.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
                    Text("Publicación del proyecto", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text("1 publicación", color = colorPrincipal, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
                FichaPublicacionCard(
                    tipo = "PUBLICACIÓN · ${pubRelacionada.anio}",
                    titulo = pubRelacionada.titulo,
                    autoria = pubRelacionada.autor,
                    colorPrincipal = colorPrincipal,
                    colorFondo = colorFondoLigero,
                    onClick = { onPublicacionClick(pubRelacionada.titulo) }
                )
            }
        }
    }
}

@Composable
fun FichaInvestigadorCard(iniciales: String, especialidad: String, nombre: String, colorPrincipal: Color, colorFondo: Color, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0xFFE5E5E5))
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(48.dp).background(colorFondo, CircleShape), contentAlignment = Alignment.Center) {
                Text(iniciales, color = colorPrincipal, fontWeight = FontWeight.Bold)
            }
            Column(modifier = Modifier.weight(1f).padding(start = 12.dp, end = 8.dp)) {
                Text(especialidad, fontSize = 10.sp, color = colorPrincipal, fontWeight = FontWeight.Bold)
                Text(nombre, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.LightGray)
        }
    }
}

@Composable
fun FichaPublicacionCard(tipo: String, titulo: String, autoria: String, colorPrincipal: Color, colorFondo: Color, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0xFFE5E5E5))
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.Top) {
            Box(modifier = Modifier.size(48.dp).background(colorFondo, RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                Icon(Icons.Outlined.Article, contentDescription = null, tint = colorPrincipal)
            }
            Column(modifier = Modifier.weight(1f).padding(start = 12.dp, end = 8.dp)) {
                Text(tipo, fontSize = 10.sp, color = colorPrincipal, fontWeight = FontWeight.Bold)
                Text(titulo, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(vertical = 2.dp))
                Text(autoria, fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(top = 4.dp))
            }
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.LightGray, modifier = Modifier.align(Alignment.CenterVertically))
        }
    }
}