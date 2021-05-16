package com.android.example.a2048gameapp

import android.app.Application
import com.android.example.a2048gameapp.data.GamePreferences

/**
 * Author : Shwetha V
 **/
class GameApp: Application() {

    override fun onCreate() {
        super.onCreate()
        GamePreferences.initialize(this)
    }
}