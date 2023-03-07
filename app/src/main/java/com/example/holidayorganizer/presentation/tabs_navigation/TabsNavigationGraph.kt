package com.example.holidayorganizer.presentation.tabs_navigation

import com.example.holidayorganizer.core.ui.navigation.MainNavigationTree
import com.example.holidayorganizer.core.ui.navigation.tabs.TabsNavigationTree
import ru.alexgladkov.odyssey.compose.extensions.customNavigation
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.extensions.tab
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder

fun RootComposeBuilder.tabsNavigationGraph() {
    customNavigation(
        name = MainNavigationTree.Main.name,
        tabsNavModel = CustomConfiguration(
            content = { TabsNavigationScreen() }
        )
    ) {
        TabsNavigationTree.values().forEach { tabNavItem ->
            tab(tabNavItem.tab) {
                screen(name = tabNavItem.name) {
                    tabNavItem.screen()
                }
            }
        }
    }
}