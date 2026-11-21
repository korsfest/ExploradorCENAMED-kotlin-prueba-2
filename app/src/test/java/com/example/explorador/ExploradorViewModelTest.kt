package com.example.explorador

import com.example.explorador.model.*
import com.example.explorador.repository.CenamadApiService
import com.example.explorador.repository.CenamadRepository
import com.example.explorador.repository.ExternalApiService
import com.example.explorador.repository.SessionManager
import com.example.explorador.viewmodel.ExploradorViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class FakeCenamadApiService : CenamadApiService {

    val lineasFake = mutableListOf(
        LineaInvestigacion(1, "Construcción Sustentable en Madera", "CONSTRUCCIÓN SUSTENTABLE", "2 investigadores", "Descripción 1"),
        LineaInvestigacion(2, "Bioproductos y Biomateriales", "BIOMATERIALES", "3 investigadores", "Descripción 2")
    )

    val investigadoresFake = mutableListOf(
        Investigador(1, "Dra. Ana Silva", "Ingeniería Estructural", "Construcción Sustentable en Madera", "ana@cenamad.cl", "Bio")
    )

    val proyectosFake = mutableListOf(
        Proyecto(1, "Prototipo de vivienda modular", "Vivienda en madera", "Objetivo", "2026", "En ejecución", "Construcción Sustentable en Madera", "Dra. Ana Silva")
    )

    val publicacionesFake = mutableListOf(
        Publicacion(1, "Guía de diseño estructural", "Policy Brief", "Contexto", "Resumen", "2025", "Dra. Ana Silva", "Construcción Sustentable en Madera", "Prototipo de vivienda modular")
    )

    override suspend fun getLineas(): List<LineaInvestigacion> = lineasFake.toList()

    override suspend fun getInvestigadores(): List<Investigador> = investigadoresFake.toList()

    override suspend fun getInvestigadorDetalle(id: Int): Investigador =
        investigadoresFake.first { it.id == id }

    override suspend fun getProyectos(): List<Proyecto> = proyectosFake.toList()

    override suspend fun crearProyecto(nuevoProyecto: ProyectoRequest): Proyecto {
        val creado = Proyecto(
            id = proyectosFake.size + 1,
            titulo = nuevoProyecto.titulo,
            subtitulo = nuevoProyecto.subtitulo,
            anio = nuevoProyecto.anio,
            estado = nuevoProyecto.estado,
            investigadorNombre = nuevoProyecto.investigadorNombre
        )
        proyectosFake.add(creado)
        return creado
    }

    override suspend fun actualizarProyecto(id: Int, proyectoActualizado: ProyectoRequest): Proyecto {
        val index = proyectosFake.indexOfFirst { it.id == id }
        val actualizado = Proyecto(
            id = id,
            titulo = proyectoActualizado.titulo,
            subtitulo = proyectoActualizado.subtitulo,
            anio = proyectoActualizado.anio,
            estado = proyectoActualizado.estado,
            investigadorNombre = proyectoActualizado.investigadorNombre
        )
        if (index >= 0) {
            proyectosFake[index] = actualizado
        }
        return actualizado
    }

    override suspend fun eliminarProyecto(id: Int): Response<Unit> {
        proyectosFake.removeIf { it.id == id }
        return Response.success(Unit)
    }

    override suspend fun getPublicaciones(): List<Publicacion> = publicacionesFake.toList()
}

