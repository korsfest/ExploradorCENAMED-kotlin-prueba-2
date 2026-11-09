package com.example.explorador.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.explorador.model.LineaInvestigacion

class MainViewModel : ViewModel() {
    var pestañaActual by mutableStateOf("login")
        private set

    var lineaSeleccionada by mutableStateOf<LineaInvestigacion?>(null)
        private set

    var viendoInvestigadores by mutableStateOf(false)
        private set

    var viendoProyectos by mutableStateOf(false)
        private set

    var viendoPublicaciones by mutableStateOf(false)
        private set

    var investigadorSeleccionado by mutableStateOf<String?>(null)
        private set

    var viendoFichaProyecto by mutableStateOf(false)
        private set

    var proyectoSeleccionado by mutableStateOf("Prototipo de vivienda modular")
        private set

    var viendoFichaPublicacion by mutableStateOf(false)
        private set

    var publicacionSeleccionada by mutableStateOf("Guía de diseño estructural")
        private set

    var userEmail by mutableStateOf("")
        private set

    fun onLoginSuccess(email: String) {
        userEmail = email
        pestañaActual = "explorar"
    }

    fun cambiarPestaña(pestaña: String) {
        pestañaActual = pestaña
    }

    fun seleccionarLinea(linea: LineaInvestigacion?) {
        lineaSeleccionada = linea
        if (linea == null) limpiarNavegacionInterna()
    }

    fun seleccionarInvestigadorEnBusqueda(nombre: String) {
        investigadorSeleccionado = nombre
        lineaSeleccionada = LineaInvestigacion("0", "Búsqueda", "", emptyList(), emptyList(), emptyList())
    }

    fun seleccionarProyectoEnBusqueda(titulo: String) {
        proyectoSeleccionado = titulo
        viendoFichaProyecto = true
        lineaSeleccionada = LineaInvestigacion("0", "Búsqueda", "", emptyList(), emptyList(), emptyList())
    }

    fun seleccionarPublicacionEnBusqueda(titulo: String) {
        publicacionSeleccionada = titulo
        viendoFichaPublicacion = true
        lineaSeleccionada = LineaInvestigacion("0", "Búsqueda", "", emptyList(), emptyList(), emptyList())
    }

    fun seleccionarInvestigador(nombre: String?) {
        investigadorSeleccionado = nombre
    }

    fun seleccionarProyecto(titulo: String) {
        proyectoSeleccionado = titulo
        viendoFichaProyecto = true
    }

    fun seleccionarPublicacion(titulo: String) {
        publicacionSeleccionada = titulo
        viendoFichaPublicacion = true
    }

    fun navegarDePublicacionAProyecto(proj: String) {
        proyectoSeleccionado = proj
        viendoFichaPublicacion = false
        viendoFichaProyecto = true
    }

    fun navegarDeProyectoAPublicacion(pub: String) {
        publicacionSeleccionada = pub
        viendoFichaProyecto = false
        viendoFichaPublicacion = true
    }

    fun volverDeFichaPublicacion() { viendoFichaPublicacion = false }
    fun volverDeFichaProyecto() { viendoFichaProyecto = false }

    fun irAInicio() {
        lineaSeleccionada = null
        limpiarNavegacionInterna()
    }

    // AQUI ESTÁ EL ARREGLO: Cambiamos "set" por "actualizar"
    fun actualizarViendoInvestigadores(estado: Boolean) { viendoInvestigadores = estado }
    fun actualizarViendoProyectos(estado: Boolean) { viendoProyectos = estado }
    fun actualizarViendoPublicaciones(estado: Boolean) { viendoPublicaciones = estado }

    private fun limpiarNavegacionInterna() {
        viendoInvestigadores = false
        viendoProyectos = false
        viendoPublicaciones = false
        viendoFichaProyecto = false
        viendoFichaPublicacion = false
        investigadorSeleccionado = null
    }
}