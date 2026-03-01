package com.example.myapplication.domain.model

/**
 * User domain model for Mentor Match application
 * Represents both Mentors and Mentees
 */
data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val role: UserRole = UserRole.MENTEE,
    val profileImageUrl: String? = null,
    val specialty: String? = null, // For mentors
    val bio: String? = null,
    val skills: List<String> = emptyList(),
    val experience: String? = null, // For mentors
    val rating: Double = 0.0, // For mentors
    val totalReviews: Int = 0, // For mentors
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) {
    /**
     * Convert to Firestore map
     */
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "name" to name,
            "email" to email,
            "role" to role.name,
            "profileImageUrl" to profileImageUrl,
            "specialty" to specialty,
            "bio" to bio,
            "skills" to skills,
            "experience" to experience,
            "rating" to rating,
            "totalReviews" to totalReviews,
            "createdAt" to createdAt,
            "updatedAt" to updatedAt
        )
    }

    companion object {
        /**
         * Create User from Firestore document
         */
        fun fromMap(data: Map<String, Any?>): User {
            return User(
                id = data["id"] as? String ?: "",
                name = data["name"] as? String ?: "",
                email = data["email"] as? String ?: "",
                role = UserRole.valueOf(data["role"] as? String ?: "MENTEE"),
                profileImageUrl = data["profileImageUrl"] as? String,
                specialty = data["specialty"] as? String,
                bio = data["bio"] as? String,
                skills = (data["skills"] as? List<*>)?.filterIsInstance<String>() ?: emptyList(),
                experience = data["experience"] as? String,
                rating = (data["rating"] as? Number)?.toDouble() ?: 0.0,
                totalReviews = (data["totalReviews"] as? Number)?.toInt() ?: 0,
                createdAt = (data["createdAt"] as? Number)?.toLong() ?: System.currentTimeMillis(),
                updatedAt = (data["updatedAt"] as? Number)?.toLong() ?: System.currentTimeMillis()
            )
        }
    }
}

/**
 * User role enumeration
 */
enum class UserRole {
    MENTOR,
    MENTEE
}

