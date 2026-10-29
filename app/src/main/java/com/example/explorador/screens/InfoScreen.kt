package com.example.explorador.screens

import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun InfoScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Información de Contacto",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1F1A24)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE1F5FE)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Si tienes dudas, búscanos en nuestra sucursal. ¡Nuestro equipo estará encantado de ayudarte con tus proyectos de investigación!",
                modifier = Modifier.padding(16.dp),
                fontSize = 15.sp,
                color = Color(0xFF0277BD)
            )
        }

        Text(
            text = "Nuestra Ubicación",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1F1A24)
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
    }
}