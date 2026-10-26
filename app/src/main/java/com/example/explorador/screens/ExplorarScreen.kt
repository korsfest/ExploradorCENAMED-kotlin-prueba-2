package com.example.explorador.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.explorador.data.MockData
import com.example.explorador.model.LineaInvestigacion

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExplorarScreen(onLineaClick: (LineaInvestigacion) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            TopAppBar(
                title = {
                    Column {
                        Text("CENAMAD Explorador", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text("Explorador de conocimiento", fontSize = 12.sp, color = Color.Gray)
                    }
                },
                actions = { IconButton(onClick = { }) { Icon(Icons.Default.Search, contentDescription = "Buscar") } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF32224A)),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text("CIENCIA, TECNOLOGÍA Y MADERA", color = Color.LightGray, fontSize = 10.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Conocimiento que\nconstruye futuro.", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold, lineHeight = 28.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Conecta líneas, personas y hallazgos de la industria de la madera.", color = Color.LightGray, fontSize = 14.sp)
                }
            }
        }
        item { Text("Líneas de investigación", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(top = 8.dp)) }
        items(MockData.lineas) { linea ->
            Card(
                modifier = Modifier.fillMaxWidth().clickable { onLineaClick(linea) },
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Build, contentDescription = null, modifier = Modifier.size(40.dp).padding(end = 12.dp), tint = Color(0xFF4A3780))
                    Column {
                        Text(linea.titulo, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text("${linea.investigadores.size} investigadores · ${linea.proyectos.size} proyectos", fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }
        }
    }
}