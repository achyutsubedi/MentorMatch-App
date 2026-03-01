package com.example.myapplication.data.repository

import android.util.Log
import com.example.myapplication.domain.model.User
import com.example.myapplication.domain.model.UserRole
import com.example.myapplication.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

/**
 * Firebase implementation of UserRepository
 * Handles all Firebase Auth and Firestore operations
 */
class UserRepositoryImpl(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) : UserRepository {

    companion object {
        private const val TAG = "UserRepository"
        private const val USERS_COLLECTION = "users"
    }

    override suspend fun registerUser(email: String, password: String, user: User): Result<User> {
        return try {
            // Create auth user
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val userId = authResult.user?.uid ?: throw Exception("User ID is null")

            // Create user document in Firestore
            val newUser = user.copy(id = userId, email = email)
            firestore.collection(USERS_COLLECTION)
                .document(userId)
                .set(newUser.toMap())
                .await()

            Log.d(TAG, "User registered successfully: $userId")
            Result.success(newUser)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to register user", e)
            Result.failure(e)
        }
    }

    override suspend fun loginUser(email: String, password: String): Result<User> {
        return try {
            // Sign in with Firebase Auth
            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            val userId = authResult.user?.uid ?: throw Exception("User ID is null")

            // Get user document from Firestore
            val userDoc = firestore.collection(USERS_COLLECTION)
                .document(userId)
                .get()
                .await()

            if (!userDoc.exists()) {
                throw Exception("User document not found")
            }

            val user = User.fromMap(userDoc.data ?: emptyMap())
            Log.d(TAG, "User logged in successfully: $userId")
            Result.success(user)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to login user", e)
            Result.failure(e)
        }
    }

    override suspend fun getCurrentUser(): Result<User?> {
        return try {
            val currentAuthUser = auth.currentUser
            if (currentAuthUser == null) {
                return Result.success(null)
            }

            val userDoc = firestore.collection(USERS_COLLECTION)
                .document(currentAuthUser.uid)
                .get()
                .await()

            if (!userDoc.exists()) {
                return Result.success(null)
            }

            val user = User.fromMap(userDoc.data ?: emptyMap())
            Result.success(user)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to get current user", e)
            Result.failure(e)
        }
    }

    override suspend fun updateUserProfile(user: User): Result<User> {
        return try {
            val userId = auth.currentUser?.uid ?: throw Exception("No user logged in")

            val updatedUser = user.copy(
                id = userId,
                updatedAt = System.currentTimeMillis()
            )

            firestore.collection(USERS_COLLECTION)
                .document(userId)
                .set(updatedUser.toMap())
                .await()

            Log.d(TAG, "User profile updated successfully")
            Result.success(updatedUser)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to update user profile", e)
            Result.failure(e)
        }
    }

    override fun getAllMentors(): Flow<List<User>> = callbackFlow {
        val listenerRegistration = firestore.collection(USERS_COLLECTION)
            .whereEqualTo("role", UserRole.MENTOR.name)
            .orderBy("rating", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e(TAG, "Error getting mentors", error)
                    close(error)
                    return@addSnapshotListener
                }

                val mentors = snapshot?.documents?.mapNotNull { doc ->
                    try {
                        User.fromMap(doc.data ?: emptyMap())
                    } catch (e: Exception) {
                        Log.e(TAG, "Error parsing mentor document", e)
                        null
                    }
                } ?: emptyList()

                trySend(mentors)
            }

        awaitClose { listenerRegistration.remove() }
    }

    override suspend fun getMentorById(mentorId: String): Result<User?> {
        return try {
            val mentorDoc = firestore.collection(USERS_COLLECTION)
                .document(mentorId)
                .get()
                .await()

            if (!mentorDoc.exists()) {
                return Result.success(null)
            }

            val mentor = User.fromMap(mentorDoc.data ?: emptyMap())
            if (mentor.role != UserRole.MENTOR) {
                throw Exception("User is not a mentor")
            }

            Result.success(mentor)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to get mentor by ID", e)
            Result.failure(e)
        }
    }

    override fun searchMentorsBySpecialty(specialty: String): Flow<List<User>> = callbackFlow {
        val listenerRegistration = firestore.collection(USERS_COLLECTION)
            .whereEqualTo("role", UserRole.MENTOR.name)
            .whereEqualTo("specialty", specialty)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e(TAG, "Error searching mentors", error)
                    close(error)
                    return@addSnapshotListener
                }

                val mentors = snapshot?.documents?.mapNotNull { doc ->
                    try {
                        User.fromMap(doc.data ?: emptyMap())
                    } catch (e: Exception) {
                        Log.e(TAG, "Error parsing mentor document", e)
                        null
                    }
                } ?: emptyList()

                trySend(mentors)
            }

        awaitClose { listenerRegistration.remove() }
    }

    override suspend fun logout(): Result<Unit> {
        return try {
            auth.signOut()
            Log.d(TAG, "User logged out successfully")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to logout", e)
            Result.failure(e)
        }
    }

    override suspend fun uploadProfileImage(userId: String, imageUrl: String): Result<String> {
        return try {
            firestore.collection(USERS_COLLECTION)
                .document(userId)
                .update("profileImageUrl", imageUrl, "updatedAt", System.currentTimeMillis())
                .await()

            Log.d(TAG, "Profile image updated successfully")
            Result.success(imageUrl)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to upload profile image", e)
            Result.failure(e)
        }
    }
}

