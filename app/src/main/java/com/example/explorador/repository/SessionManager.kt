package com.example.explorador.repository

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context? = null) {
    private val prefs: SharedPreferences? =
        context?.getSharedPreferences("cenamad_auth_prefs", Context.MODE_PRIVATE) ?: sharedPrefs

    private val memoryStore = mutableMapOf<String, String>()

    companion object {
        private var sharedPrefs: SharedPreferences? = null

        fun init(context: Context) {
            sharedPrefs = context.applicationContext.getSharedPreferences("cenamad_auth_prefs", Context.MODE_PRIVATE)
        }
    }

    fun registrarUsuario(nombre: String, email: String, password: String): Boolean {
        val emailNormalizado = email.trim().lowercase()
        val p = prefs
        if (p != null) {
            if (p.contains("pass_$emailNormalizado")) {
                return false
            }
            p.edit()
                .putString("name_$emailNormalizado", nombre.trim())
                .putString("pass_$emailNormalizado", password)
                .apply()
            return true
        } else {
            if (memoryStore.containsKey("pass_$emailNormalizado")) {
                return false
            }
            memoryStore["name_$emailNormalizado"] = nombre.trim()
            memoryStore["pass_$emailNormalizado"] = password
            return true
        }
    }

    fun validarCredenciales(email: String, password: String): Boolean {
        val emailNormalizado = email.trim().lowercase()
        val passwordGuardada = prefs?.getString("pass_$emailNormalizado", null)
            ?: memoryStore["pass_$emailNormalizado"]
        return passwordGuardada != null && passwordGuardada == password
    }

    fun guardarSesionActiva(email: String) {
        val emailNormalizado = email.trim().lowercase()
        if (prefs != null) {
            prefs.edit().putString("sesion_activa", emailNormalizado).apply()
        } else {
            memoryStore["sesion_activa"] = emailNormalizado
        }
    }

    fun obtenerSesionActiva(): String? {
        return prefs?.getString("sesion_activa", null) ?: memoryStore["sesion_activa"]
    }

    fun obtenerNombreUsuario(email: String): String {
        val emailNormalizado = email.trim().lowercase()
        return prefs?.getString("name_$emailNormalizado", "")
            ?: memoryStore["name_$emailNormalizado"]
            ?: ""
    }

    fun cerrarSesion() {
        if (prefs != null) {
            prefs.edit().remove("sesion_activa").apply()
        } else {
            memoryStore.remove("sesion_activa")
        }
    }
}
