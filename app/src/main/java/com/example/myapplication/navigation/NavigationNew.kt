package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.ForgotPasswordPage
import com.example.myapplication.LoginPage
import com.example.myapplication.RegisterPage
import com.example.myapplication.auth.AuthState
import com.example.myapplication.auth.AuthViewModel
import com.example.myapplication.models.UserRole
import com.example.myapplication.pages.*
import com.example.myapplication.viewmodels.MentorMatchViewModel
import com.example.myapplication.ui.theme.ThemeViewModel

@Composable
fun Navigation(themeViewModel: ThemeViewModel = viewModel()) {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()
    val mentorMatchViewModel: MentorMatchViewModel = viewModel()

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        // Home Page
        composable(Screen.Home.route) {
            HomePage(
                onNavigateToRoleSelection = {
                    navController.navigate(Screen.Register.route)
                },
                onNavigateToLogin = {
                    navController.navigate(Screen.Register.route)
                },
                themeViewModel = themeViewModel
            )
        }

        // Register Page
        composable(Screen.Register.route) {
            RegisterPage(
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                },
                onNavigateToRoleSelection = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                },
                authViewModel = authViewModel,
                themeViewModel = themeViewModel
            )
        }

        // Login Page
        composable(Screen.Login.route) {
            LoginPageWithRole(
                navController = navController,
                authViewModel = authViewModel,
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                },
                onNavigateToForgotPassword = {
                    navController.navigate(Screen.ForgotPassword.route)
                },
                themeViewModel = themeViewModel
            )
        }

        // Student Dashboard
        composable(Screen.StudentDashboard.route) {
            StudentDashboardPage(
                viewModel = mentorMatchViewModel,
                onMentorClick = { mentorId ->
                    navController.navigate(Screen.MentorProfile.createRoute(mentorId))
                },
                onLogout = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onNavigateToSettings = {
                    navController.navigate(Screen.Settings.route)
                }
            )
        }

        // Mentor Dashboard
        composable(Screen.MentorDashboard.route) {
            MentorDashboardPage(
                viewModel = mentorMatchViewModel,
                onLogout = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onNavigateToSettings = {
                    navController.navigate(Screen.Settings.route)
                }
            )
        }

        // Profile Page
        composable(Screen.Profile.route) {
            ProfilePage(
                onBack = {
                    navController.popBackStack()
                },
                themeViewModel = themeViewModel
            )
        }

        // Settings Page
        composable(Screen.Settings.route) {
            SettingsPage(
                onBack = {
                    navController.popBackStack()
                },
                themeViewModel = themeViewModel
            )
        }

        // Mentor Profile Page
        composable(
            route = Screen.MentorProfile.route,
            arguments = listOf(navArgument("mentorId") { type = NavType.StringType })
        ) { backStackEntry ->
            val mentorId = backStackEntry.arguments?.getString("mentorId") ?: ""
            MentorProfilePage(
                mentorId = mentorId,
                viewModel = mentorMatchViewModel,
                onBack = { navController.popBackStack() },
                onRequestMatch = {
                    navController.popBackStack()
                }
            )
        }

        // Forgot Password
        composable(Screen.ForgotPassword.route) {
            ForgotPasswordPage(
                onNavigateToLogin = { navController.popBackStack() },
                authViewModel = authViewModel
            )
        }


        // Role Selection
        composable(Screen.RoleSelection.route) {
            RoleSelectionPage(
                onRoleSelected = { role ->
                    mentorMatchViewModel.setUserRole(role)
                    when (role) {
                        UserRole.STUDENT -> {
                            navController.navigate(Screen.StudentDashboard.route) {
                                popUpTo(Screen.Home.route) { inclusive = false }
                            }
                        }
                        UserRole.MENTOR -> {
                            navController.navigate(Screen.MentorDashboard.route) {
                                popUpTo(Screen.Home.route) { inclusive = false }
                            }
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun LoginPageWithRole(
    navController: androidx.navigation.NavHostController,
    authViewModel: AuthViewModel,
    onNavigateToRegister: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    themeViewModel: ThemeViewModel = viewModel()
) {
    val authState by authViewModel.authState.collectAsState()
    val userRole by authViewModel.userRole.collectAsState()

    LaunchedEffect(authState, userRole) {
        if (authState is AuthState.Authenticated) {
            userRole?.let { role ->
                when (role) {
                    UserRole.STUDENT -> {
                        navController.navigate(Screen.StudentDashboard.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                    UserRole.MENTOR -> {
                        navController.navigate(Screen.MentorDashboard.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                }
            }
        }
    }

    LoginPage(
        onNavigateToRegister = onNavigateToRegister,
        onNavigateToDashboard = {},
        onNavigateToForgotPassword = onNavigateToForgotPassword,
        onNavigateToRoleSelection = {},
        authViewModel = authViewModel,
        themeViewModel = themeViewModel
    )
}

