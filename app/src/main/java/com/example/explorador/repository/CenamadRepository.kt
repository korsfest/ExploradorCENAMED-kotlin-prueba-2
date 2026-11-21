package com.example.explorador.repository

import com.example.explorador.model.*

class CenamadRepository(
    private val api: CenamadApiService = RetrofitClient.apiService
) {
    suspend fun obtenerLineas(): Result<List<LineaInvestigacion>> = runCatching {
        api.getLineas()
    }

    suspend fun obtenerInvestigadores(): Result<List<Investigador>> = runCatching {
        api.getInvestigadores()
    }

    suspend fun obtenerInvestigadorPorId(id: Int): Result<Investigador> = runCatching {
        api.getInvestigadorDetalle(id)
    }

    suspend fun obtenerProyectos(): Result<List<Proyecto>> = runCatching {
        api.getProyectos()
    }

    suspend fun agregarProyecto(nuevo: ProyectoRequest): Result<Proyecto> = runCatching {
        api.crearProyecto(nuevo)
    }

    suspend fun borrarProyecto(id: Int): Result<Boolean> = runCatching {
        val response = api.eliminarProyecto(id)
        response.isSuccessful
    }

    suspend fun obtenerPublicaciones(): Result<List<Publicacion>> = runCatching {
        api.getPublicaciones()
    }
}