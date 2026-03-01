package com.example.myapplication.auth

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Authenticated : AuthState()
    data class Error(val message: String) : AuthState()
    object UserNotFound : AuthState()
    object RegistrationSuccess : AuthState()
    object PasswordResetSuccess : AuthState()
}
