package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.User
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for user operations
 * Follows Clean Architecture principles
 */
interface UserRepository {

    /**
     * Register a new user with email and password
     */
    suspend fun registerUser(email: String, password: String, user: User): Result<User>

    /**
     * Login user with email and password
     */
    suspend fun loginUser(email: String, password: String): Result<User>

    /**
     * Get current logged in user
     */
    suspend fun getCurrentUser(): Result<User?>

    /**
     * Update user profile
     */
    suspend fun updateUserProfile(user: User): Result<User>

    /**
     * Get all mentors
     */
    fun getAllMentors(): Flow<List<User>>

    /**
     * Get mentor by ID
     */
    suspend fun getMentorById(mentorId: String): Result<User?>

    /**
     * Search mentors by specialty
     */
    fun searchMentorsBySpecialty(specialty: String): Flow<List<User>>

    /**
     * Logout current user
     */
    suspend fun logout(): Result<Unit>

    /**
     * Upload profile image and update user
     */
    suspend fun uploadProfileImage(userId: String, imageUrl: String): Result<String>
}

