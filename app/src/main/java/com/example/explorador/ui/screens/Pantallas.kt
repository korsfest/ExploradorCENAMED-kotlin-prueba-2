package com.example.explorador.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.explorador.model.LineaInvestigacion

@Composable
fun LoginScreen(onLoginSuccess: (String) -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("CENAMAD", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2563EB))
        Text("Explorador de conocimiento de la madera", color = Color.Gray)
        Spacer(modifier = Modifier.height(32.dp))
        OutlinedTextField(
            value = email, onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password, onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { onLoginSuccess(email) },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Iniciar Sesión", fontSize = 16.sp)
        }
    }
}

@Composable
fun ExplorarScreen(email: String, onLineaClick: (LineaInvestigacion) -> Unit) {
    val lineas = listOf(
        LineaInvestigacion("1", "Construcción Sustentable en Madera", "Diseño y edificación.", emptyList(), emptyList(), emptyList()),
        LineaInvestigacion("2", "Bioproductos y Biomateriales", "Innovación forestal.", emptyList(), emptyList(), emptyList()),
        LineaInvestigacion("3", "Gestión y Economía Forestal", "Desarrollo local.", emptyList(), emptyList(), emptyList()),
        LineaInvestigacion("4", "Innovación en Procesos Industriales", "Automatización.", emptyList(), emptyList(), emptyList())
    )

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("CENAMAD Explorador", fontWeight = FontWeight.Bold)
        Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE91E63)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Conocimiento que construye futuro.", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }
        Text("Líneas de Investigación", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(lineas) { linea ->
                Card(
                    modifier = Modifier.fillMaxWidth().clickable { onLineaClick(linea) },
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Build, contentDescription = null, tint = Color.Gray)
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(linea.nombre, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun InfoScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Información de Contacto", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Text("Si tienes dudas, búscanos en nuestra sucursal.", color = Color.Gray, modifier = Modifier.padding(vertical = 8.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text("Nuestra Ubicación", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        // Placeholder del mapa
        Box(modifier = Modifier.fillMaxWidth().height(300.dp).background(Color(0xFFE0E0E0), RoundedCornerShape(16.dp)), contentAlignment = Alignment.Center) {
            Icon(Icons.Default.Place, contentDescription = "Mapa", tint = Color.Gray, modifier = Modifier.size(64.dp))
            Text("Mapa interactivo", modifier = Modifier.padding(top = 80.dp), color = Color.DarkGray)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleLineaScreen(linea: LineaInvestigacion, onVolver: () -> Unit, onVerInvestigadores: () -> Unit, onVerProyectos: () -> Unit, onVerPublicaciones: () -> Unit, onInvestigadorClick: (String) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(linea.nombre) },
                navigationIcon = { IconButton(onClick = onVolver) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver") } }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Text("Proyectos y aplicaciones en ${linea.nombre}", fontSize = 16.sp)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FichaPublicacionScreen(nombrePublicacion: String, onVolver: () -> Unit, onInvestigadorClick: (String) -> Unit, onProyectoClick: (String) -> Unit, onInicioClick: () -> Unit) {
    Scaffold(topBar = { TopAppBar(title = { Text("Ficha de publicación") }, navigationIcon = { IconButton(onClick = onVolver) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver") } }) }) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) { Text(nombrePublicacion, fontWeight = FontWeight.Bold, fontSize = 24.sp) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FichaProyectoScreen(nombreProyecto: String, onVolver: () -> Unit, onInvestigadorClick: (String) -> Unit, onPublicacionClick: (String) -> Unit) {
    Scaffold(topBar = { TopAppBar(title = { Text("Ficha de proyecto") }, navigationIcon = { IconButton(onClick = onVolver) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver") } }) }) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) { Text(nombreProyecto, fontWeight = FontWeight.Bold, fontSize = 24.sp) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FichaInvestigadorScreen(nombreInvestigador: String, onVolver: () -> Unit, onProyectoClick: (String) -> Unit) {
    Scaffold(topBar = { TopAppBar(title = { Text("Ficha de investigador") }, navigationIcon = { IconButton(onClick = onVolver) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver") } }) }) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) { Text(nombreInvestigador, fontWeight = FontWeight.Bold, fontSize = 24.sp) }
    }
}

@Composable
fun InvestigadoresScreen(onVolver: () -> Unit, onInvestigadorClick: (String) -> Unit) {}
@Composable
fun ProyectosScreen(onVolver: () -> Unit, onVerProyecto: (String) -> Unit) {}
@Composable
fun PublicacionesScreen(onVolver: () -> Unit, onVerPublicacion: (String) -> Unit) {}