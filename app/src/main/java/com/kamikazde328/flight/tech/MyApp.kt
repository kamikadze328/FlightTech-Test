package com.kamikazde328.flight.tech

import android.app.Application

class MyApp : Application() {
    companion object {
        private lateinit var instance: MyApp
        fun getInstance(): MyApp {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}