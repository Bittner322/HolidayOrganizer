package com.example.holidayorganizer.core.ui.navigation

import com.example.holidayorganizer.core.theme.OrganizerTheme
import com.example.holidayorganizer.core.ui.navigation.tabs.tabsNavigationGraph
import com.example.holidayorganizer.presentation.settings_screen.SettingsScreen
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder

fun RootComposeBuilder.navigationGraph() {

    screen(MainNavigationTree.Settings.name) {
        OrganizerTheme {
            SettingsScreen()
        }
    }

    tabsNavigationGraph()

}