package com.example.explorador.view

import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.explorador.model.CurrentWeather
import com.example.explorador.ui.theme.*

@Composable
fun InfoScreen(
    userEmail: String = "",
    climaSede: CurrentWeather? = null,
    onCerrarSesion: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Información de Contacto",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = TextoOscuro
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = CelesteLineaFondo),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Si tienes dudas, búscanos en nuestra sucursal. ¡Nuestro equipo estará encantado de ayudarte con tus proyectos de investigación!",
                modifier = Modifier.padding(14.dp),
                fontSize = 14.sp,
                color = AzulInfoTexto
            )
        }

        if (climaSede != null) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = VerdeLineaFondo),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Cloud,
                        contentDescription = "Clima en Sede",
                        tint = VerdeLinea,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Clima en Sede Santiago: ${climaSede.temperature} °C · Viento ${climaSede.windspeed} km/h",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = VerdeEstadoTexto
                    )
                }
            }
        }

        Text(
            text = "Nuestra Ubicación",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = TextoOscuro
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { context ->
                    WebView(context).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        setLayerType(View.LAYER_TYPE_SOFTWARE, null)

                        settings.javaScriptEnabled = true
                        settings.domStorageEnabled = true
                        webViewClient = WebViewClient()
                        webChromeClient = WebChromeClient()
                    }
                },
                update = { view ->
                    val mapHtml = """
                        <!DOCTYPE html>
                        <html>
                        <head>
                            <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
                            <style>body, html { margin: 0; padding: 0; height: 100%; width: 100%; }</style>
                        </head>
                        <body>
                            <iframe width="100%" height="100%" frameborder="0" style="border:0" 
                                src="https://maps.google.com/maps?q=-33.443018,-70.653870&z=16&output=embed" allowfullscreen>
                            </iframe>
                        </body>
                        </html>
                    """.trimIndent()

                    view.loadDataWithBaseURL(null, mapHtml, "text/html", "UTF-8", null)
                }
            )
        }

        if (userEmail.isNotBlank()) {
            Text(
                text = "Sesión activa: $userEmail",
                fontSize = 13.sp,
                color = GrisTexto
            )
        }

        Button(
            onClick = onCerrarSesion,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = RojoError),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Logout,
                contentDescription = "Cerrar Sesión",
                tint = Blanco
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Cerrar Sesión",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Blanco
            )
        }
    }
}
