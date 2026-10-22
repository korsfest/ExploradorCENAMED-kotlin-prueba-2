package com.example.explorador


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.explorador.data.MockData
import com.example.explorador.model.LineaInvestigacion

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var estaLogueado by remember { mutableStateOf(false) }
                    var lineaSeleccionada by remember { mutableStateOf<LineaInvestigacion?>(null) }

                    // Sistema de navegación básico por estados
                    if (!estaLogueado) {
                        LoginScreen(onLoginSuccess = { estaLogueado = true })
                    } else if (lineaSeleccionada == null) {
                        ExploradorApp(onLineaClick = { linea -> lineaSeleccionada = linea })
                    } else {
                        DetalleLineaScreen(
                            linea = lineaSeleccionada!!,
                            onVolver = { lineaSeleccionada = null }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    var usuario by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var mostrarError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "¡Bienvenido!",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Estás a punto de entrar al explorador de conocimiento de CENAMAD.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = usuario,
            onValueChange = {
                usuario = it
                mostrarError = false
            },
            label = { Text("Usuario (Ej: alumno)") },
            modifier = Modifier.fillMaxWidth(),
            isError = mostrarError
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                mostrarError = false
            },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            isError = mostrarError
        )

        if (mostrarError) {
            Text(
                text = "Por favor, completa todos los campos.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.Start)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (usuario.isNotBlank() && password.isNotBlank()) {
                    onLoginSuccess()
                } else {
                    mostrarError = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Ingresar al Explorador", fontWeight = FontWeight.Bold)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploradorApp(onLineaClick: (LineaInvestigacion) -> Unit) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("CENAMAD Explorador") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            // Banner explicativo para rellenar el espacio en blanco superior
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Líneas de Investigación",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Selecciona una categoría para descubrir a sus investigadores, proyectos activos y publicaciones destacadas.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(MockData.lineas) { linea ->
                    LineaCard(linea = linea, onClick = { onLineaClick(linea) })
                }
            }
        }
    }
}

@Composable
fun LineaCard(linea: LineaInvestigacion, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = linea.titulo,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleLineaScreen(linea: LineaInvestigacion, onVolver: () -> Unit) {
    var tabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Investigadores", "Proyectos", "Publicaciones")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(linea.titulo) },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Text("⬅️")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            TabRow(selectedTabIndex = tabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = tabIndex == index,
                        onClick = { tabIndex = index },
                        text = { Text(title, fontWeight = FontWeight.SemiBold) }
                    )
                }
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                when (tabIndex) {
                    0 -> items(linea.investigadores) { inv ->
                        ItemCard(
                            titulo = inv.nombre,
                            subtitulo = inv.especialidad,
                            etiqueta = "Investigador"
                        )
                    }
                    1 -> items(linea.proyectos) { proy ->
                        ItemCard(
                            titulo = proy.titulo,
                            subtitulo = "Año: ${proy.anio}",
                            etiqueta = proy.estado
                        )
                    }
                    2 -> items(linea.publicaciones) { pub ->
                        ItemCard(
                            titulo = pub.titulo,
                            subtitulo = "",
                            etiqueta = pub.tipo
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ItemCard(titulo: String, subtitulo: String, etiqueta: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = titulo, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            if (subtitulo.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = subtitulo, style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Surface(
                color = MaterialTheme.colorScheme.secondary,
                shape = MaterialTheme.shapes.small
            ) {
                Text(
                    text = etiqueta,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
    }
}