class FakeExternalApiService : ExternalApiService {
    override suspend fun obtenerClimaSede(
        latitude: Double,
        longitude: Double,
        currentWeather: Boolean
    ): WeatherResponse {
        return WeatherResponse(
            currentWeather = CurrentWeather(
                temperature = 21.5,
                windspeed = 12.0,
                weathercode = 1
            )
        )
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class ExploradorViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var fakeApi: FakeCenamadApiService
    private lateinit var fakeExternalApi: FakeExternalApiService
    private lateinit var sessionManager: SessionManager
    private lateinit var repository: CenamadRepository
    private lateinit var viewModel: ExploradorViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        fakeApi = FakeCenamadApiService()
        fakeExternalApi = FakeExternalApiService()
        sessionManager = SessionManager(null)
        repository = CenamadRepository(
            api = fakeApi,
            externalApi = fakeExternalApi,
            sessionManager = sessionManager,
            useCatalogFallback = false
        )
        viewModel = ExploradorViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `al iniciar ViewModel carga correctamente lineas investigadores proyectos publicaciones y clima`() = runTest {
        advanceUntilIdle()

        assertEquals(2, viewModel.lineas.value.size)
        assertEquals("Construcción Sustentable en Madera", viewModel.lineas.value[0].titulo)
        assertEquals(1, viewModel.investigadores.value.size)
        assertEquals(1, viewModel.proyectos.value.size)
        assertEquals(1, viewModel.publicaciones.value.size)
        assertEquals(21.5, viewModel.climaSede.value?.temperature ?: 0.0, 0.01)
        assertNull(viewModel.mensajeError.value)
    }

    @Test
    fun `crearProyecto agrega un nuevo proyecto al StateFlow correctamente`() = runTest {
        advanceUntilIdle()
        val cantidadInicial = viewModel.proyectos.value.size

        viewModel.crearProyecto(
            titulo = "Puente de Madera Laminada",
            subtitulo = "Infraestructura vial sustentable",
            anio = "2026",
            estado = "En ejecución",
            investigador = "Dra. Ana Silva"
        )
        advanceUntilIdle()

        assertEquals(cantidadInicial + 1, viewModel.proyectos.value.size)
        assertEquals("Puente de Madera Laminada", viewModel.proyectos.value.last().titulo)
    }

    @Test
    fun `actualizarProyecto modifica el proyecto existente en el StateFlow`() = runTest {
        advanceUntilIdle()

        viewModel.actualizarProyecto(
            id = 1,
            titulo = "Prototipo de vivienda modular v2",
            subtitulo = "Actualizado con eficiencia térmica",
            anio = "2026",
            estado = "Finalizado",
            investigador = "Dra. Ana Silva"
        )
        advanceUntilIdle()

        assertEquals("Prototipo de vivienda modular v2", viewModel.proyectos.value.first().titulo)
        assertEquals("Finalizado", viewModel.proyectos.value.first().estado)
    }

    @Test
    fun `eliminarProyecto remueve el proyecto del StateFlow por su ID`() = runTest {
        advanceUntilIdle()
        assertEquals(1, viewModel.proyectos.value.size)

        viewModel.eliminarProyecto(1)
        advanceUntilIdle()

        assertTrue(viewModel.proyectos.value.isEmpty())
    }

    @Test
    fun `repository obtenerInvestigadorPorId retorna el investigador esperado`() = runTest {
        val resultado = repository.obtenerInvestigadorPorId(1)

        assertTrue(resultado.isSuccess)
        assertEquals("Dra. Ana Silva", resultado.getOrNull()?.nombre)
    }

    @Test
    fun `flujo completo de registro inicio de sesion con nombre real y cierre de sesion`() {
        assertFalse(viewModel.validarFormulario(true, "", "correo-invalido", "123"))
        assertNotNull(viewModel.nombreError.value)
        assertNotNull(viewModel.emailError.value)
        assertNotNull(viewModel.passwordError.value)

        var registroOk = false
        viewModel.registrarUsuario("Francisco Colín", "pancho@cenamad.cl", "123456") {
            registroOk = true
        }
        assertTrue(registroOk)
        assertFalse(viewModel.esErrorAuth.value)

        viewModel.iniciarSesion("pancho@cenamad.cl", "123456")
        assertTrue(viewModel.sesionIniciada.value)
        assertEquals("Francisco Colín", viewModel.usuarioNombre.value)
        assertEquals("pancho@cenamad.cl", viewModel.usuarioEmail.value)

        viewModel.cerrarSesion()
        assertFalse(viewModel.sesionIniciada.value)
        assertEquals("", viewModel.usuarioNombre.value)
    }
}
