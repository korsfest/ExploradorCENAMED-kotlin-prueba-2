package com.example.explorador.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Factory
import androidx.compose.material.icons.filled.Forest
import androidx.compose.material.icons.filled.Science
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.explorador.model.LineaInvestigacion
import com.example.explorador.ui.theme.*
import com.example.explorador.viewmodel.ExploradorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExplorarScreen(
    viewModel: ExploradorViewModel,
    nombreUsuario: String,
    onLineaClick: (LineaInvestigacion) -> Unit
) {
    val lineas by viewModel.lineas.collectAsState()
    val cargando by viewModel.cargando.collectAsState()
    val error by viewModel.mensajeError.collectAsState()

    val bannerGradient = Brush.linearGradient(
        colors = listOf(AzulPrimario, MoradoBanner, RosaBanner)
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("CENAMAD Explorador", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                    if (nombreUsuario.isNotEmpty()) {
                        Text("¡Bienvenido, $nombreUsuario!", fontSize = 14.sp, color = AzulPrimario, fontWeight = FontWeight.Medium, modifier = Modifier.padding(top = 2.dp))
                    }
                    Text("Explorador de conocimiento", fontSize = 14.sp, color = GrisTexto, modifier = Modifier.padding(top = 2.dp))
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(modifier = Modifier.background(bannerGradient)) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Text("CIENCIA, TECNOLOGÍA Y MADERA", color = AzulFondoClaro, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        Text(
                            "Conocimiento que construye futuro.",
                            color = Blanco,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 8.dp),
                            lineHeight = 30.sp
                        )
                        Text("Conecta líneas, personas y hallazgos de la industria de la madera.", color = MoradoFondoClaro, fontSize = 14.sp)
                    }
                }
            }
        }

        item {
            Text("Líneas de investigación", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextoOscuro, modifier = Modifier.padding(top = 8.dp))
        }

        if (cargando && lineas.isEmpty()) {
            item {
                Box(modifier = Modifier.fillMaxWidth().padding(24.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = AzulPrimario)
                }
            }
        } else if (error != null && lineas.isEmpty()) {
            item {
                Text(text = error ?: "Error al conectar con el servidor", color = RojoError, fontSize = 14.sp)
            }
        } else {
            items(lineas.size) { index ->
                val linea = lineas[index]
                LineaInvestigacionCard(
                    titulo = linea.titulo,
                    detalle = linea.detalle.ifEmpty { "Línea CENAMAD" },
                    icono = when (index % 4) {
                        0 -> Icons.Default.Build
                        1 -> Icons.Default.Science
                        2 -> Icons.Default.Forest
                        else -> Icons.Default.Factory
                    },
                    colorIcono = when (index % 4) {
                        0 -> RosaLinea
                        1 -> VerdeLinea
                        2 -> NaranjaLinea
                        else -> CelesteLinea
                    },
                    fondoIcono = when (index % 4) {
                        0 -> RosaLineaFondo
                        1 -> VerdeLineaFondo
                        2 -> NaranjaLineaFondo
                        else -> CelesteLineaFondo
                    },
                    onClick = { onLineaClick(linea) }
                )
            }
        }
    }
}

@Composable
fun LineaInvestigacionCard(titulo: String, detalle: String, icono: ImageVector, colorIcono: Color, fondoIcono: Color, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Blanco),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, BordeSutil)
    ) {
        Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(48.dp).background(fondoIcono, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icono, contentDescription = null, tint = colorIcono)
            }
            Column(modifier = Modifier.weight(1f).padding(start = 16.dp)) {
                Text(titulo, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextoOscuro)
                Text(detalle, fontSize = 13.sp, color = GrisTexto, modifier = Modifier.padding(top = 4.dp))
            }
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = GrisClaro)
        }
    }
}
