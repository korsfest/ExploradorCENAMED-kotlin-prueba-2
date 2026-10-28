package com.example.explorador.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FichaPublicacionScreen(
    nombrePublicacion: String,
    onVolver: () -> Unit,
    onInvestigadorClick: (String) -> Unit,
    onProyectoClick: (String) -> Unit,
    onInicioClick: () -> Unit
) {
    val esConstruccion = nombrePublicacion.contains("Guía de diseño", ignoreCase = true) || nombrePublicacion.contains("fuego en CLT", ignoreCase = true)
    val esBioproductos = nombrePublicacion.contains("Caracterización", ignoreCase = true) || nombrePublicacion.contains("Nanocompuestos", ignoreCase = true) || nombrePublicacion.contains("Impacto ambiental", ignoreCase = true)
    val esGestion = nombrePublicacion.contains("Tendencias del mercado", ignoreCase = true) || nombrePublicacion.contains("Desarrollo Local", ignoreCase = true)

    val colorPrincipal = when {
        esConstruccion -> Color(0xFFE91E63)
        esBioproductos -> Color(0xFF4CAF50)
        esGestion -> Color(0xFFFF9800)
        else -> Color(0xFF03A9F4)
    }

    val colorFondoLigero = when {
        esConstruccion -> Color(0xFFFCE4EC)
        esBioproductos -> Color(0xFFE8F5E9)
        esGestion -> Color(0xFFFFF3E0)
        else -> Color(0xFFE1F5FE)
    }

    val esGuia = nombrePublicacion.contains("Guía de diseño", ignoreCase = true)
    val esBiopolimeros = nombrePublicacion.contains("Caracterización de biopolímeros", ignoreCase = true)
    val esNanocompuestos = nombrePublicacion.contains("Nanocompuestos de celulosa", ignoreCase = true)
    val esImpactoAmbiental = nombrePublicacion.contains("Impacto ambiental", ignoreCase = true)
    val esTendencias = nombrePublicacion.contains("Tendencias del mercado", ignoreCase = true)
    val esDesarrolloSostenible = nombrePublicacion.contains("Desarrollo Local Sostenible", ignoreCase = true)
    val esAutomatizacion = nombrePublicacion.contains("Automatización en aserraderos", ignoreCase = true)
    val esManufactura = nombrePublicacion.contains("Manufactura y Robótica Avanzada", ignoreCase = true)

    val tipo = if (esTendencias || esGuia || esDesarrolloSostenible || esAutomatizacion || esManufactura) "POLICY BRIEF" else "ARTÍCULO CIENTÍFICO"
    val anio = if (esImpactoAmbiental || nombrePublicacion.contains("fuego en CLT", ignoreCase = true)) "2024" else "2025"

    val titulo = when {
        esGuia -> "Guía de diseño estructural"
        esBiopolimeros -> "Caracterización de biopolímeros"
        esNanocompuestos -> "Nanocompuestos de celulosa"
        esImpactoAmbiental -> "Impacto ambiental del CLT"
        esTendencias -> "Tendencias del mercado maderero"
        esDesarrolloSostenible -> "Desarrollo Local Sostenible"
        esAutomatizacion -> "Automatización en aserraderos"
        esManufactura -> "Manufactura y Robótica Avanzada"
        else -> "Resistencia al fuego en CLT"
    }

    val subtitulo = when {
        esGuia -> "Diseño de sistemas modulares en madera"
        esBiopolimeros -> "Biopolímeros forestales avanzados"
        esNanocompuestos -> "Nanocompuestos estructurales"
        esImpactoAmbiental -> "Evaluación de Ciclo de Vida en CLT"
        esTendencias -> "Mercados de Exportación Maderera"
        esDesarrolloSostenible -> "Desarrollo Local y Territorio"
        esAutomatizacion -> "Robótica en Aserraderos"
        esManufactura -> "Celdas Robotizadas de Producción"
        else -> "Análisis sísmico de uniones"
    }

    val resumen = when {
        esGuia -> "Síntesis de criterios para diseñar módulos habitacionales de madera. Aborda organización estructural, uniones y montaje, y conecta estas decisiones con el prototipo de vivienda modular."
        esBiopolimeros -> "Estudio analítico sobre las propiedades mecánicas y estructurales de nuevos biopolímeros obtenidos a partir de componentes de la biomasa forestal."
        esNanocompuestos -> "Investigación sobre el refuerzo de matrices poliméricas con celulosa nanofibrilada para obtener materiales de alta resistencia."
        esImpactoAmbiental -> "Análisis detallado de la huella de carbono y los beneficios ecológicos del uso de madera contralaminada en proyectos de construcción."
        esTendencias -> "Informe sintético sobre la evolución de la demanda internacional y los principales desafíos comerciales y logísticos para la madera nacional."
        esDesarrolloSostenible -> "Análisis sobre el impacto socioeconómico y las directrices estratégicas para fomentar el bienestar en comunidades forestales locales."
        esAutomatizacion -> "Directrices para integrar celdas robotizadas y control de alta precisión en líneas de aserrío y transformación industrial."
        esManufactura -> "Lineamientos para la optimización de procesos mediante celdas robotizadas colaborativas orientadas a la industria de la madera."
        else -> "Revisión exploratoria del desempeño al fuego de paneles de madera contralaminada y su comportamiento en uniones estructurales."
    }

    val proyectoRelacionado = when {
        esGuia -> "Prototipo de vivienda modular"
        esBiopolimeros -> "Biopolímeros forestales avanzados"
        esNanocompuestos -> "Nanocompuestos estructurales"
        esImpactoAmbiental -> "Evaluación de Ciclo de Vida en CLT"
        esTendencias -> "Mercados de Exportación Maderera"
        esDesarrolloSostenible -> "Desarrollo Local y Territorio"
        esAutomatizacion -> "Robótica en Aserraderos"
        esManufactura -> "Celdas Robotizadas de Producción"
        else -> "Análisis sísmico de uniones"
    }

    val proyectoEstado = if (esImpactoAmbiental || nombrePublicacion.contains("fuego en CLT", ignoreCase = true)) "2024 · Finalizado" else "2025 · En ejecución"

    val autorNombre = when {
        esBiopolimeros -> "Dr. Luis Morales"
        esNanocompuestos -> "Dra. Sofía Valdés"
        esImpactoAmbiental -> "Dr. Mateo Rojas"
        esTendencias -> "Dr. Gabriel Fuentes"
        esDesarrolloSostenible -> "Dra. Marcela Parra"
        esAutomatizacion -> "Dr. Ignacio Becker"
        esManufactura -> "Dra. Valentina Soto"
        else -> "Dra. Ana Silva"
    }
    val autorIniciales = when {
        esBiopolimeros -> "LM"
        esNanocompuestos -> "SV"
        esImpactoAmbiental -> "MR"
        esTendencias -> "GF"
        esDesarrolloSostenible -> "MP"
        esAutomatizacion -> "IB"
        esManufactura -> "VS"
        else -> "AS"
    }
    val autorEsp = when {
        esBiopolimeros -> "Química de Materiales"
        esNanocompuestos -> "Nanotecnología de la Madera"
        esImpactoAmbiental -> "Gestión Ambiental"
        esTendencias -> "Economía Forestal"
        esDesarrolloSostenible -> "Sostenibilidad Territorial"
        esAutomatizacion -> "Automatización Industrial"
        esManufactura -> "Manufactura Avanzada"
        else -> "Ingeniería Estructural"
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            TopAppBar(
                title = { Text("Ficha de publicación", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onVolver) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver") }
                },
                actions = {
                    IconButton(onClick = { }) { Icon(Icons.Default.Search, contentDescription = "Buscar") }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = colorFondoLigero),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text(tipo, color = colorPrincipal, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Text(anio, color = colorPrincipal, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                    }
                    Text(
                        titulo,
                        color = Color(0xFF1F1A24),
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 12.dp),
                        lineHeight = 32.sp
                    )
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 4.dp)) {
                        Icon(Icons.Default.Book, contentDescription = null, tint = colorPrincipal, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(subtitulo, color = Color.DarkGray, fontSize = 14.sp)
                    }
                }
            }
        }

        item {
            Text("Resumen", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF1F1A24))
            Text(resumen, fontSize = 14.sp, color = Color.DarkGray, modifier = Modifier.padding(top = 8.dp), lineHeight = 20.sp)
            Text("Resumen de demostración · no es un documento técnico real.", fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(top = 8.dp))
        }

        item {
            Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
                Text("Autoría", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("1 autor", color = colorPrincipal, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }

        item {
            AutorCardPublicacion(
                iniciales = autorIniciales,
                especialidad = autorEsp,
                nombre = autorNombre,
                colorPrincipal = colorPrincipal,
                colorFondo = colorFondoLigero,
                onClick = { onInvestigadorClick(autorNombre) }
            )
        }

        item {
            Text("Proyecto relacionado", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(top = 8.dp))
            Card(
                modifier = Modifier.fillMaxWidth().clickable { onProyectoClick(proyectoRelacionado) },
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Color(0xFFE5E5E5))
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(40.dp).background(colorFondoLigero, RoundedCornerShape(8.dp)), contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Book, contentDescription = null, tint = colorPrincipal, modifier = Modifier.size(20.dp))
                    }
                    Column(modifier = Modifier.weight(1f).padding(start = 12.dp)) {
                        Text(proyectoEstado, fontSize = 10.sp, color = colorPrincipal, fontWeight = FontWeight.Bold)
                        Text(proyectoRelacionado, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    }
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.LightGray)
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth().clickable { onInicioClick() },
                colors = CardDefaults.cardColors(containerColor = colorFondoLigero),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Home, contentDescription = null, tint = colorPrincipal)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Volver al inicio", color = colorPrincipal, fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = colorPrincipal)
                }
            }
        }

        item {
            Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 32.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.Info, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Muestra académica · datos e indicadores sintéticos", fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
private fun AutorCardPublicacion(iniciales: String, especialidad: String, nombre: String, colorPrincipal: Color, colorFondo: Color, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0xFFE5E5E5))
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(48.dp).background(colorFondo, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(iniciales, color = colorPrincipal, fontWeight = FontWeight.Bold)
            }
            Column(modifier = Modifier.weight(1f).padding(start = 12.dp, end = 8.dp)) {
                Text(especialidad, fontSize = 10.sp, color = colorPrincipal, fontWeight = FontWeight.Bold)
                Text(nombre, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.LightGray)
        }
    }
}