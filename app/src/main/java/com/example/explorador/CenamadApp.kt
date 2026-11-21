package com.example.explorador

import android.app.Application
import com.example.explorador.repository.SessionManager

class CenamadApp : Application() {
    override fun onCreate() {
        super.onCreate()
        SessionManager.init(this)
    }
}
