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

    private val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

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

    private val _climaSede = MutableStateFlow<CurrentWeather?>(null)
    val climaSede: StateFlow<CurrentWeather?> = _climaSede.asStateFlow()

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando.asStateFlow()

    private val _mensajeError = MutableStateFlow<String?>(null)
    val mensajeError: StateFlow<String?> = _mensajeError.asStateFlow()

    private val _usuarioEmail = MutableStateFlow("")
    val usuarioEmail: StateFlow<String> = _usuarioEmail.asStateFlow()

    private val _usuarioNombre = MutableStateFlow("")
    val usuarioNombre: StateFlow<String> = _usuarioNombre.asStateFlow()

    private val _sesionIniciada = MutableStateFlow(false)
    val sesionIniciada: StateFlow<Boolean> = _sesionIniciada.asStateFlow()

    private val _nombreError = MutableStateFlow<String?>(null)
    val nombreError: StateFlow<String?> = _nombreError.asStateFlow()

    private val _emailError = MutableStateFlow<String?>(null)
    val emailError: StateFlow<String?> = _emailError.asStateFlow()

    private val _passwordError = MutableStateFlow<String?>(null)
    val passwordError: StateFlow<String?> = _passwordError.asStateFlow()

    private val _mensajeAuth = MutableStateFlow<String?>(null)
    val mensajeAuth: StateFlow<String?> = _mensajeAuth.asStateFlow()

    private val _esErrorAuth = MutableStateFlow(false)
    val esErrorAuth: StateFlow<Boolean> = _esErrorAuth.asStateFlow()

    init {
        val sesionActiva = repository.obtenerSesionActiva()
        if (sesionActiva != null) {
            _usuarioEmail.value = sesionActiva
            _usuarioNombre.value = repository.obtenerNombreUsuario(sesionActiva)
            _sesionIniciada.value = true
        }
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

            repository.obtenerClimaSede()
                .onSuccess { _climaSede.value = it }

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

    fun actualizarProyecto(id: Int, titulo: String, subtitulo: String, anio: String, estado: String, investigador: String) {
        viewModelScope.launch {
            _cargando.value = true
            val request = ProyectoRequest(titulo, subtitulo, anio, estado, investigador)
            repository.editarProyecto(id, request)
                .onSuccess { proyectoEditado ->
                    _proyectos.value = _proyectos.value.map { if (it.id == id) proyectoEditado else it }
                }
                .onFailure { _mensajeError.value = "Error al actualizar proyecto: ${it.message}" }
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

    fun validarFormulario(esRegistro: Boolean, nombre: String, email: String, password: String): Boolean {
        var esValido = true

        if (esRegistro && nombre.isBlank()) {
            _nombreError.value = "El nombre es obligatorio"
            esValido = false
        } else {
            _nombreError.value = null
        }

        if (email.isBlank()) {
            _emailError.value = "El correo es obligatorio"
            esValido = false
        } else if (!emailRegex.matches(email.trim())) {
            _emailError.value = "Formato de correo inválido"
            esValido = false
        } else {
            _emailError.value = null
        }

        if (password.isBlank()) {
            _passwordError.value = "La contraseña es obligatoria"
            esValido = false
        } else if (password.length < 6) {
            _passwordError.value = "Mínimo 6 caracteres"
            esValido = false
        } else {
            _passwordError.value = null
        }

        return esValido
    }

    fun registrarUsuario(nombre: String, email: String, password: String, onRegistroExitoso: () -> Unit = {}) {
        if (!validarFormulario(true, nombre, email, password)) return

        val exito = repository.registrarUsuario(nombre, email, password)
        if (exito) {
            _esErrorAuth.value = false
            _mensajeAuth.value = "Registro guardado. Ya puedes iniciar sesión."
            onRegistroExitoso()
        } else {
            _esErrorAuth.value = true
            _mensajeAuth.value = "Este correo ya está registrado"
        }
    }

    fun iniciarSesion(email: String, password: String) {
        if (!validarFormulario(false, "", email, password)) return

        if (repository.validarCredenciales(email, password)) {
            val correoLimpio = email.trim()
            repository.guardarSesionActiva(correoLimpio)
            _usuarioEmail.value = correoLimpio
            _usuarioNombre.value = repository.obtenerNombreUsuario(correoLimpio)
            _mensajeAuth.value = null
            _sesionIniciada.value = true
        } else {
            _esErrorAuth.value = true
            _mensajeAuth.value = "Credenciales incorrectas o usuario no registrado"
        }
    }

    fun limpiarMensajesAuth() {
        _mensajeAuth.value = null
        _nombreError.value = null
        _emailError.value = null
        _passwordError.value = null
    }

    fun cerrarSesion() {
        repository.cerrarSesion()
        _usuarioEmail.value = ""
        _usuarioNombre.value = ""
        _sesionIniciada.value = false
        limpiarMensajesAuth()
    }
}
