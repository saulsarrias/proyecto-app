package com.example.andamiosapp.SharedPreferences

import android.app.Application

class SharesPreferencesApplication:Application() {
    companion object {lateinit var preferences: SharedPreferencesManager}

    override fun onCreate() {
        super.onCreate()
        preferences = SharedPreferencesManager(applicationContext)
    }
}