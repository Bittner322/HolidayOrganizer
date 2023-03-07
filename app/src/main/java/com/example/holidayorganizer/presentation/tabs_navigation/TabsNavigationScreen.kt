package com.example.holidayorganizer.presentation.tabs_navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.holidayorganizer.R
import com.example.holidayorganizer.core.theme.NoRippleTheme
import com.example.holidayorganizer.core.theme.OrganizerTheme
import com.example.holidayorganizer.core.ui.BottomNavigationBar
import com.example.holidayorganizer.core.ui.navigation.tabs.TabsNavigationTree
import com.example.holidayorganizer.presentation.tabs_navigation.tabs.HomeTab
import com.example.holidayorganizer.presentation.tabs_navigation.tabs.SettingsTab
import ru.alexgladkov.odyssey.compose.base.AnimatedHost
import ru.alexgladkov.odyssey.compose.controllers.MultiStackRootController
import ru.alexgladkov.odyssey.compose.controllers.TabNavigationModel
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.core.toScreenBundle

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
internal fun TabsNavigationScreen() {

    val rootController = LocalRootController.current as MultiStackRootController
    val nullableSelectedTabItem by rootController.stackChangeObserver.collectAsState()
    val selectedTabItem = nullableSelectedTabItem ?: return

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedTabItem = selectedTabItem,
                onItemSelect = { tab ->
                    val eventName = when (tab.tabInfo.tabItem) {
                        is HomeTab -> "TabHome"
                        is SettingsTab -> "TabSettings"
                        else -> "Undefined"
                    }

                    val position = rootController.tabItems.indexOf(tab)
                    rootController.switchTab(position)
                }
            )
        },
        floatingActionButton = {
            CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                FloatingActionButton(
                    backgroundColor = OrganizerTheme.color.primary,
                    contentColor = OrganizerTheme.color.primaryVariant,
                    onClick = {
                        //rootController.findRootController().push(MainNavigationTree.AddTransaction.name)
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_plus),
                        contentDescription = null,
                        tint = OrganizerTheme.color.white
                    )
                }
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center
    ) {
        TabNavigator(
            modifier = Modifier.fillMaxSize(),
            startScreen = TabsNavigationTree.Home.name,
            currentTab = selectedTabItem
        )
    }
}

@Composable
private fun TabNavigator(
    startScreen: String?,
    currentTab: TabNavigationModel,
    modifier: Modifier = Modifier
) {
    val configuration = currentTab.rootController.currentScreen.collectAsState()
    val saveableStateHolder = rememberSaveableStateHolder()

    saveableStateHolder.SaveableStateProvider(currentTab.tabInfo.tabItem.name) {
        Box(modifier = modifier) {
            CompositionLocalProvider(
                LocalRootController provides currentTab.rootController
            ) {
                configuration.value?.let { navConfig ->
                    AnimatedHost(
                        currentScreen = navConfig.screen.toScreenBundle(),
                        animationType = navConfig.screen.animationType,
                        screenToRemove = navConfig.screenToRemove?.toScreenBundle(),
                        isForward = navConfig.screen.isForward,
                        onScreenRemove = currentTab.rootController.onScreenRemove
                    ) {
                        val rootController = currentTab.rootController
                        rootController.renderScreen(it.realKey, it.params)
                    }
                }
            }
        }
    }

    LaunchedEffect(currentTab) {
        currentTab.rootController.drawCurrentScreen(startScreen = startScreen)
    }
}