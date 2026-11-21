package com.example.explorador.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.explorador.model.*
import com.example.explorador.repository.CenamadRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ExploradorViewModel(
    private val repository: CenamadRepository = CenamadRepository()
) : ViewModel() {

    private val _lineas = MutableStateFlow<List<LineaInvestigacion>>(emptyList())
    val lineas: StateFlow<List<LineaInvestigacion>> = _lineas.asStateFlow()

    private val _investigadores = MutableStateFlow<List<Investigador>>(emptyList())
    val investigadores: StateFlow<List<Investigador>> = _investigadores.asStateFlow()

    private val _investigadorSeleccionado = MutableStateFlow<Investigador?>(null)
    val investigadorSeleccionado: StateFlow<Investigador?> = _investigadorSeleccionado.asStateFlow()

    private val _proyectos = MutableStateFlow<List<Proyecto>>(emptyList())
    val proyectos: StateFlow<List<Proyecto>> = _proyectos.asStateFlow()

    private val _publicaciones = MutableStateFlow<List<Publicacion>>(emptyList())
    val publicaciones: StateFlow<List<Publicacion>> = _publicaciones.asStateFlow()

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando.asStateFlow()

    private val _mensajeError = MutableStateFlow<String?>(null)
    val mensajeError: StateFlow<String?> = _mensajeError.asStateFlow()

    init {
        cargarDatosIniciales()
    }

    fun cargarDatosIniciales() {
        viewModelScope.launch {
            _cargando.value = true
            _mensajeError.value = null

            repository.obtenerLineas()
                .onSuccess { _lineas.value = it }
                .onFailure { _mensajeError.value = "Error al cargar líneas: ${it.message}" }

            repository.obtenerInvestigadores()
                .onSuccess { _investigadores.value = it }

            repository.obtenerProyectos()
                .onSuccess { _proyectos.value = it }

            repository.obtenerPublicaciones()
                .onSuccess { _publicaciones.value = it }

            _cargando.value = false
        }
    }

    fun cargarDetalleInvestigador(id: Int) {
        viewModelScope.launch {
            _cargando.value = true
            repository.obtenerInvestigadorPorId(id)
                .onSuccess { _investigadorSeleccionado.value = it }
                .onFailure { _mensajeError.value = "No se pudo cargar la ficha" }
            _cargando.value = false
        }
    }

    fun crearProyecto(titulo: String, subtitulo: String, anio: String, estado: String, investigador: String) {
        viewModelScope.launch {
            _cargando.value = true
            val request = ProyectoRequest(titulo, subtitulo, anio, estado, investigador)
            repository.agregarProyecto(request)
                .onSuccess { proyectoCreado ->
                    _proyectos.value = _proyectos.value + proyectoCreado
                }
                .onFailure { _mensajeError.value = "Error al guardar proyecto: ${it.message}" }
            _cargando.value = false
        }
    }

    fun eliminarProyecto(id: Int) {
        viewModelScope.launch {
            repository.borrarProyecto(id)
                .onSuccess { eliminado ->
                    if (eliminado) {
                        _proyectos.value = _proyectos.value.filterNot { it.id == id }
                    }
                }
        }
    }
}