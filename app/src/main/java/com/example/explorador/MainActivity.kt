package com.example.explorador

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.explorador.model.LineaInvestigacion
import com.example.explorador.repository.SessionManager
import com.example.explorador.view.*
import com.example.explorador.viewmodel.ExploradorViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = Color(0xFF2563EB),
                    background = Color(0xFFF8F9FA)
                )
            ) {
                val context = LocalContext.current
                val sessionManager = remember { SessionManager(context) }
                val sesionGuardada = remember { sessionManager.obtenerSesionActiva() }

                // Instancia única del ViewModel compartido para toda la app (MVVM)
                val exploradorViewModel: ExploradorViewModel = viewModel()

                var pestañaActual by remember { mutableStateOf(if (sesionGuardada != null) "explorar" else "login") }
                var lineaSeleccionada by remember { mutableStateOf<LineaInvestigacion?>(null) }
                var viendoInvestigadores by remember { mutableStateOf(false) }
                var viendoProyectos by remember { mutableStateOf(false) }
                var viendoPublicaciones by remember { mutableStateOf(false) }
                var investigadorSeleccionado by remember { mutableStateOf<String?>(null) }
                var viendoFichaProyecto by remember { mutableStateOf(false) }
                var proyectoSeleccionado by remember { mutableStateOf("") }
                var viendoFichaPublicacion by remember { mutableStateOf(false) }
                var publicacionSeleccionada by remember { mutableStateOf("") }
                var userEmail by remember { mutableStateOf(sesionGuardada ?: "") }

                if (pestañaActual == "login") {
                    LoginScreen(
                        onLoginSuccess = { emailIngresado ->
                            userEmail = emailIngresado
                            pestañaActual = "explorar"
                        }
                    )
                } else {
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
                                    NavigationBarItem(
                                        icon = { Icon(Icons.Outlined.Map, contentDescription = "Info") },
                                        label = { Text("Info") },
                                        selected = pestañaActual == "info",
                                        onClick = { pestañaActual = "info" }
                                    )
                                }
                            }
                        }
                    ) { padding ->
                        Surface(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(padding),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            if (lineaSeleccionada == null) {
                                viendoInvestigadores = false
                                viendoProyectos = false
                                viendoPublicaciones = false
                                viendoFichaProyecto = false
                                viendoFichaPublicacion = false
                                investigadorSeleccionado = null

                                when (pestañaActual) {
                                    "explorar" -> {
                                        ExplorarScreen(
                                            viewModel = exploradorViewModel,
                                            email = userEmail,
                                            onLineaClick = { linea -> lineaSeleccionada = linea }
                                        )
                                    }
                                    "info" -> {
                                        InfoScreen(
                                            userEmail = userEmail,
                                            onCerrarSesion = {
                                                sessionManager.cerrarSesion()
                                                userEmail = ""
                                                pestañaActual = "login"
                                            }
                                        )
                                    }
                                    else -> {
                                        BuscarScreen(
                                            viewModel = exploradorViewModel,
                                            onInvestigadorClick = { nombre ->
                                                investigadorSeleccionado = nombre
                                                lineaSeleccionada = LineaInvestigacion(id = 0, titulo = "Búsqueda")
                                            },
                                            onProyectoClick = { titulo ->
                                                proyectoSeleccionado = titulo
                                                viendoFichaProyecto = true
                                                lineaSeleccionada = LineaInvestigacion(id = 0, titulo = "Búsqueda")
                                            },
                                            onPublicacionClick = { titulo ->
                                                publicacionSeleccionada = titulo
                                                viendoFichaPublicacion = true
                                                lineaSeleccionada = LineaInvestigacion(id = 0, titulo = "Búsqueda")
                                            }
                                        )
                                    }
                                }
                            } else {
                                when {
                                    viendoFichaPublicacion -> {
                                        FichaPublicacionScreen(
                                            viewModel = exploradorViewModel,
                                            nombrePublicacion = publicacionSeleccionada,
                                            onVolver = { viendoFichaPublicacion = false },
                                            onInvestigadorClick = { nombre -> investigadorSeleccionado = nombre },
                                            onProyectoClick = { proj ->
                                                proyectoSeleccionado = proj
                                                viendoFichaPublicacion = false
                                                viendoFichaProyecto = true
                                            },
                                            onInicioClick = {
                                                lineaSeleccionada = null
                                                viendoFichaPublicacion = false
                                                viendoFichaProyecto = false
                                                investigadorSeleccionado = null
                                                viendoInvestigadores = false
                                                viendoProyectos = false
                                                viendoPublicaciones = false
                                            }
                                        )
                                    }
                                    viendoFichaProyecto -> {
                                        FichaProyectoScreen(
                                            viewModel = exploradorViewModel,
                                            nombreProyecto = proyectoSeleccionado,
                                            onVolver = { viendoFichaProyecto = false },
                                            onInvestigadorClick = { nombre -> investigadorSeleccionado = nombre },
                                            onPublicacionClick = { pub ->
                                                publicacionSeleccionada = pub
                                                viendoFichaProyecto = false
                                                viendoFichaPublicacion = true
                                            }
                                        )
                                    }
                                    investigadorSeleccionado != null -> {
                                        FichaInvestigadorScreen(
                                            viewModel = exploradorViewModel,
                                            nombreInvestigador = investigadorSeleccionado!!,
                                            onVolver = { investigadorSeleccionado = null },
                                            onProyectoClick = { proj ->
                                                proyectoSeleccionado = proj
                                                viendoFichaProyecto = true
                                            }
                                        )
                                    }
                                    viendoInvestigadores -> {
                                        InvestigadoresScreen(
                                            viewModel = exploradorViewModel,
                                            onVolver = { viendoInvestigadores = false },
                                            onInvestigadorClick = { nombre -> investigadorSeleccionado = nombre }
                                        )
                                    }
                                    viendoProyectos -> {
                                        ProyectosScreen(
                                            viewModel = exploradorViewModel,
                                            onVolver = { viendoProyectos = false },
                                            onVerProyecto = { proj ->
                                                proyectoSeleccionado = proj
                                                viendoFichaProyecto = true
                                            }
                                        )
                                    }
                                    viendoPublicaciones -> {
                                        PublicacionesScreen(
                                            viewModel = exploradorViewModel,
                                            onVolver = { viendoPublicaciones = false },
                                            onVerPublicacion = { pub ->
                                                publicacionSeleccionada = pub
                                                viendoFichaPublicacion = true
                                            }
                                        )
                                    }
                                    else -> {
                                        DetalleLineaScreen(
                                            viewModel = exploradorViewModel,
                                            linea = lineaSeleccionada!!,
                                            onVolver = { lineaSeleccionada = null },
                                            onVerInvestigadores = { viendoInvestigadores = true },
                                            onVerProyectos = { viendoProyectos = true },
                                            onVerPublicaciones = { viendoPublicaciones = true },
                                            onInvestigadorClick = { nombre -> investigadorSeleccionado = nombre }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}