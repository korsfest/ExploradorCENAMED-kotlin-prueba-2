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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.explorador.ui.screens.*
import com.example.explorador.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme(primary = Color(0xFF2563EB), background = Color(0xFFF8F9FA))
            ) {
                val viewModel: MainViewModel = viewModel()

                if (viewModel.pestañaActual == "login") {
                    LoginScreen(
                        onLoginSuccess = { emailIngresado -> viewModel.onLoginSuccess(emailIngresado) }
                    )
                } else {
                    Scaffold(
                        bottomBar = {
                            if (viewModel.lineaSeleccionada == null && !viewModel.viendoFichaProyecto && !viewModel.viendoFichaPublicacion && viewModel.investigadorSeleccionado == null) {
                                NavigationBar(containerColor = Color.White) {
                                    NavigationBarItem(
                                        icon = { Icon(Icons.Outlined.Explore, contentDescription = "Explorar") },
                                        label = { Text("Explorar") },
                                        selected = viewModel.pestañaActual == "explorar",
                                        onClick = { viewModel.cambiarPestaña("explorar") }
                                    )
                                    NavigationBarItem(
                                        icon = { Icon(Icons.Outlined.Search, contentDescription = "Buscar") },
                                        label = { Text("Buscar") },
                                        selected = viewModel.pestañaActual == "buscar",
                                        onClick = { viewModel.cambiarPestaña("buscar") }
                                    )
                                    NavigationBarItem(
                                        icon = { Icon(Icons.Outlined.Map, contentDescription = "Info") },
                                        label = { Text("Info") },
                                        selected = viewModel.pestañaActual == "info",
                                        onClick = { viewModel.cambiarPestaña("info") }
                                    )
                                }
                            }
                        }
                    ) { padding ->
                        Surface(modifier = Modifier.fillMaxSize().padding(padding), color = MaterialTheme.colorScheme.background) {
                            if (viewModel.lineaSeleccionada == null) {
                                if (viewModel.pestañaActual == "explorar") {
                                    ExplorarScreen(
                                        email = viewModel.userEmail,
                                        onLineaClick = { linea -> viewModel.seleccionarLinea(linea) }
                                    )
                                } else if (viewModel.pestañaActual == "info") {
                                    InfoScreen()
                                } else {
                                    BuscarScreen(
                                        onInvestigadorClick = { nombre -> viewModel.seleccionarInvestigadorEnBusqueda(nombre) },
                                        onProyectoClick = { titulo -> viewModel.seleccionarProyectoEnBusqueda(titulo) },
                                        onPublicacionClick = { titulo -> viewModel.seleccionarPublicacionEnBusqueda(titulo) }
                                    )
                                }
                            } else {
                                if (viewModel.viendoFichaPublicacion) {
                                    FichaPublicacionScreen(
                                        nombrePublicacion = viewModel.publicacionSeleccionada,
                                        onVolver = { viewModel.volverDeFichaPublicacion() },
                                        onInvestigadorClick = { nombre -> viewModel.seleccionarInvestigador(nombre) },
                                        onProyectoClick = { proj -> viewModel.navegarDePublicacionAProyecto(proj) },
                                        onInicioClick = { viewModel.irAInicio() }
                                    )
                                } else if (viewModel.viendoFichaProyecto) {
                                    FichaProyectoScreen(
                                        nombreProyecto = viewModel.proyectoSeleccionado,
                                        onVolver = { viewModel.volverDeFichaProyecto() },
                                        onInvestigadorClick = { nombre -> viewModel.seleccionarInvestigador(nombre) },
                                        onPublicacionClick = { pub -> viewModel.navegarDeProyectoAPublicacion(pub) }
                                    )
                                } else if (viewModel.investigadorSeleccionado != null) {
                                    FichaInvestigadorScreen(
                                        nombreInvestigador = viewModel.investigadorSeleccionado!!,
                                        onVolver = { viewModel.seleccionarInvestigador(null) },
                                        onProyectoClick = { proj -> viewModel.seleccionarProyecto(proj) }
                                    )
                                } else if (viewModel.viendoInvestigadores) {
                                    InvestigadoresScreen(
                                        onVolver = { viewModel.actualizarViendoInvestigadores(false) },
                                        onInvestigadorClick = { nombre -> viewModel.seleccionarInvestigador(nombre) }
                                    )
                                } else if (viewModel.viendoProyectos) {
                                    ProyectosScreen(
                                        onVolver = { viewModel.actualizarViendoProyectos(false) },
                                        onVerProyecto = { proj -> viewModel.seleccionarProyecto(proj) }
                                    )
                                } else if (viewModel.viendoPublicaciones) {
                                    PublicacionesScreen(
                                        onVolver = { viewModel.actualizarViendoPublicaciones(false) },
                                        onVerPublicacion = { pub -> viewModel.seleccionarPublicacion(pub) }
                                    )
                                } else {
                                    DetalleLineaScreen(
                                        linea = viewModel.lineaSeleccionada!!,
                                        onVolver = { viewModel.seleccionarLinea(null) },
                                        onVerInvestigadores = { viewModel.actualizarViendoInvestigadores(true) },
                                        onVerProyectos = { viewModel.actualizarViendoProyectos(true) },
                                        onVerPublicaciones = { viewModel.actualizarViendoPublicaciones(true) },
                                        onInvestigadorClick = { nombre -> viewModel.seleccionarInvestigador(nombre) }
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