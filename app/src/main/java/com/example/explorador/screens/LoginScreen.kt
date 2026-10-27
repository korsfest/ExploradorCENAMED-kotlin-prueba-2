package com.example.explorador.screens

import android.util.Patterns
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onLoginSuccess: (String) -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    fun validateForm(): Boolean {
        var isValid = true

        if (email.isBlank()) {
            emailError = "El correo es obligatorio"
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()) {
            emailError = "Formato de correo inválido"
            isValid = false
        } else {
            emailError = null
        }

        if (password.isBlank()) {
            passwordError = "La contraseña es obligatoria"
            isValid = false
        } else if (password.length < 6) {
            passwordError = "Mínimo 6 caracteres"
            isValid = false
        } else {
            passwordError = null
        }

        return isValid
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "CENAMAD",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2563EB)
        )
        Text(
            text = "Explorador de conocimiento",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                if (emailError != null) validateForm()
            },
            label = { Text("Correo electrónico") },
            isError = emailError != null,
            supportingText = { emailError?.let { Text(it) } },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF2563EB),
                focusedLabelColor = Color(0xFF2563EB)
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                if (passwordError != null) validateForm()
            },
            label = { Text("Contraseña") },
            isError = passwordError != null,
            supportingText = { passwordError?.let { Text(it) } },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = "Alternar visibilidad", tint = Color.Gray)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF2563EB),
                focusedLabelColor = Color(0xFF2563EB)
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (validateForm()) {
                    onLoginSuccess(email.trim())
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Iniciar Sesión", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}