package com.example.explorador.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Build
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.explorador.viewmodel.BuscarViewModel
import com.example.explorador.model.SearchItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuscarScreen(
    viewModel: BuscarViewModel = viewModel(),
    onInvestigadorClick: (String) -> Unit,
    onProyectoClick: (String) -> Unit,
    onPublicacionClick: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF8F9FA))) {
        Column(modifier = Modifier.background(Color.White).padding(16.dp)) {
            OutlinedTextField(
                value = viewModel.searchText,
                onValueChange = { viewModel.onSearchTextChanged(it) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Buscar proyectos, personas...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF2563EB),
                    unfocusedBorderColor = Color(0xFFE5E5E5)
                )
            )
            Spacer(modifier = Modifier.height(12.dp))
            ExposedDropdownMenuBox(
                expanded = viewModel.expandedDropdown,
                onExpandedChange = { viewModel.toggleDropdown() }
            ) {
                OutlinedTextField(
                    value = viewModel.selectedLinea,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { Icon(Icons.Default.ArrowDropDown, contentDescription = null) },
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF2563EB),
                        unfocusedBorderColor = Color(0xFFE5E5E5)
                    )
                )
                ExposedDropdownMenu(
                    expanded = viewModel.expandedDropdown,
                    onDismissRequest = { viewModel.dismissDropdown() }
                ) {
                    viewModel.lineas.forEach { linea ->
                        DropdownMenuItem(
                            text = { Text(linea) },
                            onClick = { viewModel.onLineaSelected(linea) }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                viewModel.filters.forEach { filter ->
                    val isSelected = viewModel.selectedFilter == filter
                    Surface(
                        modifier = Modifier.clickable { viewModel.onFilterSelected(filter) },
                        color = if (isSelected) Color(0xFFE0E7FF) else Color.Transparent,
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, if (isSelected) Color.Transparent else Color.LightGray)
                    ) {
                        Text(
                            text = filter,
                            color = if (isSelected) Color(0xFF2563EB) else Color.Gray,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 13.sp,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                }
            }
        }
        LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("${viewModel.filteredItems.size} resultados", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("Por relevancia", fontSize = 14.sp, color = Color.Gray)
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            items(viewModel.filteredItems) { item ->
                SearchItemCard(
                    item = item,
                    onClick = {
                        when (item.tipo) {
                            "INVESTIGADOR" -> onInvestigadorClick(item.titulo)
                            "PROYECTO" -> onProyectoClick(item.titulo)
                            "PUBLICACIÓN" -> onPublicacionClick(item.titulo)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun SearchItemCard(item: SearchItem, onClick: () -> Unit) {
    val colorPrincipal = when (item.linea) {
        "Construcción Sustentable en Madera" -> Color(0xFFE91E63)
        "Bioproductos y Biomateriales" -> Color(0xFF4CAF50)
        "Gestión y Economía Forestal" -> Color(0xFFFF9800)
        "Innovación en Procesos Industriales" -> Color(0xFF03A9F4)
        else -> Color(0xFF2563EB)
    }

    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0xFFE5E5E5))
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(48.dp).background(colorPrincipal.copy(alpha = 0.1f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                if (item.tipo == "INVESTIGADOR" && item.tag != null) {
                    Text(item.tag, color = colorPrincipal, fontWeight = FontWeight.Bold)
                } else {
                    Icon(
                        when (item.tipo) {
                            "PROYECTO" -> Icons.Default.Build
                            "PUBLICACIÓN" -> Icons.Outlined.Article
                            else -> Icons.Default.Search
                        },
                        contentDescription = null,
                        tint = colorPrincipal
                    )
                }
            }
            Column(modifier = Modifier.weight(1f).padding(start = 12.dp, end = 8.dp)) {
                Text(item.tipo, fontSize = 10.sp, color = colorPrincipal, fontWeight = FontWeight.Bold)
                Text(item.titulo, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(vertical = 2.dp))
                Text(item.subtitulo, fontSize = 13.sp, color = Color.DarkGray)
            }
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.LightGray)
        }
    }
}