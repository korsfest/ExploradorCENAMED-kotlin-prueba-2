package com.example.explorador

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.explorador.data.MockData
import com.example.explorador.model.LineaInvestigacion

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = Color(0xFF4A3780),
                    background = Color(0xFFF8F9FA)
                )
            ) {
                var pestañaActual by remember { mutableStateOf("explorar") }
                var lineaSeleccionada by remember { mutableStateOf<LineaInvestigacion?>(null) }
                var viendoInvestigadores by remember { mutableStateOf(false) }

                Scaffold(
                    bottomBar = {
                        if (lineaSeleccionada == null) {
                            NavigationBar(containerColor = Color.White) {
                                NavigationBarItem(
                                    icon = { Icon(Icons.Outlined.Explore, contentDescription = "Explorar") },
                                    label = { Text("Explorar") },
                                    selected = pestañaActual == "explorar",
                                    onClick = { pestañaActual = "explorar" }
                                )
                                NavigationBarItem(
                                    icon = { Icon(Icons.Outlined.Search, contentDescription = "Buscar") },
                                    label = { Text("Buscar") },
                                    selected = pestañaActual == "buscar",
                                    onClick = { pestañaActual = "buscar" }
                                )
                            }
                        }
                    }
                ) { padding ->
                    Surface(
                        modifier = Modifier.fillMaxSize().padding(padding),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        if (lineaSeleccionada == null) {
                            viendoInvestigadores = false // Reset al volver a la vista principal
                            if (pestañaActual == "explorar") {
                                ExplorarScreen(onLineaClick = { linea -> lineaSeleccionada = linea })
                            } else {
                                BuscarScreen()
                            }
                        } else {
                            if (viendoInvestigadores) {
                                InvestigadoresScreen(onVolver = { viendoInvestigadores = false })
                            } else {
                                DetalleLineaScreen(
                                    linea = lineaSeleccionada!!,
                                    onVolver = { lineaSeleccionada = null },
                                    onVerInvestigadores = { viendoInvestigadores = true }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

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
                actions = {
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(Icons.Default.Search, contentDescription = "Buscar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }

        // Banner Oscuro
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF32224A)),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text("CIENCIA, TECNOLOGÍA Y MADERA", color = Color.LightGray, fontSize = 10.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Conocimiento que\nconstruye futuro.",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 28.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        "Conecta líneas, personas y hallazgos de la industria de la madera.",
                        color = Color.LightGray,
                        fontSize = 14.sp
                    )
                }
            }
        }

        item {
            Text(
                "Líneas de investigación",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        items(MockData.lineas) { linea ->
            Card(
                modifier = Modifier.fillMaxWidth().clickable { onLineaClick(linea) },
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Build,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp).padding(end = 12.dp),
                        tint = Color(0xFF4A3780)
                    )
                    Column {
                        Text(linea.titulo, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text(
                            "${linea.investigadores.size} investigadores · ${linea.proyectos.size} proyectos",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleLineaScreen(linea: LineaInvestigacion, onVolver: () -> Unit, onVerInvestigadores: () -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            TopAppBar(
                title = { Text("Línea de investigación", fontSize = 16.sp) },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }

        item {
            Text("CONSTRUCCIÓN Y HÁBITAT", color = Color(0xFF4A3780), fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Text(linea.titulo, fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 8.dp))
            Text("Explora soluciones en madera para una edificación eficiente, segura y de menor impacto ambiental.", color = Color.DarkGray)
        }

        item {
            Text("Esta línea en la muestra", fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 16.dp, bottom = 8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                StatCard(linea.investigadores.size.toString(), "Investigadores", Modifier.weight(1f))
                StatCard(linea.proyectos.size.toString(), "Proyectos", Modifier.weight(1f))
                StatCard(linea.publicaciones.size.toString(), "Publicaciones", Modifier.weight(1f))
            }
        }

        item {
            Text("Explora la línea", fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 16.dp))
        }

        // Navegación interna (Listas)
        item {
            Card(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column {
                    ListItem(
                        modifier = Modifier.clickable { onVerInvestigadores() },
                        headlineContent = { Text("Investigadores", fontWeight = FontWeight.Bold) },
                        supportingContent = { Text("Conoce a quienes investigan") },
                        leadingContent = { Icon(Icons.Default.Person, contentDescription = null) },
                        trailingContent = { Text(linea.investigadores.size.toString()) }
                    )
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                    ListItem(
                        headlineContent = { Text("Proyectos", fontWeight = FontWeight.Bold) },
                        supportingContent = { Text("En ejecución y finalizados") },
                        leadingContent = { Icon(Icons.Default.Build, contentDescription = null) },
                        trailingContent = { Text(linea.proyectos.size.toString()) }
                    )
                }
            }
        }
    }
}

@Composable
fun StatCard(numero: String, etiqueta: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3EDF7)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(numero, fontSize = 24.sp, color = Color(0xFF4A3780))
            Text(etiqueta, fontSize = 10.sp, color = Color.Gray)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvestigadoresScreen(onVolver: () -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // App Bar
        item {
            TopAppBar(
                title = { Text("Investigadores de la línea", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Buscar */ }) {
                        Icon(Icons.Default.Search, contentDescription = "Buscar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }

        // Etiqueta de la línea (Breadcrumb)
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF3EDF7)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp).fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Build, contentDescription = null, tint = Color(0xFF4A3780), modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Construcción Sustentable en Madera", color = Color(0xFF4A3780), fontSize = 14.sp, modifier = Modifier.weight(1f))
                    Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = Color(0xFF4A3780))
                }
            }
        }

        // Títulos de la sección
        item {
            Text("Personas que conectan ideas", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF32224A))
            Text(
                "2 investigadores en Construcción Sustentable en Madera.",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        // Filtros (Chips)
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(
                    selected = true,
                    onClick = {},
                    label = { Text("Todos · 2") },
                    leadingIcon = { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp)) }
                )
                FilterChip(
                    selected = false,
                    onClick = {},
                    label = { Text("Especialidad") },
                    trailingIcon = { Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, modifier = Modifier.size(16.dp)) }
                )
            }
        }

        // Encabezado de la lista
        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Equipo de investigación", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("A–Z", color = Color(0xFF4A3780), fontSize = 14.sp, fontWeight = FontWeight.Medium)
            }
        }

        // Tarjeta Investigador 1
        item {
            InvestigadorCard(
                iniciales = "AS",
                especialidad = "Ingeniería Estructural",
                nombre = "Dra. Ana Silva",
                descripcion = "Diseño y evaluación de sistemas modulares de madera para vivienda.",
                estadisticas = "1 proyecto · 1 publicación relacionada"
            )
        }

        // Tarjeta Investigador 2
        item {
            InvestigadorCard(
                iniciales = "CR",
                especialidad = "Arquitectura Sustentable",
                nombre = "Dr. Carlos Ruiz",
                descripcion = "Desempeño sísmico y seguridad de soluciones constructivas en madera.",
                estadisticas = "1 proyecto · 2 publicaciones relacionadas"
            )
        }

        // Tarjeta informativa inferior
        item {
            Card(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF3EDF7)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("De las personas a sus hallazgos", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF32224A))
                    Text(
                        "Abre una ficha para conocer su enfoque y seguir los vínculos a proyectos y publicaciones de esta muestra.",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }

        // Texto legal / Footer
        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 32.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Outlined.Info, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Muestra académica · datos e indicadores sintéticos", fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
