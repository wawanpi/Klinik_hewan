package com.example.klinikhewan

import android.app.Application
import com.example.klinikhewan.dependeciesinjection.AppContainerImpl

class KlinikApplications : Application() {
    lateinit var container: AppContainerImpl

    override fun onCreate() {
        super.onCreate()
        container = AppContainerImpl()  // Menggunakan AppContainerImpl yang sudah digabung
    }
}
