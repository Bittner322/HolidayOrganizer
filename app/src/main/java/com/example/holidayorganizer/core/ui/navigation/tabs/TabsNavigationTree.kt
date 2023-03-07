package com.example.holidayorganizer.core.ui.navigation.tabs

import androidx.compose.runtime.Composable
import com.example.holidayorganizer.core.theme.OrganizerTheme
import com.example.holidayorganizer.presentation.home_screen.HomeScreen
import com.example.holidayorganizer.presentation.settings_screen.SettingsScreen
import com.example.holidayorganizer.presentation.tabs_navigation.tabs.HomeTab
import com.example.holidayorganizer.presentation.tabs_navigation.tabs.SettingsTab
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.TabItem

enum class TabsNavigationTree(
    val tab: TabItem,
    val screen: @Composable () -> Unit
) {
    Home(
        tab = HomeTab(),
        screen = {
            OrganizerTheme {
                HomeScreen()
            }
        }
    ),
    Profile(
        tab = SettingsTab(),
        screen = {
            OrganizerTheme {
               SettingsScreen()
            }
        }
    )
}