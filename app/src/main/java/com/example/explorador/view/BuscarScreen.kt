package com.example.explorador.view

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
import com.example.explorador.viewmodel.ExploradorViewModel

data class SearchItem(
    val tipo: String,
    val titulo: String,
    val subtitulo: String,
    val linea: String,
    val tag: String = ""
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuscarScreen(
    viewModel: ExploradorViewModel,
    onInvestigadorClick: (String) -> Unit,
    onProyectoClick: (String) -> Unit,
    onPublicacionClick: (String) -> Unit
) {
    var searchText by remember { mutableStateOf("") }
    var selectedLinea by remember { mutableStateOf("Todas las líneas") }
    var selectedFilter by remember { mutableStateOf("Todos") }
    var expandedDropdown by remember { mutableStateOf(false) }

    val lineasApi by viewModel.lineas.collectAsState()
    val investigadores by viewModel.investigadores.collectAsState()
    val proyectos by viewModel.proyectos.collectAsState()
    val publicaciones by viewModel.publicaciones.collectAsState()

    val lineas = listOf("Todas las líneas") + lineasApi.map { it.titulo }
    val filters = listOf("Todos", "Investigadores", "Proyectos", "Publicaciones")

    val allItems = remember(investigadores, proyectos, publicaciones) {
        val listaInv = investigadores.map { inv ->
            val iniciales = inv.nombre.split(" ").let { parts ->
                if (parts.size >= 3) "${parts[1].first()}${parts[2].first()}" else inv.nombre.take(2).uppercase()
            }
            SearchItem("INVESTIGADOR", inv.nombre, inv.especialidad, inv.lineaNombre, iniciales)
        }
        val listaProj = proyectos.map { proj ->
            SearchItem("PROYECTO", proj.titulo, "${proj.anio} · ${proj.estado}", proj.investigadorNombre)
        }
        val listaPub = publicaciones.map { pub ->
            SearchItem("PUBLICACIÓN", pub.titulo, "${pub.autor} · ${pub.anio}", pub.autor)
        }
        listaInv + listaProj + listaPub
    }

    val filteredItems = allItems.filter { item ->
        val matchesSearch = searchText.isBlank() ||
                item.titulo.contains(searchText, ignoreCase = true) ||
                item.subtitulo.contains(searchText, ignoreCase = true)
        val matchesLinea = selectedLinea == "Todas las líneas" || item.linea.equals(selectedLinea, ignoreCase = true)
        val matchesType = when (selectedFilter) {
            "Investigadores" -> item.tipo == "INVESTIGADOR"
            "Proyectos" -> item.tipo == "PROYECTO"
            "Publicaciones" -> item.tipo == "PUBLICACIÓN"
            else -> true
        }
        matchesSearch && matchesLinea && matchesType
    }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF8F9FA))) {
        Column(modifier = Modifier.background(Color.White).padding(16.dp)) {
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
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
                expanded = expandedDropdown,
                onExpandedChange = { expandedDropdown = !expandedDropdown }
            ) {
                OutlinedTextField(
                    value = selectedLinea,
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
                    expanded = expandedDropdown,
                    onDismissRequest = { expandedDropdown = false }
                ) {
                    lineas.forEach { linea ->
                        DropdownMenuItem(
                            text = { Text(linea) },
                            onClick = {
                                selectedLinea = linea
                                expandedDropdown = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                filters.forEach { filter ->
                    val isSelected = selectedFilter == filter
                    Surface(
                        modifier = Modifier.clickable { selectedFilter = filter },
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

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("${filteredItems.size} resultados", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("En vivo (API)", fontSize = 14.sp, color = Color.Gray)
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(filteredItems) { item ->
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

    val colorFondo = when (item.linea) {
        "Construcción Sustentable en Madera" -> Color(0xFFFCE4EC)
        "Bioproductos y Biomateriales" -> Color(0xFFE8F5E9)
        "Gestión y Economía Forestal" -> Color(0xFFFFF3E0)
        "Innovación en Procesos Industriales" -> Color(0xFFE1F5FE)
        else -> Color(0xFFE0E7FF)
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
                modifier = Modifier.size(48.dp).background(colorFondo, if (item.tipo == "INVESTIGADOR") CircleShape else RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                when (item.tipo) {
                    "INVESTIGADOR" -> Text(item.tag, color = colorPrincipal, fontWeight = FontWeight.Bold)
                    "PROYECTO" -> Icon(Icons.Default.Build, contentDescription = null, tint = colorPrincipal)
                    "PUBLICACIÓN" -> Icon(Icons.Outlined.Article, contentDescription = null, tint = colorPrincipal)
                }
            }
            Column(modifier = Modifier.weight(1f).padding(start = 16.dp)) {
                Text(item.tipo, fontSize = 10.sp, color = colorPrincipal, fontWeight = FontWeight.Bold)
                Text(item.titulo, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(vertical = 2.dp))
                Text(item.subtitulo, fontSize = 13.sp, color = Color.Gray)
            }
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.LightGray)
        }
    }
}