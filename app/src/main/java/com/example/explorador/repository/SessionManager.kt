package com.example.explorador.repository

import android.content.Context

class SessionManager(context: Context) {
    private val prefs = context.getSharedPreferences("cenamad_auth_prefs", Context.MODE_PRIVATE)

    // Guarda un nuevo usuario registrado en la memoria persistente del teléfono
    fun registrarUsuario(nombre: String, email: String, password: String): Boolean {
        val emailNormalizado = email.trim().lowercase()
        if (prefs.contains("pass_$emailNormalizado")) {
            return false // El correo ya está registrado
        }
        prefs.edit()
            .putString("name_$emailNormalizado", nombre.trim())
            .putString("pass_$emailNormalizado", password)
            .apply()
        return true
    }

    // Valida las credenciales contra los usuarios persistidos localmente
    fun validarCredenciales(email: String, password: String): Boolean {
        val emailNormalizado = email.trim().lowercase()
        val passwordGuardada = prefs.getString("pass_$emailNormalizado", null)
        return passwordGuardada != null && passwordGuardada == password
    }

    // Guarda la sesión activa para que no se cierre al salir de la app
    fun guardarSesionActiva(email: String) {
        prefs.edit().putString("sesion_activa", email.trim().lowercase()).apply()
    }

    fun obtenerSesionActiva(): String? {
        return prefs.getString("sesion_activa", null)
    }

    fun cerrarSesion() {
        prefs.edit().remove("sesion_activa").apply()
    }
}