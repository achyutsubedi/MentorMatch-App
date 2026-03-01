package com.example.myapplication.models

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val role: UserRole = UserRole.STUDENT,
    val profileImageUrl: String = "",
    val bio: String = "",
    val phoneNumber: String = ""
)

enum class UserRole {
    STUDENT,
    MENTOR
}

