package com.example.explorador.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuscarScreen() {
    var searchQuery by remember { mutableStateOf("modular") }

    LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            TopAppBar(
                title = { Text("Buscar conocimiento", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = { IconButton(onClick = { }) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver") } },
                actions = { IconButton(onClick = { }) { Icon(Icons.Default.Search, contentDescription = "Buscar") } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
        item {
            Text("Encuentra conexiones", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF32224A))
            Text("Busca en títulos, temas y contenido relacionado.", fontSize = 14.sp, color = Color.Gray, modifier = Modifier.padding(top = 4.dp))
        }
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = { if (searchQuery.isNotEmpty()) { IconButton(onClick = { searchQuery = "" }) { Icon(Icons.Default.Close, contentDescription = "Borrar") } } },
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF4A3780), unfocusedBorderColor = Color.LightGray, focusedContainerColor = Color.White, unfocusedContainerColor = Color.White)
            )
        }
        item {
            Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Filtrar resultados", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text("Limpiar filtros", color = Color(0xFF4A3780), fontSize = 12.sp, fontWeight = FontWeight.Medium)
            }
            Card(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFF3EDF7)), shape = RoundedCornerShape(12.dp)) {
                Row(modifier = Modifier.padding(12.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = Color(0xFF4A3780), modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Construcción Sustentable en Madera", color = Color(0xFF4A3780), fontSize = 14.sp)
                    }
                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, tint = Color(0xFF4A3780))
                }
            }
            Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp).horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(selected = true, onClick = {}, label = { Text("Todos · 3") })
                FilterChip(selected = false, onClick = {}, label = { Text("Investigadores · 1") })
                FilterChip(selected = false, onClick = {}, label = { Text("Proyectos · 1") })
                FilterChip(selected = false, onClick = {}, label = { Text("Publicaciones · 1") })
            }
        }
        item {
            Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("3 resultados", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("Por relevancia", color = Color.Gray, fontSize = 12.sp)
            }
        }
        item { SearchResultCard("PROYECTO · 2025 · En ejecución", "Prototipo de vivienda modular", "Coincidencia en el título") { Icon(Icons.Default.Build, contentDescription = null, tint = Color(0xFF4A3780)) } }
        item {
            SearchResultCard("INVESTIGADORA · Ingeniería Estructural", "Dra. Ana Silva", "Responsable del proyecto de vivienda modular") {
                Box(modifier = Modifier.size(40.dp).background(Color(0xFFEADDFF), CircleShape), contentAlignment = Alignment.Center) { Text("AS", color = Color(0xFF4A3780), fontWeight = FontWeight.Bold) }
            }
        }
        item { SearchResultCard("PUBLICACIÓN · Policy Brief", "Guía de diseño estructural", "Relacionada con el prototipo modular") { Icon(Icons.Outlined.Article, contentDescription = null, tint = Color(0xFF4A3780)) } }
        item { Spacer(modifier = Modifier.height(30.dp)) }
    }
}

@Composable
fun SearchResultCard(tipo: String, titulo: String, descripcion: String, icon: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0xFFE5E5E5))
    ) {
        Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(48.dp), contentAlignment = Alignment.Center) { icon() }
            Column(modifier = Modifier.weight(1f).padding(start = 12.dp, end = 8.dp)) {
                Text(tipo, fontSize = 10.sp, color = Color(0xFF4A3780), fontWeight = FontWeight.Bold)
                Text(titulo, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(vertical = 2.dp))
                Text(descripcion, fontSize = 12.sp, color = Color.Gray)
            }
            Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = Color.LightGray)
        }
    }
}