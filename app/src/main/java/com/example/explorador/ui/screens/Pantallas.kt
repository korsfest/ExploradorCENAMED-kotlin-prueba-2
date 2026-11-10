package com.example.explorador.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.explorador.model.LineaInvestigacion
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

val ColorCenamad = Color(0xFFE91E63)
val ColorFondo = Color(0xFFF8F9FA)

@Composable
fun LoginScreen(onLoginSuccess: (String) -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMsg by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().background(Color.White).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("CENAMAD", fontSize = 36.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2563EB))
        Text("Explorador de conocimiento de la madera", color = Color.Gray, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(48.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it; errorMsg = "" },
            label = { Text("Correo electrónico") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it; errorMsg = "" },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        if (errorMsg.isNotEmpty()) {
            Text(text = errorMsg, color = Color.Red, fontSize = 12.sp, modifier = Modifier.padding(top = 8.dp).align(Alignment.Start))
        }

        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                if (email.isBlank() || password.isBlank()) {
                    errorMsg = "Todos los campos son obligatorios"
                } else if (!email.contains("@")) {
                    errorMsg = "Ingrese un correo válido"
                } else if (password.length < 6) {
                    errorMsg = "La contraseña debe tener al menos 6 caracteres"
                } else {
                    onLoginSuccess(email)
                }
            },
            modifier = Modifier.fillMaxWidth().height(54.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Iniciar Sesión", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ExplorarScreen(email: String, onLineaClick: (LineaInvestigacion) -> Unit) {
    val lineas = listOf(
        LineaInvestigacion("1", "Construcción Sustentable en Madera", "Diseño, edificación y comportamiento estructural.", emptyList(), emptyList(), emptyList()),
        LineaInvestigacion("2", "Bioproductos y Biomateriales", "Innovación química y nanotecnología forestal.", emptyList(), emptyList(), emptyList()),
        LineaInvestigacion("3", "Gestión y Economía Forestal", "Desarrollo local, mercados y sustentabilidad.", emptyList(), emptyList(), emptyList()),
        LineaInvestigacion("4", "Innovación en Procesos Industriales", "Automatización, robótica y manufactura avanzada.", emptyList(), emptyList(), emptyList())
    )

    Column(modifier = Modifier.fillMaxSize().background(ColorFondo).padding(16.dp)) {
        Text("CENAMAD Explorador", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Text("Bienvenido, $email", color = Color.Gray, fontSize = 14.sp)

        Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            colors = CardDefaults.cardColors(containerColor = ColorCenamad),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text("Conocimiento que construye futuro.", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold, lineHeight = 28.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Explora las líneas de investigación, proyectos y publicaciones del centro.", color = Color.White.copy(alpha = 0.9f), fontSize = 14.sp)
            }
        }

        Text("Líneas de Investigación", fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(bottom = 12.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(lineas) { linea ->
                Card(
                    modifier = Modifier.fillMaxWidth().clickable { onLineaClick(linea) },
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(48.dp).background(ColorCenamad.copy(alpha = 0.1f), CircleShape), contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.Build, contentDescription = null, tint = ColorCenamad)
                        }
                        Column(modifier = Modifier.weight(1f).padding(horizontal = 16.dp)) {
                            Text(linea.nombre, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            Text(linea.descripcion, color = Color.Gray, fontSize = 12.sp, maxLines = 1)
                        }
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.LightGray)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleLineaScreen(linea: LineaInvestigacion, onVolver: () -> Unit, onVerInvestigadores: () -> Unit, onVerProyectos: () -> Unit, onVerPublicaciones: () -> Unit, onInvestigadorClick: (String) -> Unit) {
    Scaffold(
        topBar = { TopAppBar(title = { Text(linea.nombre, fontSize = 18.sp, maxLines = 1) }, navigationIcon = { IconButton(onClick = onVolver) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver") } }) }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().background(ColorFondo).padding(padding).padding(16.dp).verticalScroll(rememberScrollState())) {
            Text("Acerca de esta línea", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(linea.descripcion, color = Color.DarkGray, modifier = Modifier.padding(top = 8.dp, bottom = 24.dp))

            // ¡ARREGLADO! Ahora muestra 8 investigadores que es el total global
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                EstadisticaCard("Investigadores", "8", Modifier.width(160.dp).clickable { onVerInvestigadores() })
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("Investigadores destacados", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(bottom = 12.dp))

            when (linea.id) {
                "1" -> {
                    InvestigadorItem("Dra. Ana Silva", "Ingeniería Estructural", onInvestigadorClick)
                    InvestigadorItem("Dr. Carlos Ruiz", "Arquitectura Sustentable", onInvestigadorClick)
                }
                "2" -> {
                    InvestigadorItem("Dr. Luis Morales", "Química de Materiales", onInvestigadorClick)
                    InvestigadorItem("Dra. Sofía Valdés", "Nanotecnología de la Madera", onInvestigadorClick)
                }
                "3" -> {
                    InvestigadorItem("Dr. Gabriel Fuentes", "Economía Forestal", onInvestigadorClick)
                    InvestigadorItem("Dra. Marcela Parra", "Sostenibilidad Territorial", onInvestigadorClick)
                }
                "4" -> {
                    InvestigadorItem("Dr. Ignacio Becker", "Automatización Industrial", onInvestigadorClick)
                    InvestigadorItem("Dra. Valentina Soto", "Manufactura Avanzada", onInvestigadorClick)
                }
            }
        }
    }
}

@Composable
fun EstadisticaCard(titulo: String, valor: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier, colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(12.dp)) {
        Column(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(valor, fontWeight = FontWeight.Bold, fontSize = 24.sp, color = ColorCenamad)
            Text(titulo, fontSize = 14.sp, color = Color.Gray)
        }
    }
}

@Composable
fun InvestigadorItem(nombre: String, especialidad: String, onClick: (String) -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp).clickable { onClick(nombre) }, colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(40.dp).background(Color(0xFFE0E7FF), CircleShape), contentAlignment = Alignment.Center) {
                Text(nombre.split(" ").let { if(it.size > 1) "${it[1].first()}" else "I" }, color = Color(0xFF2563EB), fontWeight = FontWeight.Bold)
            }
            Column(modifier = Modifier.padding(start = 16.dp).weight(1f)) {
                Text(nombre, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Text(especialidad, color = Color.Gray, fontSize = 13.sp)
            }
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.LightGray)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FichaProyectoScreen(nombreProyecto: String, onVolver: () -> Unit, onInvestigadorClick: (String) -> Unit, onPublicacionClick: (String) -> Unit) {
    val investigador = when (nombreProyecto) {
        "Análisis sísmico de uniones" -> "Dr. Carlos Ruiz"
        "Biopolímeros forestales avanzados" -> "Dr. Luis Morales"
        "Nanocompuestos estructurales" -> "Dra. Sofía Valdés"
        "Mercados de Exportación Maderera" -> "Dr. Gabriel Fuentes"
        "Desarrollo Local y Territorio" -> "Dra. Marcela Parra"
        "Robótica en Aserraderos" -> "Dr. Ignacio Becker"
        "Celdas Robotizadas de Producción" -> "Dra. Valentina Soto"
        else -> "Dra. Ana Silva" // Prototipo de vivienda modular
    }

    val especialidad = when (investigador) {
        "Dr. Carlos Ruiz" -> "Arquitectura Sustentable"
        "Dr. Luis Morales" -> "Química de Materiales"
        "Dra. Sofía Valdés" -> "Nanotecnología de la Madera"
        "Dr. Gabriel Fuentes" -> "Economía Forestal"
        "Dra. Marcela Parra" -> "Sostenibilidad Territorial"
        "Dr. Ignacio Becker" -> "Automatización Industrial"
        "Dra. Valentina Soto" -> "Manufactura Avanzada"
        else -> "Ingeniería Estructural"
    }

    val publicacionAsociada = when (nombreProyecto) {
        "Análisis sísmico de uniones" -> "Resistencia al fuego en CLT"
        "Biopolímeros forestales avanzados" -> "Caracterización de biopolímeros"
        "Nanocompuestos estructurales" -> "Nanocompuestos de celulosa"
        "Mercados de Exportación Maderera" -> "Tendencias del mercado maderero"
        "Desarrollo Local y Territorio" -> "Desarrollo Local Sostenible"
        "Robótica en Aserraderos" -> "Automatización en aserraderos"
        "Celdas Robotizadas de Producción" -> "Manufactura y Robótica Avanzada"
        else -> "Guía de diseño estructural"
    }

    val tipoPub = if (nombreProyecto == "Análisis sísmico de uniones" || nombreProyecto.contains("Biopolímeros") || nombreProyecto.contains("Nanocompuestos")) "Artículo científico" else "Policy Brief"

    val descripcion = when (nombreProyecto) {
        "Biopolímeros forestales avanzados" -> "Desarrollar nuevos biopolímeros a partir de residuos forestales para su aplicación en la industria de la construcción."
        "Nanocompuestos estructurales" -> "Investigar las propiedades mecánicas de nanocompuestos basados en celulosa para mejorar la resistencia de materiales de construcción."
        "Mercados de Exportación Maderera" -> "Analizar las tendencias del mercado internacional para potenciar la exportación de productos madereros."
        "Desarrollo Local y Territorio" -> "Evaluar el impacto de la industria forestal en las comunidades locales y promover el desarrollo sostenible."
        "Robótica en Aserraderos" -> "Implementar sistemas robóticos en aserraderos para optimizar la producción y reducir riesgos laborales."
        "Celdas Robotizadas de Producción" -> "Diseñar celdas de manufactura avanzada para la creación eficiente de estructuras de madera."
        "Análisis sísmico de uniones" -> "Estudiar la resistencia sísmica de uniones en estructuras de madera masiva (CLT) para mejorar la seguridad."
        else -> "Evaluar el comportamiento y la integridad estructural de la madera mediante módulos y diseños avanzados para lograr soluciones sustentables y eficientes."
    }

    val lineaInvestigacion = when (investigador) {
        "Dr. Luis Morales", "Dra. Sofía Valdés" -> "Bioproductos y Biomateriales"
        "Dr. Gabriel Fuentes", "Dra. Marcela Parra" -> "Gestión y Economía Forestal"
        "Dr. Ignacio Becker", "Dra. Valentina Soto" -> "Innovación en Procesos Industriales"
        else -> "Construcción Sustentable en Madera"
    }

    Scaffold(topBar = { TopAppBar(title = { Text("Ficha de proyecto") }, navigationIcon = { IconButton(onClick = onVolver) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver") } }) }) { padding ->
        Column(modifier = Modifier.fillMaxSize().background(ColorFondo).padding(padding).padding(16.dp).verticalScroll(rememberScrollState())) {
            Text(nombreProyecto, fontWeight = FontWeight.Bold, fontSize = 22.sp, lineHeight = 28.sp)

            Surface(color = ColorCenamad.copy(alpha = 0.1f), shape = RoundedCornerShape(16.dp), modifier = Modifier.padding(vertical = 12.dp)) {
                Text(lineaInvestigacion, color = ColorCenamad, fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp))
            }

            Text("¿Qué busca resolver?", fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(top = 16.dp, bottom = 8.dp))
            Text(descripcion, color = Color.DarkGray, fontSize = 14.sp)

            Text("Investigador responsable", fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(top = 24.dp, bottom = 8.dp))
            InvestigadorItem(investigador, especialidad, onInvestigadorClick)

            Text("Publicación del proyecto", fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(top = 16.dp, bottom = 8.dp))
            Card(modifier = Modifier.fillMaxWidth().clickable { onPublicacionClick(publicacionAsociada) }, colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.Article, contentDescription = null, tint = ColorCenamad)
                    Column(modifier = Modifier.weight(1f).padding(horizontal = 12.dp)) {
                        Text(publicacionAsociada, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text(tipoPub, color = Color.Gray, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FichaPublicacionScreen(nombrePublicacion: String, onVolver: () -> Unit, onInvestigadorClick: (String) -> Unit, onProyectoClick: (String) -> Unit, onInicioClick: () -> Unit) {
    val autor = when (nombrePublicacion) {
        "Resistencia al fuego en CLT" -> "Dr. Carlos Ruiz"
        "Caracterización de biopolímeros" -> "Dr. Luis Morales"
        "Nanocompuestos de celulosa" -> "Dra. Sofía Valdés"
        "Tendencias del mercado maderero" -> "Dr. Gabriel Fuentes"
        "Desarrollo Local Sostenible" -> "Dra. Marcela Parra"
        "Automatización en aserraderos" -> "Dr. Ignacio Becker"
        "Manufactura y Robótica Avanzada" -> "Dra. Valentina Soto"
        else -> "Dra. Ana Silva" // Guía de diseño estructural
    }

    val especialidad = when (autor) {
        "Dr. Carlos Ruiz" -> "Arquitectura Sustentable"
        "Dr. Luis Morales" -> "Química de Materiales"
        "Dra. Sofía Valdés" -> "Nanotecnología de la Madera"
        "Dr. Gabriel Fuentes" -> "Economía Forestal"
        "Dra. Marcela Parra" -> "Sostenibilidad Territorial"
        "Dr. Ignacio Becker" -> "Automatización Industrial"
        "Dra. Valentina Soto" -> "Manufactura Avanzada"
        else -> "Ingeniería Estructural"
    }

    val tipoDoc = if (nombrePublicacion.contains("fuego") || nombrePublicacion.contains("biopolímeros") || nombrePublicacion.contains("celulosa")) "ARTÍCULO CIENTÍFICO" else "POLICY BRIEF"

    val resumen = when (nombrePublicacion) {
        "Caracterización de biopolímeros" -> "Estudio sobre la síntesis y caracterización de nuevos biopolímeros para su uso como adhesivos en la industria maderera."
        "Nanocompuestos de celulosa" -> "Análisis de las propiedades mecánicas y térmicas de nanocompuestos reforzados con nanocristales de celulosa."
        "Tendencias del mercado maderero" -> "Análisis exhaustivo sobre los movimientos y previsiones económicas de los mercados forestales de exportación."
        "Desarrollo Local Sostenible" -> "Estudio de impacto social sobre cómo las prácticas madereras sostenibles benefician a comunidades rurales locales."
        "Automatización en aserraderos" -> "Reporte técnico sobre las nuevas tecnologías robóticas implementadas en los aserraderos chilenos."
        "Manufactura y Robótica Avanzada" -> "Investigación sobre la integración de brazos robóticos en la creación de paneles de madera masiva estructural."
        "Resistencia al fuego en CLT" -> "Evaluación técnica de la resistencia y comportamiento de uniones de CLT sometidas a altas temperaturas."
        else -> "Documento técnico con directrices para el diseño sismorresistente en edificaciones modulares de mediana altura utilizando sistemas de madera masiva."
    }

    Scaffold(topBar = { TopAppBar(title = { Text("Ficha de publicación") }, navigationIcon = { IconButton(onClick = onVolver) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver") } }) }) { padding ->
        Column(modifier = Modifier.fillMaxSize().background(ColorFondo).padding(padding).padding(16.dp).verticalScroll(rememberScrollState())) {
            Text(tipoDoc, color = ColorCenamad, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Text(nombrePublicacion, fontWeight = FontWeight.Bold, fontSize = 22.sp, lineHeight = 28.sp, modifier = Modifier.padding(vertical = 8.dp))

            Text("Resumen", fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(top = 16.dp, bottom = 8.dp))
            Text(resumen, color = Color.DarkGray, fontSize = 14.sp)

            Text("Autoría", fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(top = 24.dp, bottom = 8.dp))
            InvestigadorItem(autor, especialidad, onInvestigadorClick)

            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = onInicioClick, modifier = Modifier.fillMaxWidth().height(50.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB))) {
                Text("Volver al inicio")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FichaInvestigadorScreen(nombreInvestigador: String, onVolver: () -> Unit, onProyectoClick: (String) -> Unit) {
    val especialidad = when (nombreInvestigador) {
        "Dr. Carlos Ruiz" -> "Arquitectura Sustentable"
        "Dr. Luis Morales" -> "Química de Materiales"
        "Dra. Sofía Valdés" -> "Nanotecnología de la Madera"
        "Dr. Gabriel Fuentes" -> "Economía Forestal"
        "Dra. Marcela Parra" -> "Sostenibilidad Territorial"
        "Dr. Ignacio Becker" -> "Automatización Industrial"
        "Dra. Valentina Soto" -> "Manufactura Avanzada"
        else -> "Ingeniería Estructural" // Para Dra. Ana Silva
    }

    val proyectoAsociado = when (nombreInvestigador) {
        "Dr. Carlos Ruiz" -> "Análisis sísmico de uniones"
        "Dr. Luis Morales" -> "Biopolímeros forestales avanzados"
        "Dra. Sofía Valdés" -> "Nanocompuestos estructurales"
        "Dr. Gabriel Fuentes" -> "Mercados de Exportación Maderera"
        "Dra. Marcela Parra" -> "Desarrollo Local y Territorio"
        "Dr. Ignacio Becker" -> "Robótica en Aserraderos"
        "Dra. Valentina Soto" -> "Celdas Robotizadas de Producción"
        else -> "Prototipo de vivienda modular"
    }

    Scaffold(topBar = { TopAppBar(title = { Text("Perfil Investigador") }, navigationIcon = { IconButton(onClick = onVolver) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver") } }) }) { padding ->
        Column(modifier = Modifier.fillMaxSize().background(ColorFondo).padding(padding).padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(modifier = Modifier.size(80.dp).background(Color(0xFFE0E7FF), CircleShape), contentAlignment = Alignment.Center) {
                Text(nombreInvestigador.split(" ").let { if(it.size > 1) "${it[1].first()}" else "I" }, color = Color(0xFF2563EB), fontWeight = FontWeight.Bold, fontSize = 32.sp)
            }
            Text(nombreInvestigador, fontWeight = FontWeight.Bold, fontSize = 22.sp, modifier = Modifier.padding(top = 16.dp))
            Text(especialidad, color = Color.Gray, fontSize = 14.sp)

            Spacer(modifier = Modifier.height(32.dp))
            Card(modifier = Modifier.fillMaxWidth().clickable { onProyectoClick(proyectoAsociado) }, colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Proyectos Asociados", color = Color.Gray, fontSize = 12.sp)
                    Text(proyectoAsociado, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(top = 4.dp))
                }
            }
        }
    }
}

@Composable
fun InfoScreen() {
    // Coordenadas exactas de Duoc UC San Joaquín
    val santiagoDuoc = LatLng(-33.498116, -70.613149)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(santiagoDuoc, 15f)
    }

    Column(modifier = Modifier.fillMaxSize().background(ColorFondo).padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Información de Contacto", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Text("Si tienes dudas, búscanos en nuestra sucursal o escríbenos. Nuestro equipo de ayuda te orientará en tus proyectos de investigación.", color = Color.Gray, modifier = Modifier.padding(vertical = 12.dp), textAlign = androidx.compose.ui.text.style.TextAlign.Center)

        Text("Nuestra Ubicación", fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 16.dp, bottom = 8.dp).align(Alignment.Start))

        Card(modifier = Modifier.fillMaxWidth().height(300.dp), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFE8EAED))) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {
                Marker(
                    state = MarkerState(position = santiagoDuoc),
                    title = "Sede Duoc UC",
                    snippet = "Av. Vicuña Mackenna 4860, Macul"
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvestigadoresScreen(onVolver: () -> Unit, onInvestigadorClick: (String) -> Unit) {
    Scaffold(topBar = { TopAppBar(title = { Text("Lista de Investigadores") }, navigationIcon = { IconButton(onClick = onVolver) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver") } }) }) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().background(ColorFondo).padding(padding).padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            item { InvestigadorItem("Dra. Ana Silva", "Ingeniería Estructural", onInvestigadorClick) }
            item { InvestigadorItem("Dr. Carlos Ruiz", "Arquitectura Sustentable", onInvestigadorClick) }
            item { InvestigadorItem("Dr. Luis Morales", "Química de Materiales", onInvestigadorClick) }
            item { InvestigadorItem("Dra. Sofía Valdés", "Nanotecnología de la Madera", onInvestigadorClick) }
            item { InvestigadorItem("Dr. Gabriel Fuentes", "Economía Forestal", onInvestigadorClick) }
            item { InvestigadorItem("Dra. Marcela Parra", "Sostenibilidad Territorial", onInvestigadorClick) }
            item { InvestigadorItem("Dr. Ignacio Becker", "Automatización Industrial", onInvestigadorClick) }
            item { InvestigadorItem("Dra. Valentina Soto", "Manufactura Avanzada", onInvestigadorClick) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProyectosScreen(onVolver: () -> Unit, onVerProyecto: (String) -> Unit) {
    val proyectos = listOf("Prototipo de vivienda modular", "Análisis sísmico de uniones", "Biopolímeros forestales avanzados", "Nanocompuestos estructurales", "Mercados de Exportación Maderera", "Desarrollo Local y Territorio", "Robótica en Aserraderos", "Celdas Robotizadas de Producción")
    Scaffold(topBar = { TopAppBar(title = { Text("Lista de Proyectos") }, navigationIcon = { IconButton(onClick = onVolver) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver") } }) }) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().background(ColorFondo).padding(padding).padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(proyectos) { proyecto ->
                Card(modifier = Modifier.fillMaxWidth().clickable { onVerProyecto(proyecto) }, colors = CardDefaults.cardColors(containerColor = Color.White)) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Build, contentDescription = null, tint = ColorCenamad)
                        Text(proyecto, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f).padding(horizontal = 16.dp))
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.LightGray)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublicacionesScreen(onVolver: () -> Unit, onVerPublicacion: (String) -> Unit) {
    val publicaciones = listOf("Guía de diseño estructural", "Resistencia al fuego en CLT", "Caracterización de biopolímeros", "Nanocompuestos de celulosa", "Tendencias del mercado maderero", "Desarrollo Local Sostenible", "Automatización en aserraderos", "Manufactura y Robótica Avanzada")
    Scaffold(topBar = { TopAppBar(title = { Text("Lista de Publicaciones") }, navigationIcon = { IconButton(onClick = onVolver) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver") } }) }) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().background(ColorFondo).padding(padding).padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(publicaciones) { pub ->
                Card(modifier = Modifier.fillMaxWidth().clickable { onVerPublicacion(pub) }, colors = CardDefaults.cardColors(containerColor = Color.White)) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.Article, contentDescription = null, tint = ColorCenamad)
                        Text(pub, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f).padding(horizontal = 16.dp))
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.LightGray)
                    }
                }
            }
        }
    }
}