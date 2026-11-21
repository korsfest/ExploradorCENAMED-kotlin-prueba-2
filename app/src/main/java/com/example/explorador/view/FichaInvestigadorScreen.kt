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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Info
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
fun FichaInvestigadorScreen(
    viewModel: ExploradorViewModel,
    nombreInvestigador: String,
    onVolver: () -> Unit,
    onProyectoClick: (String) -> Unit = {}
) {
    val investigadores by viewModel.investigadores.collectAsState()
    val proyectos by viewModel.proyectos.collectAsState()

    val inv = investigadores.find { it.nombre.equals(nombreInvestigador, ignoreCase = true) }
    val proyectoRelacionado = proyectos.find { it.investigadorNombre.equals(nombreInvestigador, ignoreCase = true) }

    val lineaTexto = inv?.lineaNombre ?: ""
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

    val iniciales = nombreInvestigador.split(" ").let { parts ->
        if (parts.size >= 3) "${parts[1].first()}${parts[2].first()}" else nombreInvestigador.take(2).uppercase()
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            TopAppBar(
                title = { Text("Ficha de investigador", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
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
                colors = CardDefaults.cardColors(containerColor = Blanco),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, BordeTarjeta)
            ) {
                Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier.size(56.dp).background(colorFondoLigero, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(iniciales, color = colorPrincipal, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                    Column(modifier = Modifier.padding(start = 16.dp)) {
                        Text((inv?.especialidad ?: "Investigador CENAMAD").uppercase(), fontSize = 11.sp, color = colorPrincipal, fontWeight = FontWeight.Bold)
                        Text(nombreInvestigador, fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.padding(top = 2.dp))
                    }
                }
            }
        }

        item {
            Text(
                text = inv?.bio?.ifEmpty { "Línea de investigación: ${inv.lineaNombre}" } ?: "Investigador asociado a CENAMAD.",
                fontSize = 14.sp,
                color = GrisOscuro,
                modifier = Modifier.padding(horizontal = 4.dp),
                lineHeight = 20.sp
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
                            Icon(Icons.Default.Build, contentDescription = null, tint = colorPrincipal, modifier = Modifier.size(20.dp))
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
            Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 32.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.Info, contentDescription = null, tint = GrisTexto, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Modelo académico · No son perfiles oficiales", fontSize = 12.sp, color = GrisTexto)
            }
        }
    }
}
