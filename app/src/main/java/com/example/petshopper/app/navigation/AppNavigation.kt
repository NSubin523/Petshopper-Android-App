package com.example.petshopper.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.petshopper.features.auth.presentation.screen.LoginScreen
import com.example.petshopper.features.auth.presentation.viewmodel.AuthViewModel
import com.example.petshopper.features.bottomnavigation.LandingScreen
import com.example.petshopper.features.bottomnavigation.home.presentation.viewmodel.HomeViewModel
import com.example.petshopper.features.register.presentation.screen.RegisterScreen
import com.example.petshopper.features.register.presentation.viewmodel.CreateUserViewModel

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Home : Screen("home")
    object Register : Screen("register")
}

@Composable
fun AppNavigation(
    authViewModel: AuthViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel(),
    registerUserViewModel: CreateUserViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val authState by authViewModel.state.collectAsState()

    if (authState.isLoadingSplash) { return }
    val startScreen = if (authState.isLoggedIn) Screen.Home.route else Screen.Login.route

    LaunchedEffect(authState.isLoggedIn) {
        if (authState.isLoggedIn) {
            if(navController.currentDestination?.route != Screen.Home.route){
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            }
        } else {
            val currentRoute = navController.currentBackStackEntry?.destination?.route
            if (currentRoute != Screen.Login.route && currentRoute != Screen.Register.route) {
                navController.navigate(Screen.Login.route) {
                    popUpTo(0) { inclusive = true }
                    launchSingleTop = true
                }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = startScreen
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                authViewModel = authViewModel,
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onSignUpClick = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                viewModel = registerUserViewModel,
                onRegistrationComplete = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Home.route) {
            LandingScreen(authViewModel = authViewModel, homeViewModel = homeViewModel)
        }
    }
}