fun InvestigadorCard(iniciales: String, especialidad: String, nombre: String, descripcion: String, estadisticas: String) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0xFFE5E5E5))
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.Top) {
            Box(
                modifier = Modifier.size(48.dp).background(Color(0xFFEADDFF), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(iniciales, color = Color(0xFF4A3780), fontWeight = FontWeight.Bold)
            }
            Column(modifier = Modifier.weight(1f).padding(start = 12.dp, end = 8.dp)) {
                Text(especialidad, fontSize = 10.sp, color = Color(0xFF4A3780), fontWeight = FontWeight.Bold)
                Text(nombre, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(vertical = 2.dp))
                Text(descripcion, fontSize = 14.sp, color = Color.DarkGray, lineHeight = 18.sp)
                Text(estadisticas, fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(top = 8.dp))
            }
            Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = Color.LightGray, modifier = Modifier.align(Alignment.CenterVertically))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuscarScreen() {
    var searchQuery by remember { mutableStateOf("modular") }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Cabecera superior
        item {
            TopAppBar(
                title = { Text("Buscar conocimiento", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { /* TODO: Acción volver */ }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Search, contentDescription = "Buscar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }

        // Títulos de la sección
        item {
            Text("Encuentra conexiones", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF32224A))
            Text(
                "Busca en títulos, temas y contenido relacionado.",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        // Barra de búsqueda
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Close, contentDescription = "Borrar")
                        }
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF4A3780),
                    unfocusedBorderColor = Color.LightGray,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )
        }

        // Sección de Filtros
        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Filtrar resultados", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text("Limpiar filtros", color = Color(0xFF4A3780), fontSize = 12.sp, fontWeight = FontWeight.Medium)
            }

            // Dropdown de línea de investigación
            Card(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF3EDF7)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = Color(0xFF4A3780), modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Construcción Sustentable en Madera", color = Color(0xFF4A3780), fontSize = 14.sp)
                    }
                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, tint = Color(0xFF4A3780))
                }
            }

            // Chips de categorías
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(selected = true, onClick = {}, label = { Text("Todos · 3") })
                FilterChip(selected = false, onClick = {}, label = { Text("Investigadores · 1") })
                FilterChip(selected = false, onClick = {}, label = { Text("Proyectos · 1") })
                FilterChip(selected = false, onClick = {}, label = { Text("Publicaciones · 1") })
            }
        }

        // Cabecera de resultados
        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("3 resultados", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("Por relevancia", color = Color.Gray, fontSize = 12.sp)
            }
        }

        // Tarjeta de Resultado 1: Proyecto
        item {
            SearchResultCard(
                tipo = "PROYECTO · 2025 · En ejecución",
                titulo = "Prototipo de vivienda modular",
                descripcion = "Coincidencia en el título",
                icon = { Icon(Icons.Default.Build, contentDescription = null, tint = Color(0xFF4A3780)) }
            )
        }

        // Tarjeta de Resultado 2: Investigadora
        item {
            SearchResultCard(
                tipo = "INVESTIGADORA · Ingeniería Estructural",
                titulo = "Dra. Ana Silva",
                descripcion = "Responsable del proyecto de vivienda modular",
                icon = {
                    Box(
                        modifier = Modifier.size(40.dp).background(Color(0xFFEADDFF), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("AS", color = Color(0xFF4A3780), fontWeight = FontWeight.Bold)
                    }
                }
            )
        }

        // Tarjeta de Resultado 3: Publicación
        item {
            SearchResultCard(
                tipo = "PUBLICACIÓN · Policy Brief",
                titulo = "Guía de diseño estructural",
                descripcion = "Relacionada con el prototipo modular",
                icon = { Icon(Icons.Outlined.Article, contentDescription = null, tint = Color(0xFF4A3780)) }
            )
        }

        item {
            Spacer(modifier = Modifier.height(30.dp)) // Espacio para el footer
        }
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
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.size(48.dp), contentAlignment = Alignment.Center) {
                icon()
            }
            Column(modifier = Modifier.weight(1f).padding(start = 12.dp, end = 8.dp)) {
                Text(tipo, fontSize = 10.sp, color = Color(0xFF4A3780), fontWeight = FontWeight.Bold)
                Text(titulo, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(vertical = 2.dp))
                Text(descripcion, fontSize = 12.sp, color = Color.Gray)
            }
            Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = Color.LightGray)
        }
    }
}
// UI: Integración completa de InvestigadoresScreen con navegación funcional.