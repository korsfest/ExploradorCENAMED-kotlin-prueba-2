package com.example.explorador

import com.example.explorador.model.*
import com.example.explorador.repository.CenamadApiService
import com.example.explorador.repository.CenamadRepository
import com.example.explorador.viewmodel.ExploradorViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Response

// Simulador controlado de la API de Xano para pruebas unitarias deterministas
class FakeCenamadApiService : CenamadApiService {

    val lineasFake = mutableListOf(
        LineaInvestigacion(1, "Construcción Sustentable en Madera", "2 investigadores", "Descripción 1"),
        LineaInvestigacion(2, "Bioproductos y Biomateriales", "3 investigadores", "Descripción 2")
    )

    val investigadoresFake = mutableListOf(
        Investigador(1, "Dra. Ana Silva", "Ingeniería Estructural", "Construcción Sustentable en Madera", "ana@cenamad.cl")
    )

    val proyectosFake = mutableListOf(
        Proyecto(1, "Prototipo de vivienda modular", "Vivienda en madera", "2026", "En ejecución", "Dra. Ana Silva")
    )

    val publicacionesFake = mutableListOf(
        Publicacion(1, "Guía de diseño estructural", "Dra. Ana Silva", "2025")
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

    override suspend fun eliminarProyecto(id: Int): Response<Unit> {
        proyectosFake.removeIf { it.id == id }
        return Response.success(Unit)
    }

    override suspend fun getPublicaciones(): List<Publicacion> = publicacionesFake.toList()
}

@OptIn(ExperimentalCoroutinesApi::class)
class ExploradorViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var fakeApi: FakeCenamadApiService
    private lateinit var repository: CenamadRepository
    private lateinit var viewModel: ExploradorViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        fakeApi = FakeCenamadApiService()
        repository = CenamadRepository(fakeApi)
        viewModel = ExploradorViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `al iniciar ViewModel carga correctamente lineas investigadores proyectos y publicaciones`() = runTest {
        advanceUntilIdle()

        assertEquals(2, viewModel.lineas.value.size)
        assertEquals("Construcción Sustentable en Madera", viewModel.lineas.value[0].titulo)
        assertEquals(1, viewModel.investigadores.value.size)
        assertEquals(1, viewModel.proyectos.value.size)
        assertEquals(1, viewModel.publicaciones.value.size)
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
}