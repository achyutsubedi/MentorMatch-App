package com.example.myapplication.utils

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await
import java.util.UUID

/**
 * ImageUploadHelper - Utility for uploading images to Firebase Storage
 *
 * Features:
 * - Upload images to Firebase Storage
 * - Get download URLs
 * - Delete old images
 * - Progress tracking
 * - Error handling
 */
object ImageUploadHelper {

    private const val TAG = "ImageUploadHelper"
    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.reference

    /**
     * Upload an image to Firebase Storage
     *
     * @param imageUri URI of the image to upload
     * @param folder Folder path in Firebase Storage (e.g., "profile_images", "posts")
     * @param userId Optional user ID to organize files
     * @return Result with download URL on success, or error on failure
     */
    suspend fun uploadImage(
        imageUri: Uri,
        folder: String = "images",
        userId: String? = null,
        onProgress: ((Float) -> Unit)? = null
    ): Result<String> {
        return try {
            // Generate unique filename
            val filename = "${UUID.randomUUID()}.jpg"
            val path = if (userId != null) {
                "$folder/$userId/$filename"
            } else {
                "$folder/$filename"
            }

            // Create storage reference
            val imageRef: StorageReference = storageRef.child(path)

            // Upload file with progress tracking
            val uploadTask = imageRef.putFile(imageUri)

            // Track upload progress
            uploadTask.addOnProgressListener { taskSnapshot ->
                val progress = (100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount).toFloat()
                onProgress?.invoke(progress)
                Log.d(TAG, "Upload progress: $progress%")
            }

            // Wait for upload to complete
            uploadTask.await()

            // Get download URL
            val downloadUrl = imageRef.downloadUrl.await()
            Log.d(TAG, "Image uploaded successfully: $downloadUrl")

            Result.success(downloadUrl.toString())

        } catch (e: Exception) {
            Log.e(TAG, "Error uploading image", e)
            Result.failure(e)
        }
    }

    /**
     * Upload profile image
     * Convenience method specifically for profile images
     */
    suspend fun uploadProfileImage(
        imageUri: Uri,
        userId: String,
        onProgress: ((Float) -> Unit)? = null
    ): Result<String> {
        return uploadImage(
            imageUri = imageUri,
            folder = "profile_images",
            userId = userId,
            onProgress = onProgress
        )
    }

    /**
     * Upload post/content image
     * Convenience method for user-generated content
     */
    suspend fun uploadPostImage(
        imageUri: Uri,
        userId: String,
        onProgress: ((Float) -> Unit)? = null
    ): Result<String> {
        return uploadImage(
            imageUri = imageUri,
            folder = "post_images",
            userId = userId,
            onProgress = onProgress
        )
    }

    /**
     * Delete an image from Firebase Storage
     *
     * @param imageUrl Full download URL of the image
     * @return Result indicating success or failure
     */
    suspend fun deleteImage(imageUrl: String): Result<Boolean> {
        return try {
            // Get reference from URL
            val imageRef = FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl)

            // Delete the file
            imageRef.delete().await()

            Log.d(TAG, "Image deleted successfully")
            Result.success(true)

        } catch (e: Exception) {
            Log.e(TAG, "Error deleting image", e)
            Result.failure(e)
        }
    }

    /**
     * Delete old profile image and upload new one
     *
     * @param oldImageUrl URL of old image to delete (nullable)
     * @param newImageUri URI of new image to upload
     * @param userId User ID
     * @return Result with new download URL
     */
    suspend fun replaceProfileImage(
        oldImageUrl: String?,
        newImageUri: Uri,
        userId: String,
        onProgress: ((Float) -> Unit)? = null
    ): Result<String> {
        return try {
            // Delete old image if it exists
            if (oldImageUrl != null && oldImageUrl.isNotEmpty()) {
                deleteImage(oldImageUrl)
            }

            // Upload new image
            uploadProfileImage(newImageUri, userId, onProgress)

        } catch (e: Exception) {
            Log.e(TAG, "Error replacing profile image", e)
            Result.failure(e)
        }
    }

    /**
     * Get file size from URI (for validation)
     */
    fun getFileSize(context: Context, uri: Uri): Long {
        return try {
            context.contentResolver.openFileDescriptor(uri, "r")?.use { descriptor ->
                descriptor.statSize
            } ?: 0L
        } catch (e: Exception) {
            Log.e(TAG, "Error getting file size", e)
            0L
        }
    }

    /**
     * Validate image before upload
     *
     * @param context Android context
     * @param uri Image URI
     * @param maxSizeMB Maximum file size in MB
     * @return Result indicating if image is valid
     */
    fun validateImage(
        context: Context,
        uri: Uri,
        maxSizeMB: Int = 5
    ): Result<Boolean> {
        return try {
            val fileSize = getFileSize(context, uri)
            val maxSizeBytes = maxSizeMB * 1024 * 1024

            if (fileSize > maxSizeBytes) {
                Result.failure(Exception("Image size exceeds $maxSizeMB MB limit"))
            } else {
                Result.success(true)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

