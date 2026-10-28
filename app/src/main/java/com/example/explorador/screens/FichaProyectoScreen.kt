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
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.TrackChanges
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
fun FichaProyectoScreen(
    nombreProyecto: String,
    onVolver: () -> Unit,
    onInvestigadorClick: (String) -> Unit,
    onPublicacionClick: (String) -> Unit
) {
    val esConstruccion = nombreProyecto.contains("vivienda modular", ignoreCase = true) || nombreProyecto.contains("Análisis sísmico", ignoreCase = true)
    val esBioproductos = nombreProyecto.contains("Biopolímeros", ignoreCase = true) || nombreProyecto.contains("Nanocompuestos", ignoreCase = true) || nombreProyecto.contains("Ciclo de Vida", ignoreCase = true)
    val esGestion = nombreProyecto.contains("Mercados de Exportación", ignoreCase = true) || nombreProyecto.contains("Desarrollo Local", ignoreCase = true)

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

    val esModular = nombreProyecto.contains("vivienda modular", ignoreCase = true)
    val esBiopolimeros = nombreProyecto.contains("Biopolímeros", ignoreCase = true)
    val esNanocompuestos = nombreProyecto.contains("Nanocompuestos", ignoreCase = true)
    val esCicloVida = nombreProyecto.contains("Ciclo de Vida", ignoreCase = true)
    val esMercados = nombreProyecto.contains("Mercados de Exportación", ignoreCase = true)
    val esDesarrolloLocal = nombreProyecto.contains("Desarrollo Local", ignoreCase = true)
    val esRobotica = nombreProyecto.contains("Robótica en Aserraderos", ignoreCase = true)
    val esCeldas = nombreProyecto.contains("Celdas Robotizadas", ignoreCase = true)

    val titulo = when {
        esModular -> "Prototipo de vivienda modular"
        esBiopolimeros -> "Biopolímeros forestales avanzados"
        esNanocompuestos -> "Nanocompuestos estructurales"
        esCicloVida -> "Evaluación de Ciclo de Vida en CLT"
        esMercados -> "Mercados de Exportación Maderera"
        esDesarrolloLocal -> "Desarrollo Local y Territorio"
        esRobotica -> "Robótica en Aserraderos"
        esCeldas -> "Celdas Robotizadas de Producción"
        else -> "Análisis sísmico de uniones"
    }

    val subtitulo = when {
        esModular -> "Una propuesta para explorar la construcción de viviendas mediante módulos de madera."
        esBiopolimeros -> "Desarrollo y caracterización de nuevos biopolímeros derivados de la biomasa forestal."
        esNanocompuestos -> "Aplicación de celulosa nanofibrilada en elementos de alta resistencia mecánica."
        esCicloVida -> "Análisis ambiental y de huella de carbono en la producción y uso de paneles contralaminados de madera."
        esMercados -> "Estudio de cadenas de valor y proyecciones de mercados madereros globales."
        esDesarrolloLocal -> "Evaluación del impacto socioeconómico y estrategias de desarrollo en comunidades forestales."
        esRobotica -> "Implementación de sistemas robóticos y control de alta precisión para la transformación de la madera."
        esCeldas -> "Optimización y automatización avanzada de procesos de manufactura con control numérico y robótica colaborativa."
        else -> "Evaluación de uniones ante sismos y revisión complementaria del desempeño al fuego en CLT."
    }

    val estado = if (esCicloVida || titulo == "Análisis sísmico de uniones") "Finalizado" else "En ejecución"
    val anio = if (esCicloVida || titulo == "Análisis sísmico de uniones") "Año 2024" else "Año 2025"

    val investigadorNombre = when {
        esModular -> "Dra. Ana Silva"
        esBiopolimeros -> "Dr. Luis Morales"
        esNanocompuestos -> "Dra. Sofía Valdés"
        esCicloVida -> "Dr. Mateo Rojas"
        esMercados -> "Dr. Gabriel Fuentes"
        esDesarrolloLocal -> "Dra. Marcela Parra"
        esRobotica -> "Dr. Ignacio Becker"
        esCeldas -> "Dra. Valentina Soto"
        else -> "Dr. Carlos Ruiz"
    }

    val investigadorIniciales = when {
        esModular -> "AS"
        esBiopolimeros -> "LM"
        esNanocompuestos -> "SV"
        esCicloVida -> "MR"
        esMercados -> "GF"
        esDesarrolloLocal -> "MP"
        esRobotica -> "IB"
        esCeldas -> "VS"
        else -> "CR"
    }

    val investigadorEspecialidad = when {
        esModular -> "Ingeniería Estructural"
        esBiopolimeros -> "Química de Materiales"
        esNanocompuestos -> "Nanotecnología de la Madera"
        esCicloVida -> "Gestión Ambiental"
        esMercados -> "Economía Forestal"
        esDesarrolloLocal -> "Sostenibilidad Territorial"
        esRobotica -> "Automatización Industrial"
        esCeldas -> "Manufactura Avanzada"
        else -> "Arquitectura Sustentable"
    }

    val pubTipo = when {
        esModular || esMercados || esDesarrolloLocal -> "Policy Brief · 2025"
        else -> "Artículo científico · 2025"
    }

    val pubTitulo = when {
        esModular -> "Guía de diseño estructural"
        esBiopolimeros -> "Caracterización de biopolímeros"
        esNanocompuestos -> "Nanocompuestos de celulosa"
        esCicloVida -> "Impacto ambiental del CLT"
        esMercados -> "Tendencias del mercado maderero"
        esDesarrolloLocal -> "Desarrollo Local Sostenible"
        esRobotica -> "Automatización en aserraderos"
        esCeldas -> "Manufactura y Robótica Avanzada"
        else -> "Resistencia al fuego en CLT"
    }

    val pubAutoria = when {
        esModular -> "Dra. Ana Silva y Dr. Carlos Ruiz"
        esBiopolimeros -> "Dr. Luis Morales · Biopolímeros forestales"
        esNanocompuestos -> "Dra. Sofía Valdés · Nanocompuestos"
        esCicloVida -> "Dr. Mateo Rojas · Ciclo de Vida en CLT"
        esMercados -> "Dr. Gabriel Fuentes · Mercados de Exportación"
        esDesarrolloLocal -> "Dra. Marcela Parra · Desarrollo Local"
        esRobotica -> "Dr. Ignacio Becker · Robótica en Aserraderos"
        esCeldas -> "Dra. Valentina Soto · Celdas Robotizadas"
        else -> "Dr. Carlos Ruiz · Análisis sísmico de uniones"
    }

    val lineaNombre = when {
        esBioproductos -> "Bioproductos y Biomateriales"
        esGestion -> "Gestión y Economía Forestal"
        !esConstruccion -> "Innovación en Procesos Industriales"
        else -> "Construcción Sustentable en Madera"
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            TopAppBar(
                title = { Text("Ficha de proyecto", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
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
            Text("PROYECTO DE INVESTIGACIÓN", color = colorPrincipal, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Text(titulo, fontSize = 28.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 8.dp), lineHeight = 32.sp)
            Text(subtitulo, color = Color.Gray, fontSize = 16.sp)
        }

        item {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 8.dp)) {
                Surface(
                    color = Color(0xFFE6F4EA),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF1E8E3E), modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(estado, color = Color(0xFF1E8E3E), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(anio, color = Color.Gray, fontSize = 14.sp)
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                colors = CardDefaults.cardColors(containerColor = colorFondoLigero),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Build, contentDescription = null, tint = colorPrincipal, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        lineaNombre,
                        color = colorPrincipal,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = colorPrincipal)
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.TrackChanges, contentDescription = null, tint = colorPrincipal)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("¿Qué busca resolver?", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF1F1A24))
                    }
                    Text(
                        when {
                            esModular -> "Evaluar una solución modular que integre diseño estructural, facilidad de montaje y uso eficiente de madera. La guía relacionada sintetiza criterios para su diseño."
                            esBiopolimeros -> "Transformar componentes de la biomasa forestal en nuevos biopolímeros y materiales de alto valor renovable."
                            esNanocompuestos -> "Desarrollar materiales compuestos de alta resistencia basados en celulosa nanofibrilada para aplicaciones avanzadas."
                            esCicloVida -> "Cuantificar la huella de carbono y el impacto ambiental global del uso de paneles CLT en la construcción industrializada."
                            esMercados -> "Analizar la evolución de la demanda internacional, barreras comerciales y oportunidades estratégicas para la exportación de productos madereros."
                            esDesarrolloLocal -> "Evaluar el impacto socioeconómico y promover estrategias de desarrollo sostenible en las comunidades vinculadas a los territorios forestales."
                            esRobotica -> "Diseñar e implementar algoritmos de control avanzado y celdas robotizadas para elevar la precisión en la transformación industrial de la madera."
                            esCeldas -> "Optimizar los procesos de manufactura y el rendimiento de celdas robotizadas colaborativas orientadas a la prefabricación en madera."
                            else -> "Analizar la respuesta estructural de uniones en construcciones de madera ante eventos sísmicos y validar su comportamiento al fuego."
                        },
                        fontSize = 14.sp,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                    Text("Objetivo y relaciones sintéticos para el caso académico.", fontSize = 12.sp, color = colorPrincipal)
                }
            }
        }

        item {
            Text("Investigador responsable", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(top = 8.dp, bottom = 4.dp))
            FichaInvestigadorCard(
                iniciales = investigadorIniciales,
                especialidad = investigadorEspecialidad,
                nombre = investigadorNombre,
                colorPrincipal = colorPrincipal,
                colorFondo = colorFondoLigero,
                onClick = { onInvestigadorClick(investigadorNombre) }
            )
        }

        item {
            Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 4.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
                Text("Publicación del proyecto", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("1 publicación", color = colorPrincipal, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            FichaPublicacionCard(
                tipo = pubTipo,
                titulo = pubTitulo,
                autoria = pubAutoria,
                colorPrincipal = colorPrincipal,
                colorFondo = colorFondoLigero,
                onClick = { onPublicacionClick(pubTitulo) }
            )
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
fun FichaInvestigadorCard(iniciales: String, especialidad: String, nombre: String, colorPrincipal: Color, colorFondo: Color, onClick: () -> Unit) {
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

@Composable
fun FichaPublicacionCard(tipo: String, titulo: String, autoria: String, colorPrincipal: Color, colorFondo: Color, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0xFFE5E5E5))
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.Top) {
            Box(
                modifier = Modifier.size(48.dp).background(colorFondo, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Outlined.Article, contentDescription = null, tint = colorPrincipal)
            }
            Column(modifier = Modifier.weight(1f).padding(start = 12.dp, end = 8.dp)) {
                Text(tipo, fontSize = 10.sp, color = colorPrincipal, fontWeight = FontWeight.Bold)
                Text(titulo, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(vertical = 2.dp))
                Text(autoria, fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(top = 4.dp))
            }
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.LightGray, modifier = Modifier.align(Alignment.CenterVertically))
        }
    }
}