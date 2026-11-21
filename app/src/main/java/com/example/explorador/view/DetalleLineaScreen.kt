package com.example.explorador.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.explorador.model.LineaInvestigacion
import com.example.explorador.ui.theme.*
import com.example.explorador.viewmodel.ExploradorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleLineaScreen(
    viewModel: ExploradorViewModel,
    linea: LineaInvestigacion,
    onVolver: () -> Unit,
    onVerInvestigadores: () -> Unit,
    onVerProyectos: () -> Unit,
    onVerPublicaciones: () -> Unit,
    onInvestigadorClick: (String) -> Unit
) {
    val investigadores by viewModel.investigadores.collectAsState()
    val proyectos by viewModel.proyectos.collectAsState()
    val publicaciones by viewModel.publicaciones.collectAsState()

    val invDeLinea = investigadores.filter { it.lineaNombre.equals(linea.titulo, ignoreCase = true) }
    val projDeLinea = proyectos.filter { it.lineaNombre.equals(linea.titulo, ignoreCase = true) }
    val pubDeLinea = publicaciones.filter { it.lineaNombre.equals(linea.titulo, ignoreCase = true) }

    val esConstruccion = linea.titulo.contains("Construcción", ignoreCase = true)
    val esBioproductos = linea.titulo.contains("Bioproductos", ignoreCase = true)
    val esGestion = linea.titulo.contains("Gestión", ignoreCase = true)

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
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Transparente)
            )
        }

        item {
            Text(
                linea.tag.ifEmpty {
                    when {
                        esConstruccion -> "CONSTRUCCIÓN SUSTENTABLE"
                        esBioproductos -> "BIOMATERIALES Y CIRCULARIDAD"
                        esGestion -> "ECONOMÍA Y TERRITORIO"
                        else -> "TECNOLOGÍA Y MANUFACTURA"
                    }
                },
                color = colorPrincipal,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
            Text(linea.titulo, fontSize = 28.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 8.dp), lineHeight = 32.sp)
            Text(linea.descripcion, color = GrisTexto, fontSize = 16.sp)
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = colorFondoLigero),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(40.dp).background(Blanco, RoundedCornerShape(8.dp)), contentAlignment = Alignment.Center) {
                        Icon(
                            when {
                                esConstruccion -> Icons.Default.Build
                                esBioproductos -> Icons.Default.Science
                                esGestion -> Icons.Default.Forest
                                else -> Icons.Default.Factory
                            },
                            contentDescription = null,
                            tint = colorPrincipal,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Column(modifier = Modifier.weight(1f).padding(start = 12.dp)) {
                        Text(linea.titulo, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = colorPrincipal)
                        Text("${invDeLinea.size} investigadores · ${projDeLinea.size} proyectos", fontSize = 12.sp, color = GrisOscuro)
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Blanco),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, BordeTarjeta)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Esta línea en CENAMAD", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = TextoOscuro)
                        Text("Explora por tipo", fontSize = 12.sp, color = GrisTexto)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MetricBox(
                            valor = "${invDeLinea.size}",
                            label = "Investigadores",
                            colorFondo = colorFondoLigero,
                            colorTexto = colorPrincipal,
                            onClick = onVerInvestigadores
                        )
                        MetricBox(
                            valor = "${projDeLinea.size}",
                            label = "Proyectos",
                            colorFondo = colorFondoLigero,
                            colorTexto = colorPrincipal,
                            onClick = onVerProyectos
                        )
                        MetricBox(
                            valor = "${pubDeLinea.size}",
                            label = "Publicaciones",
                            colorFondo = colorFondoLigero,
                            colorTexto = colorPrincipal,
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
                Text("${invDeLinea.size} investigadores", color = colorPrincipal, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }

        items(invDeLinea) { inv ->
            val iniciales = inv.nombre.split(" ").let { parts ->
                if (parts.size >= 3) "${parts[1].first()}${parts[2].first()}" else inv.nombre.take(2).uppercase()
            }
            Card(
                modifier = Modifier.fillMaxWidth().clickable { onInvestigadorClick(inv.nombre) },
                colors = CardDefaults.cardColors(containerColor = Blanco),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, BordeTarjeta)
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(44.dp).background(colorFondoLigero, CircleShape), contentAlignment = Alignment.Center) {
                        Text(iniciales, color = colorPrincipal, fontWeight = FontWeight.Bold)
                    }
                    Column(modifier = Modifier.weight(1f).padding(start = 12.dp)) {
                        Text(inv.nombre, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        Text(inv.bio.ifEmpty { inv.especialidad }, fontSize = 12.sp, color = GrisTexto)
                    }
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = GrisClaro)
                }
            }
        }
    }
}

@Composable
fun MetricBox(
    valor: String,
    label: String,
    colorFondo: Color,
    colorTexto: Color,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .width(96.dp)
            .clickable { onClick() },
        color = colorFondo,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = valor,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = colorTexto
            )
            Text(
                text = label,
                fontSize = 11.sp,
                color = GrisOscuro,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}
