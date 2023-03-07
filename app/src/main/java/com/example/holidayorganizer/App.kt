package com.example.holidayorganizer

import android.app.Application
import com.example.holidayorganizer.di.Di
import org.koin.core.component.KoinComponent

class App : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()

        Di.init(this)
    }
}