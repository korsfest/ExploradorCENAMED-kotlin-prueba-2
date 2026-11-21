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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Article
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
fun PublicacionesScreen(
    viewModel: ExploradorViewModel,
    onVolver: () -> Unit,
    onVerPublicacion: (String) -> Unit
) {
    val publicaciones by viewModel.publicaciones.collectAsState()

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF8F9FA))) {
        TopAppBar(
            title = { Text("Publicaciones", fontSize = 18.sp, fontWeight = FontWeight.Bold) },
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
            itemsIndexed(publicaciones) { index, pub ->
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
                    modifier = Modifier.fillMaxWidth().clickable { onVerPublicacion(pub.titulo) },
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
                            Icon(Icons.Outlined.Article, contentDescription = null, tint = colorPrincipal)
                        }
                        Column(modifier = Modifier.weight(1f).padding(start = 16.dp)) {
                            Text("${pub.anio} · ${pub.autor}".uppercase(), fontSize = 10.sp, color = colorPrincipal, fontWeight = FontWeight.Bold)
                            Text(pub.titulo, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF1F1A24))
                        }
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.LightGray)
                    }
                }
            }
        }
    }
}