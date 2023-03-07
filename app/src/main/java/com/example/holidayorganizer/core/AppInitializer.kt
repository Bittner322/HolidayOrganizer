package com.example.holidayorganizer.core

import android.util.Log
import com.example.holidayorganizer.core.ui.navigation.MainNavigationTree
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

class AppInitializer: CoroutineScope {

    val startScreen = MainNavigationTree.Main.name

    override val coroutineContext: CoroutineContext = SupervisorJob() +
            CoroutineExceptionHandler { _, throwable ->
                Log.d("AppInitializer", throwable.toString())
            }
}