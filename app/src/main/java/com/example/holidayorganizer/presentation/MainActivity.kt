package com.example.holidayorganizer.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import com.example.holidayorganizer.core.AppInitializer
import com.example.holidayorganizer.core.theme.OrganizerTheme
import com.example.holidayorganizer.core.ui.navigation.navigationGraph
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinComponent
import ru.alexgladkov.odyssey.compose.setup.OdysseyConfiguration
import ru.alexgladkov.odyssey.compose.setup.StartScreen
import ru.alexgladkov.odyssey.compose.setup.setNavigationContent
import ru.alexgladkov.odyssey.core.configuration.DisplayType

class MainActivity : ComponentActivity(), KoinComponent {

    private val appInitializer by inject<AppInitializer>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.Transparent.toArgb()
        window.navigationBarColor = Color.Transparent.toArgb()

        setContent {
            OrganizerTheme {
                setNavigationContent(
                    configuration = OdysseyConfiguration(
                        canvas = this,
                        startScreen = StartScreen.Custom(appInitializer.startScreen),
                        backgroundColor = OrganizerTheme.color.background,
                        displayType = DisplayType.EdgeToEdge
                    ),
                    onApplicationFinish = ::finish
                ) {
                    navigationGraph()
                }
            }
        }
    }
}