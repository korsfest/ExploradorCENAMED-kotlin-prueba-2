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
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.explorador.ui.theme.*
import com.example.explorador.viewmodel.ExploradorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FichaPublicacionScreen(
    viewModel: ExploradorViewModel,
    nombrePublicacion: String,
    onVolver: () -> Unit,
    onInvestigadorClick: (String) -> Unit,
    onProyectoClick: (String) -> Unit,
    onInicioClick: () -> Unit
) {
    val publicaciones by viewModel.publicaciones.collectAsState()
    val investigadores by viewModel.investigadores.collectAsState()
    val proyectos by viewModel.proyectos.collectAsState()

    val pub = publicaciones.find { it.titulo.equals(nombrePublicacion, ignoreCase = true) }
    val autorNombre = pub?.autor ?: "Dra. Ana Silva"
    val inv = investigadores.find { it.nombre.equals(autorNombre, ignoreCase = true) }
    val proyectoRelacionado = proyectos.find { it.titulo.equals(pub?.proyectoTitulo ?: "", ignoreCase = true) }
        ?: proyectos.find { it.investigadorNombre.equals(autorNombre, ignoreCase = true) }
        ?: proyectos.firstOrNull()

    val lineaTexto = pub?.lineaNombre ?: inv?.lineaNombre ?: ""
    val esConstruccion = lineaTexto.contains("Construcción", ignoreCase = true)
    val esBioproductos = lineaTexto.contains("Bioproductos", ignoreCase = true)
    val esGestion = lineaTexto.contains("Gestión", ignoreCase = true)

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

    val titulo = pub?.titulo ?: nombrePublicacion
    val anio = pub?.anio ?: "2025"
    val tipo = (pub?.tipo ?: if (titulo.contains("Guía", ignoreCase = true)) "Policy Brief" else "Artículo científico").uppercase()

    val autorIniciales = autorNombre.split(" ").let { parts ->
        if (parts.size >= 3) "${parts[1].first()}${parts[2].first()}" else autorNombre.take(2).uppercase()
    }
    val autorEsp = (inv?.especialidad ?: "Investigador CENAMAD").uppercase()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            TopAppBar(
                title = { Text("Ficha de publicación", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onVolver) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver") }
                },
                actions = {
                    IconButton(onClick = { }) { Icon(Icons.Default.Search, contentDescription = "Buscar") }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Transparente)
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = colorFondoLigero),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text(tipo, color = colorPrincipal, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Text(anio, color = colorPrincipal, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                    }
                    Text(
                        titulo,
                        color = TextoOscuro,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 12.dp),
                        lineHeight = 32.sp
                    )
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 4.dp)) {
                        Icon(Icons.Default.Book, contentDescription = null, tint = colorPrincipal, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(pub?.contexto?.ifEmpty { lineaTexto } ?: "Diseño de sistemas modulares en madera", color = GrisOscuro, fontSize = 14.sp)
                    }
                }
            }
        }

        item {
            Text("Resumen", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = TextoOscuro)
            Text(
                pub?.resumen?.ifEmpty { "Documento científico publicado por $autorNombre en el marco de las líneas de investigación de CENAMAD ($anio)." }
                    ?: "Criterios prácticos para diseñar módulos funcionales y montables.",
                fontSize = 14.sp,
                color = GrisOscuro,
                modifier = Modifier.padding(top = 8.dp),
                lineHeight = 20.sp
            )
            Text("El resumen es una síntesis académica ilustrativa.", fontSize = 12.sp, color = GrisTexto, modifier = Modifier.padding(top = 8.dp))
        }

        item {
            Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
                Text("Autoría", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("1 autor", color = colorPrincipal, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }

        item {
            AutorCardPublicacion(
                iniciales = autorIniciales,
                especialidad = autorEsp,
                nombre = autorNombre,
                colorPrincipal = colorPrincipal,
                colorFondo = colorFondoLigero,
                onClick = { onInvestigadorClick(autorNombre) }
            )
        }

        if (proyectoRelacionado != null) {
            item {
                Text("Proyecto relacionado", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(top = 8.dp, bottom = 8.dp))
                Card(
                    modifier = Modifier.fillMaxWidth().clickable { onProyectoClick(proyectoRelacionado.titulo) },
                    colors = CardDefaults.cardColors(containerColor = Blanco),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, BordeTarjeta)
                ) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(40.dp).background(colorFondoLigero, RoundedCornerShape(8.dp)), contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.Book, contentDescription = null, tint = colorPrincipal, modifier = Modifier.size(20.dp))
                        }
                        Column(modifier = Modifier.weight(1f).padding(start = 12.dp)) {
                            Text("${proyectoRelacionado.anio} · ${proyectoRelacionado.estado}".uppercase(), fontSize = 10.sp, color = colorPrincipal, fontWeight = FontWeight.Bold)
                            Text(proyectoRelacionado.titulo, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        }
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = GrisClaro)
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth().clickable { onInicioClick() },
                colors = CardDefaults.cardColors(containerColor = colorFondoLigero),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Home, contentDescription = null, tint = colorPrincipal)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Volver al inicio", color = colorPrincipal, fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = colorPrincipal)
                }
            }
        }

        item {
            Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 32.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.Info, contentDescription = null, tint = GrisTexto, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Modelo académico · No son documentos oficiales", fontSize = 12.sp, color = GrisTexto)
            }
        }
    }
}

@Composable
private fun AutorCardPublicacion(iniciales: String, especialidad: String, nombre: String, colorPrincipal: Color, colorFondo: Color, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Blanco),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, BordeTarjeta)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(48.dp).background(colorFondo, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(iniciales, color = colorPrincipal, fontWeight = FontWeight.Bold)
            }
            Column(modifier = Modifier.weight(1f).padding(start = 12.dp, end = 8.dp)) {
                Text(especialidad, fontSize = 10.sp, color = colorPrincipal, fontWeight = FontWeight.Bold)
                Text(nombre, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = GrisClaro)
        }
    }
}
