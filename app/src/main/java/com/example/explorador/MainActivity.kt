package com.example.explorador

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.explorador.model.LineaInvestigacion
import com.example.explorador.screens.*

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
                var pestañaActual by remember { mutableStateOf("login") }
                var lineaSeleccionada by remember { mutableStateOf<LineaInvestigacion?>(null) }
                var viendoInvestigadores by remember { mutableStateOf(false) }
                var viendoProyectos by remember { mutableStateOf(false) }
                var viendoPublicaciones by remember { mutableStateOf(false) }
                var investigadorSeleccionado by remember { mutableStateOf<String?>(null) }
                var viendoFichaProyecto by remember { mutableStateOf(false) }
                var proyectoSeleccionado by remember { mutableStateOf("Prototipo de vivienda modular") }
                var viendoFichaPublicacion by remember { mutableStateOf(false) }
                var publicacionSeleccionada by remember { mutableStateOf("Guía de diseño estructural") }
                var userEmail by remember { mutableStateOf("") }

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
                                }
                            }
                        }
                    ) { padding ->
                        Surface(
                            modifier = Modifier.fillMaxSize().padding(padding),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            if (lineaSeleccionada == null) {
                                viendoInvestigadores = false
                                viendoProyectos = false
                                viendoPublicaciones = false
                                viendoFichaProyecto = false
                                viendoFichaPublicacion = false
                                investigadorSeleccionado = null
                                if (pestañaActual == "explorar") {
                                    ExplorarScreen(email = userEmail, onLineaClick = { linea -> lineaSeleccionada = linea })
                                } else {
                                    BuscarScreen(
                                        onInvestigadorClick = { nombre ->
                                            investigadorSeleccionado = nombre
                                            lineaSeleccionada = LineaInvestigacion("0", "Búsqueda", "", emptyList(), emptyList(), emptyList())
                                        },
                                        onProyectoClick = { titulo ->
                                            proyectoSeleccionado = titulo
                                            viendoFichaProyecto = true
                                            lineaSeleccionada = LineaInvestigacion("0", "Búsqueda", "", emptyList(), emptyList(), emptyList())
                                        },
                                        onPublicacionClick = { titulo ->
                                            publicacionSeleccionada = titulo
                                            viendoFichaPublicacion = true
                                            lineaSeleccionada = LineaInvestigacion("0", "Búsqueda", "", emptyList(), emptyList(), emptyList())
                                        }
                                    )
                                }
                            } else {
                                if (viendoFichaPublicacion) {
                                    FichaPublicacionScreen(
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
                                } else if (viendoFichaProyecto) {
                                    FichaProyectoScreen(
                                        nombreProyecto = proyectoSeleccionado,
                                        onVolver = { viendoFichaProyecto = false },
                                        onInvestigadorClick = { nombre -> investigadorSeleccionado = nombre },
                                        onPublicacionClick = { pub ->
                                            publicacionSeleccionada = pub
                                            viendoFichaProyecto = false
                                            viendoFichaPublicacion = true
                                        }
                                    )
                                } else if (investigadorSeleccionado != null) {
                                    FichaInvestigadorScreen(
                                        nombreInvestigador = investigadorSeleccionado!!,
                                        onVolver = { investigadorSeleccionado = null },
                                        onProyectoClick = { proj ->
                                            proyectoSeleccionado = proj
                                            viendoFichaProyecto = true
                                        }
                                    )
                                } else if (viendoInvestigadores) {
                                    InvestigadoresScreen(
                                        onVolver = { viendoInvestigadores = false },
                                        onInvestigadorClick = { nombre -> investigadorSeleccionado = nombre }
                                    )
                                } else if (viendoProyectos) {
                                    ProyectosScreen(
                                        onVolver = { viendoProyectos = false },
                                        onVerProyecto = { proj ->
                                            proyectoSeleccionado = proj
                                            viendoFichaProyecto = true
                                        }
                                    )
                                } else if (viendoPublicaciones) {
                                    PublicacionesScreen(
                                        onVolver = { viendoPublicaciones = false },
                                        onVerPublicacion = { pub ->
                                            publicacionSeleccionada = pub
                                            viendoFichaPublicacion = true
                                        }
                                    )
                                } else {
                                    DetalleLineaScreen(
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