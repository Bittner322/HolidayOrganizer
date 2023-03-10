package com.example.holidayorganizer.presentation.tabs_navigation.tabs

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.holidayorganizer.R
import com.example.holidayorganizer.core.theme.OrganizerTheme
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.TabConfiguration
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.TabItem

class HomeTab : TabItem() {

    override val configuration: TabConfiguration
        @Composable
        get() {
            return TabConfiguration(
                title = stringResource(R.string.tab_home),
                selectedIcon = painterResource(R.drawable.ic_home_active),
                unselectedIcon = painterResource(R.drawable.ic_home_inactive),
                selectedColor = OrganizerTheme.color.primary,
                unselectedColor = OrganizerTheme.color.content
            )
        }
}