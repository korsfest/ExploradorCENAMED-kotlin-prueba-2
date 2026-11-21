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
import androidx.compose.material.icons.filled.Search
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
fun InvestigadoresScreen(
    viewModel: ExploradorViewModel,
    onVolver: () -> Unit,
    onInvestigadorClick: (String) -> Unit
) {
    val investigadores by viewModel.investigadores.collectAsState()

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF8F9FA))) {
        TopAppBar(
            title = { Text("Investigadores", fontSize = 18.sp, fontWeight = FontWeight.Bold) },
            navigationIcon = {
                IconButton(onClick = onVolver) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver") }
            },
            actions = {
                IconButton(onClick = { }) { Icon(Icons.Default.Search, contentDescription = "Buscar") }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
        )

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(investigadores) { inv ->
                val esConstruccion = inv.lineaNombre.contains("Construcción", ignoreCase = true)
                val esBioproductos = inv.lineaNombre.contains("Bioproductos", ignoreCase = true)
                val esGestion = inv.lineaNombre.contains("Gestión", ignoreCase = true)

                val colorPrincipal = when {
                    esConstruccion -> Color(0xFFE91E63)
                    esBioproductos -> Color(0xFF4CAF50)
                    esGestion -> Color(0xFFFF9800)
                    else -> Color(0xFF03A9F4)
                }

                val colorFondoLigero = when {
                    esConstruccion -> Color(0xFFFCE4EC)
                    esBioproductos -> Color(0xFFE8F5E9)
                    esGestion -> Color(0xFFFFF3E0)
                    else -> Color(0xFFE1F5FE)
                }

                val iniciales = inv.nombre.split(" ").let { parts ->
                    when {
                        parts.size >= 3 -> "${parts[1].first()}${parts[2].first()}"
                        parts.size == 2 -> "${parts[0].first()}${parts[1].first()}"
                        else -> inv.nombre.take(2).uppercase()
                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth().clickable { onInvestigadorClick(inv.nombre) },
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, Color(0xFFE5E5E5))
                ) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier.size(48.dp).background(colorFondoLigero, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(iniciales, color = colorPrincipal, fontWeight = FontWeight.Bold)
                        }
                        Column(modifier = Modifier.weight(1f).padding(start = 16.dp)) {
                            Text(inv.especialidad, fontSize = 10.sp, color = colorPrincipal, fontWeight = FontWeight.Bold)
                            Text(inv.nombre, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF1F1A24))
                        }
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.LightGray)
                    }
                }
            }
        }
    }
}