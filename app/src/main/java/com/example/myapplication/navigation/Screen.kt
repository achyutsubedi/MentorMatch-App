package com.example.myapplication.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Login : Screen("login")
    object Register : Screen("register")
    object ForgotPassword : Screen("forgot_password")
    object RoleSelection : Screen("role_selection")
    object StudentDashboard : Screen("student_dashboard")
    object MentorDashboard : Screen("mentor_dashboard")
    object MentorProfile : Screen("mentor_profile/{mentorId}") {
        fun createRoute(mentorId: String) = "mentor_profile/$mentorId"
    }
    object Profile : Screen("profile")
    object Settings : Screen("settings")
}
