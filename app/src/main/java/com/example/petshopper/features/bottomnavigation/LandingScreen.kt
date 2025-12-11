package com.example.petshopper.features.bottomnavigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.example.petshopper.core.util.constants.Constants
import com.example.petshopper.features.auth.presentation.viewmodel.AuthViewModel
import com.example.petshopper.features.bottomnavigation.home.presentation.screen.HomeScreen
import com.example.petshopper.features.bottomnavigation.home.presentation.viewmodel.HomeViewModel
import com.example.petshopper.features.bottomnavigation.profile.presentation.screen.ProfileScreen

enum class BottomTab(val label: String, val icon: ImageVector) {
    Home(Constants.ScreenLabels.Home.toString(), Icons.Default.Home),
    Cart(Constants.ScreenLabels.Cart.toString(), Icons.Default.ShoppingCart),
    Profile(Constants.ScreenLabels.Profile.toString(), Icons.Default.Person)
}

@Composable
fun LandingScreen(
    authViewModel: AuthViewModel,
    homeViewModel: HomeViewModel,
    onNavigateToAccountInfoScreen: () -> Unit
) {
    var currentTab by rememberSaveable { mutableStateOf(BottomTab.Home) }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                BottomTab.entries.forEach { tab ->
                    NavigationBarItem(
                        icon = { Icon(tab.icon, contentDescription = tab.label) },
                        label = { Text(tab.label) },
                        selected = currentTab == tab,
                        onClick = { currentTab = tab },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        when (currentTab) {
            BottomTab.Home -> {
                HomeScreen(
                    modifier = Modifier.padding(innerPadding),
                    vm = homeViewModel
                )
            }
            BottomTab.Cart -> {
                Box(modifier = Modifier.padding(innerPadding)) { Text("Cart Coming Soon") }
            }
            BottomTab.Profile -> {
                Box(modifier = Modifier.padding(innerPadding)) {
                    ProfileScreen(authViewModel = authViewModel, onNavigateToAccountInfo = onNavigateToAccountInfoScreen)
                }
            }
        }
    }
}