package com.example.explorador.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.explorador.ui.theme.*
import com.example.explorador.viewmodel.ExploradorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublicacionesScreen(
    viewModel: ExploradorViewModel,
    lineaFiltro: String? = null,
    onVolver: () -> Unit,
    onVerPublicacion: (String) -> Unit
) {
    val todas by viewModel.publicaciones.collectAsState()
    val publicaciones = if (!lineaFiltro.isNullOrBlank() && lineaFiltro != "Búsqueda") {
        todas.filter { it.lineaNombre.equals(lineaFiltro, ignoreCase = true) }
    } else {
        todas
    }

    Column(modifier = Modifier.fillMaxSize().background(FondoApp)) {
        TopAppBar(
            title = { Text("Publicaciones", fontSize = 18.sp, fontWeight = FontWeight.Bold) },
            navigationIcon = {
                IconButton(onClick = onVolver) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver") }
            },
            actions = {
                IconButton(onClick = { }) { Icon(Icons.Default.Search, contentDescription = "Buscar") }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Blanco)
        )

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(publicaciones) { pub ->
                val esConstruccion = pub.lineaNombre.contains("Construcción", ignoreCase = true)
                val esBioproductos = pub.lineaNombre.contains("Bioproductos", ignoreCase = true)
                val esGestion = pub.lineaNombre.contains("Gestión", ignoreCase = true)

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
                    modifier = Modifier.fillMaxWidth().clickable { onVerPublicacion(pub.titulo) },
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
                            Icon(Icons.Outlined.Article, contentDescription = null, tint = colorPrincipal)
                        }
                        Column(modifier = Modifier.weight(1f).padding(start = 16.dp)) {
                            Text("${pub.tipo} · ${pub.anio}".uppercase(), fontSize = 10.sp, color = colorPrincipal, fontWeight = FontWeight.Bold)
                            Text(pub.titulo, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextoOscuro)
                        }
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = GrisClaro)
                    }
                }
            }
        }
    }
}
