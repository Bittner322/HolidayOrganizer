package com.example.holidayorganizer.di

import android.content.Context
import org.koin.core.context.GlobalContext.startKoin

object Di {

    fun init(context: Context) {
        startKoin {
            modules(coreModule)
        }
    }
}