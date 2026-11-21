package com.example.explorador.repository

import com.example.explorador.model.*

class CenamadRepository(
    private val api: CenamadApiService = RetrofitClient.apiService,
    private val externalApi: ExternalApiService = RetrofitClient.externalApiService,
    private val sessionManager: SessionManager = SessionManager(),
    private val useCatalogFallback: Boolean = true
) {

    private val lineasBase = listOf(
        LineaInvestigacion(
            id = 1,
            titulo = "Construcción Sustentable en Madera",
            tag = "CONSTRUCCIÓN SUSTENTABLE",
            detalle = "2 investigadores · 2 proyectos",
            descripcion = "Diseño estructural, prefabricación modular y desempeño sísmico y térmico de edificaciones en madera."
        ),
        LineaInvestigacion(
            id = 2,
            titulo = "Bioproductos y Biomateriales",
            tag = "BIOMATERIALES Y CIRCULARIDAD",
            detalle = "3 investigadores · 1 proyectos",
            descripcion = "Desarrollo de biopolímeros, nanocelulosa y materiales avanzados a partir de recursos forestales renovables."
        ),
        LineaInvestigacion(
            id = 3,
            titulo = "Gestión y Economía Forestal",
            tag = "ECONOMÍA Y TERRITORIO",
            detalle = "2 investigadores · 1 proyectos",
            descripcion = "Análisis de cadenas de valor, sostenibilidad territorial y modelos de negocio para la bioeconomía forestal."
        ),
        LineaInvestigacion(
            id = 4,
            titulo = "Innovación en Procesos Industriales",
            tag = "TECNOLOGÍA Y MANUFACTURA",
            detalle = "2 investigadores · 2 proyectos",
            descripcion = "Automatización, robótica y digitalización en la transformación industrial de la madera."
        )
    )

    private val investigadoresBase = listOf(
        Investigador(1, "Dra. Ana Silva", "Ingeniería Estructural", "Construcción Sustentable en Madera", "ana.silva@cenamad.cl", "Diseño y evaluación de sistemas modulares de madera para vivienda."),
        Investigador(2, "Dr. Carlos Ruiz", "Arquitectura Sustentable", "Construcción Sustentable en Madera", "carlos.ruiz@cenamad.cl", "Investigación en envolventes térmicas y comportamiento sísmico de estructuras de madera."),
        Investigador(3, "Dr. Luis Morales", "Química de Materiales", "Bioproductos y Biomateriales", "luis.morales@cenamad.cl", "Síntesis y caracterización de biopolímeros y adhesivos naturales derivados de lignina."),
        Investigador(4, "Dra. Sofía Valdés", "Nanotecnología de la Madera", "Bioproductos y Biomateriales", "sofia.valdes@cenamad.cl", "Desarrollo de nanocompuestos estructurales basados en celulosa microfibrilada."),
        Investigador(5, "Dr. Mateo Rojas", "Gestión Ambiental", "Bioproductos y Biomateriales", "mateo.rojas@cenamad.cl", "Evaluación de ciclo de vida y huella de carbono en productos de madera contralaminada."),
        Investigador(6, "Dr. Gabriel Fuentes", "Economía Forestal", "Gestión y Economía Forestal", "gabriel.fuentes@cenamad.cl", "Estudio de competitividad, mercados internacionales y cadenas productivas madereras."),
        Investigador(7, "Dra. Marcela Parra", "Sostenibilidad Territorial", "Gestión y Economía Forestal", "marcela.parra@cenamad.cl", "Planificación territorial sostenible y desarrollo de comunidades vinculadas al bosque."),
        Investigador(8, "Dr. Ignacio Becker", "Automatización Industrial", "Innovación en Procesos Industriales", "ignacio.becker@cenamad.cl", "Especialista en robótica y digitalización en la línea de producción de madera."),
        Investigador(9, "Dra. Valentina Soto", "Manufactura Avanzada", "Innovación en Procesos Industriales", "valentina.soto@cenamad.cl", "Manufactura industrial con control automatizado y optimización de corte.")
    )

    private val proyectosBase = mutableListOf(
        Proyecto(1, "Prototipo de vivienda modular", "Una propuesta para acelerar la construcción de viviendas mediante módulos de madera.", "Evaluar una solución modular que integre diseño estructural, facilidad de montaje y uso eficiente de madera. La guía relaciona criterios técnicos para su diseño.", "2025", "En ejecución", "Construcción Sustentable en Madera", "Dra. Ana Silva"),
        Proyecto(2, "Análisis sísmico de uniones", "Estudio experimental de conexiones estructurales en edificaciones de mediana altura.", "Caracterizar la disipación de energía y rigidez de uniones metálicas y de madera en marcos estructurales.", "2024", "Finalizado", "Construcción Sustentable en Madera", "Dr. Carlos Ruiz"),
        Proyecto(3, "Biopolímeros forestales avanzados", "Desarrollo de resinas biodegradables a partir de subproductos de la industria forestal.", "Sustituir adhesivos sintéticos derivados del petróleo por formulaciones bio-basadas de alta resistencia.", "2025", "En ejecución", "Bioproductos y Biomateriales", "Dr. Luis Morales"),
        Proyecto(4, "Nanocompuestos estructurales", "Refuerzo de matrices poliméricas mediante nanocelulosa cristalina.", "Incrementar el desempeño mecánico y la durabilidad de tableros estructurales ligeros.", "2025", "En ejecución", "Bioproductos y Biomateriales", "Dra. Sofía Valdés"),
        Proyecto(5, "Evaluación de Ciclo de Vida en CLT", "Cuantificación de captura de carbono en construcción masiva con madera.", "Establecer indicadores ambientales verificables para paneles contralaminados producidos localmente.", "2024", "Finalizado", "Bioproductos y Biomateriales", "Dr. Mateo Rojas"),
        Proyecto(6, "Mercados de Exportación Maderera", "Análisis estratégico de demanda global para productos de madera con valor agregado.", "Identificar oportunidades comerciales y estándares de certificación requeridos en mercados internacionales.", "2025", "En ejecución", "Gestión y Economía Forestal", "Dr. Gabriel Fuentes"),
        Proyecto(7, "Desarrollo Local y Territorio", "Modelos asociativos para pequeñas y medianas empresas forestales.", "Fortalecer el encadenamiento productivo local y la gestión forestal sustentable.", "2025", "En ejecución", "Gestión y Economía Forestal", "Dra. Marcela Parra"),
        Proyecto(8, "Robótica en Aserraderos", "Integración de visión artificial y brazos robóticos en líneas de clasificación.", "Aumentar la precisión de corte y reducir pérdidas de materia prima en aserraderos.", "2025", "En ejecución", "Innovación en Procesos Industriales", "Dr. Ignacio Becker"),
        Proyecto(9, "Celdas Robotizadas de Producción", "Automatización flexible para el ensamblaje de paneles prefabricados.", "Estandarizar los tiempos de fabricación y mejorar la seguridad operacional en planta.", "2025", "En ejecución", "Innovación en Procesos Industriales", "Dra. Valentina Soto")
    )

    private val publicacionesBase = listOf(
        Publicacion(1, "Guía de diseño estructural", "Policy Brief", "Diseño de sistemas modulares en madera", "Criterios prácticos para diseñar módulos funcionales y montables, y cómo estas decisiones se articulan con el prototipo de vivienda modular.", "2025", "Dra. Ana Silva", "Construcción Sustentable en Madera", "Prototipo de vivienda modular"),
        Publicacion(2, "Resistencia al fuego en CLT", "Artículo científico", "Comportamiento térmico de paneles estructurales", "Evaluación experimental de tasas de carbonización y estabilidad estructural en muros de madera contralaminada.", "2024", "Dr. Carlos Ruiz", "Construcción Sustentable en Madera", "Análisis sísmico de uniones"),
        Publicacion(3, "Caracterización de biopolímeros", "Artículo científico", "Materiales renovables de origen forestal", "Propiedades fisicoquímicas y desempeño adhesivo de resinas formuladas a partir de lignina purificada.", "2025", "Dr. Luis Morales", "Bioproductos y Biomateriales", "Biopolímeros forestales avanzados"),
        Publicacion(4, "Nanocompuestos de celulosa", "Artículo científico", "Nanotecnología aplicada a biomateriales", "Síntesis y ensayos mecánicos de compuestos reforzados con nanofibras de celulosa.", "2025", "Dra. Sofía Valdés", "Bioproductos y Biomateriales", "Nanocompuestos estructurales"),
        Publicacion(5, "Impacto ambiental del CLT", "Artículo científico", "Análisis de ciclo de vida en construcción", "Balance de emisiones y almacenamiento biogénico de carbono en edificaciones estructuradas en madera.", "2024", "Dr. Mateo Rojas", "Bioproductos y Biomateriales", "Evaluación de Ciclo de Vida en CLT"),
        Publicacion(6, "Tendencias del mercado maderero", "Policy Brief", "Comercio exterior y bioeconomía forestal", "Proyecciones de demanda y recomendaciones de política sectorial para manufacturas de madera.", "2025", "Dr. Gabriel Fuentes", "Gestión y Economía Forestal", "Mercados de Exportación Maderera"),
        Publicacion(7, "Desarrollo Local Sostenible", "Policy Brief", "Gobernanza y desarrollo territorial", "Estrategias de articulación territorial y agregación de valor en regiones forestales.", "2025", "Dra. Marcela Parra", "Gestión y Economía Forestal", "Desarrollo Local y Territorio"),
        Publicacion(8, "Automatización en aserraderos", "Policy Brief", "Transformación digital en industria maderera", "Guía de adopción tecnológica para procesos de escaneo, clasificación y corte automatizado.", "2025", "Dr. Ignacio Becker", "Innovación en Procesos Industriales", "Robótica en Aserraderos"),
        Publicacion(9, "Manufactura y Robótica Avanzada", "Policy Brief", "Industrialización y prefabricación en madera", "Implementación de celdas robotizadas para la manufactura precisa de componentes constructivos.", "2025", "Dra. Valentina Soto", "Innovación en Procesos Industriales", "Celdas Robotizadas de Producción")
    )

    suspend fun obtenerLineas(): Result<List<LineaInvestigacion>> {
        if (!useCatalogFallback) {
            return runCatching { api.getLineas() }
        }
        val remotas = runCatching { api.getLineas() }.getOrDefault(emptyList())
        if (remotas.isEmpty()) return Result.success(lineasBase)
        val enriquecidas = lineasBase.map { base ->
            val match = remotas.find { it.titulo.equals(base.titulo, ignoreCase = true) || it.id == base.id }
            if (match != null) {
                base.copy(
                    id = if (match.id != 0) match.id else base.id,
                    titulo = match.titulo.ifBlank { base.titulo },
                    tag = match.tag.ifBlank { base.tag },
                    detalle = match.detalle.ifBlank { base.detalle },
                    descripcion = match.descripcion.ifBlank { base.descripcion }
                )
            } else base
        }
        return Result.success(enriquecidas)
    }

    suspend fun obtenerInvestigadores(): Result<List<Investigador>> {
        if (!useCatalogFallback) {
            return runCatching { api.getInvestigadores() }
        }
        val remotos = runCatching { api.getInvestigadores() }.getOrDefault(emptyList())
        if (remotos.isEmpty()) return Result.success(investigadoresBase)
        val enriquecidos = investigadoresBase.map { base ->
            val match = remotos.find { it.nombre.equals(base.nombre, ignoreCase = true) || it.id == base.id }
            if (match != null) {
                base.copy(
                    id = if (match.id != 0) match.id else base.id,
                    nombre = match.nombre.ifBlank { base.nombre },
                    especialidad = match.especialidad.ifBlank { base.especialidad },
                    lineaNombre = match.lineaNombre.ifBlank { base.lineaNombre },
                    correo = match.correo.ifBlank { base.correo },
                    bio = match.bio.ifBlank { base.bio }
                )
            } else base
        }
        val extras = remotos.filter { r -> enriquecidos.none { it.nombre.equals(r.nombre, ignoreCase = true) } }
        return Result.success(enriquecidos + extras)
    }

    suspend fun obtenerInvestigadorPorId(id: Int): Result<Investigador> {
        if (!useCatalogFallback) {
            return runCatching { api.getInvestigadorDetalle(id) }
        }
        val remoto = runCatching { api.getInvestigadorDetalle(id) }.getOrNull()
        val base = investigadoresBase.find { it.id == id }
        return if (remoto != null) {
            Result.success(
                remoto.copy(
                    lineaNombre = remoto.lineaNombre.ifBlank { base?.lineaNombre ?: "" },
                    bio = remoto.bio.ifBlank { base?.bio ?: "" }
                )
            )
        } else if (base != null) {
            Result.success(base)
        } else {
            Result.failure(NoSuchElementException("Investigador no encontrado"))
        }
    }

    suspend fun obtenerProyectos(): Result<List<Proyecto>> {
        if (!useCatalogFallback) {
            return runCatching { api.getProyectos() }
        }
        val remotos = runCatching { api.getProyectos() }.getOrDefault(emptyList())
        if (remotos.isEmpty()) return Result.success(proyectosBase.toList())
        val enriquecidos = proyectosBase.map { base ->
            val match = remotos.find { it.titulo.equals(base.titulo, ignoreCase = true) || it.id == base.id }
            if (match != null) {
                val inv = investigadoresBase.find { it.nombre.equals(match.investigadorNombre, ignoreCase = true) }
                base.copy(
                    id = if (match.id != 0) match.id else base.id,
                    titulo = match.titulo.ifBlank { base.titulo },
                    subtitulo = match.subtitulo.ifBlank { base.subtitulo },
                    objetivo = match.objetivo.ifBlank { base.objetivo },
                    anio = match.anio.ifBlank { base.anio },
                    estado = match.estado.ifBlank { base.estado },
                    lineaNombre = match.lineaNombre.ifBlank { inv?.lineaNombre ?: base.lineaNombre },
                    investigadorNombre = match.investigadorNombre.ifBlank { base.investigadorNombre }
                )
            } else base
        }
        val extras = remotos.filter { r -> enriquecidos.none { it.titulo.equals(r.titulo, ignoreCase = true) } }.map { r ->
            val inv = investigadoresBase.find { it.nombre.equals(r.investigadorNombre, ignoreCase = true) }
            r.copy(lineaNombre = r.lineaNombre.ifBlank { inv?.lineaNombre ?: "Construcción Sustentable en Madera" })
        }
        return Result.success(enriquecidos + extras)
    }

    suspend fun agregarProyecto(nuevo: ProyectoRequest): Result<Proyecto> {
        val inv = investigadoresBase.find { it.nombre.equals(nuevo.investigadorNombre, ignoreCase = true) }
        val lineaAsignada = inv?.lineaNombre ?: "Construcción Sustentable en Madera"

        if (!useCatalogFallback) {
            return runCatching { api.crearProyecto(nuevo) }
        }

        val creadoRemoto = runCatching { api.crearProyecto(nuevo) }.getOrNull()
        val nuevoId = creadoRemoto?.id?.takeIf { it != 0 } ?: ((proyectosBase.maxOfOrNull { it.id } ?: 0) + 1)
        val proyectoFinal = Proyecto(
            id = nuevoId,
            titulo = creadoRemoto?.titulo?.ifBlank { nuevo.titulo } ?: nuevo.titulo,
            subtitulo = creadoRemoto?.subtitulo?.ifBlank { nuevo.subtitulo } ?: nuevo.subtitulo.ifBlank { "Proyecto de investigación aplicada en CENAMAD." },
            objetivo = nuevo.subtitulo.ifBlank { "Desarrollo tecnológico y sostenibilidad forestal." },
            anio = creadoRemoto?.anio?.ifBlank { nuevo.anio } ?: nuevo.anio,
            estado = creadoRemoto?.estado?.ifBlank { nuevo.estado } ?: nuevo.estado,
            lineaNombre = lineaAsignada,
            investigadorNombre = creadoRemoto?.investigadorNombre?.ifBlank { nuevo.investigadorNombre } ?: nuevo.investigadorNombre
        )
        proyectosBase.add(proyectoFinal)
        return Result.success(proyectoFinal)
    }

    suspend fun editarProyecto(id: Int, actualizado: ProyectoRequest): Result<Proyecto> {
        val inv = investigadoresBase.find { it.nombre.equals(actualizado.investigadorNombre, ignoreCase = true) }
        val lineaAsignada = inv?.lineaNombre ?: "Construcción Sustentable en Madera"

        if (!useCatalogFallback) {
            return runCatching { api.actualizarProyecto(id, actualizado) }
        }

        val editadoRemoto = runCatching { api.actualizarProyecto(id, actualizado) }.getOrNull()
        val proyectoFinal = Proyecto(
            id = id,
            titulo = editadoRemoto?.titulo?.ifBlank { actualizado.titulo } ?: actualizado.titulo,
            subtitulo = editadoRemoto?.subtitulo?.ifBlank { actualizado.subtitulo } ?: actualizado.subtitulo,
            objetivo = actualizado.subtitulo.ifBlank { "Desarrollo tecnológico y sostenibilidad forestal." },
            anio = editadoRemoto?.anio?.ifBlank { actualizado.anio } ?: actualizado.anio,
            estado = editadoRemoto?.estado?.ifBlank { actualizado.estado } ?: actualizado.estado,
            lineaNombre = lineaAsignada,
            investigadorNombre = editadoRemoto?.investigadorNombre?.ifBlank { actualizado.investigadorNombre } ?: actualizado.investigadorNombre
        )
        val idx = proyectosBase.indexOfFirst { it.id == id }
        if (idx >= 0) {
            proyectosBase[idx] = proyectoFinal
        }
        return Result.success(proyectoFinal)
    }

    suspend fun borrarProyecto(id: Int): Result<Boolean> {
        if (!useCatalogFallback) {
            return runCatching {
                val response = api.eliminarProyecto(id)
                response.isSuccessful
            }
        }
        runCatching { api.eliminarProyecto(id) }
        proyectosBase.removeAll { it.id == id }
        return Result.success(true)
    }

    suspend fun obtenerPublicaciones(): Result<List<Publicacion>> {
        if (!useCatalogFallback) {
            return runCatching { api.getPublicaciones() }
        }
        val remotas = runCatching { api.getPublicaciones() }.getOrDefault(emptyList())
        if (remotas.isEmpty()) return Result.success(publicacionesBase)
        val enriquecidas = publicacionesBase.map { base ->
            val match = remotas.find { it.titulo.equals(base.titulo, ignoreCase = true) || it.id == base.id }
            if (match != null) {
                base.copy(
                    id = if (match.id != 0) match.id else base.id,
                    titulo = match.titulo.ifBlank { base.titulo },
                    tipo = match.tipo.ifBlank { base.tipo },
                    contexto = match.contexto.ifBlank { base.contexto },
                    resumen = match.resumen.ifBlank { base.resumen },
                    anio = match.anio.ifBlank { base.anio },
                    autor = match.autor.ifBlank { base.autor },
                    lineaNombre = match.lineaNombre.ifBlank { base.lineaNombre },
                    proyectoTitulo = match.proyectoTitulo.ifBlank { base.proyectoTitulo }
                )
            } else base
        }
        val extras = remotas.filter { r -> enriquecidas.none { it.titulo.equals(r.titulo, ignoreCase = true) } }
        return Result.success(enriquecidas + extras)
    }

    suspend fun obtenerClimaSede(): Result<CurrentWeather> = runCatching {
        externalApi.obtenerClimaSede().currentWeather
            ?: throw IllegalStateException("Sin datos meteorológicos")
    }

    fun registrarUsuario(nombre: String, email: String, password: String): Boolean {
        return sessionManager.registrarUsuario(nombre, email, password)
    }

    fun validarCredenciales(email: String, password: String): Boolean {
        return sessionManager.validarCredenciales(email, password)
    }

    fun guardarSesionActiva(email: String) {
        sessionManager.guardarSesionActiva(email)
    }

    fun obtenerSesionActiva(): String? {
        return sessionManager.obtenerSesionActiva()
    }

    fun obtenerNombreUsuario(email: String): String {
        return sessionManager.obtenerNombreUsuario(email)
    }

    fun cerrarSesion() {
        sessionManager.cerrarSesion()
    }
}
