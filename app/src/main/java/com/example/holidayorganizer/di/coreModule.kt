package com.example.holidayorganizer.di
import com.example.holidayorganizer.core.AppInitializer
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val coreModule = module {
    singleOf(::AppInitializer)
}