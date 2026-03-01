package com.example.myapplication.utils

import android.content.Context
import android.net.Uri
import android.util.Log
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * CloudinaryHelper - Handles unsigned image uploads to Cloudinary
 *
 * Setup Instructions:
 * 1. Create a Cloudinary account at https://cloudinary.com
 * 2. Get your Cloud Name from the dashboard
 * 3. Create an unsigned upload preset in Settings → Upload
 * 4. Replace CLOUD_NAME and UPLOAD_PRESET below
 */
object CloudinaryHelper {

    private const val TAG = "CloudinaryHelper"

    // TODO: Replace with your Cloudinary credentials
    private const val CLOUD_NAME = "your_cloud_name"
    private const val UPLOAD_PRESET = "your_upload_preset"

    private var isInitialized = false

    /**
     * Initialize Cloudinary MediaManager
     * Call this once in Application onCreate or before first upload
     */
    fun initialize(context: Context) {
        if (!isInitialized) {
            try {
                val config = mapOf(
                    "cloud_name" to CLOUD_NAME,
                    "secure" to true
                )
                MediaManager.init(context, config)
                isInitialized = true
                Log.d(TAG, "Cloudinary initialized successfully")
            } catch (e: Exception) {
                Log.e(TAG, "Failed to initialize Cloudinary", e)
                throw e
            }
        }
    }

    /**
     * Upload image to Cloudinary using unsigned upload
     *
     * @param imageUri Uri of the image to upload
     * @param folder Optional folder name in Cloudinary (e.g., "profile_pictures")
     * @return URL of the uploaded image
     */
    suspend fun uploadImage(
        imageUri: Uri,
        folder: String = "mentor_match"
    ): Result<String> = suspendCancellableCoroutine { continuation ->

        if (!isInitialized) {
            continuation.resumeWithException(
                IllegalStateException("Cloudinary not initialized. Call initialize() first.")
            )
            return@suspendCancellableCoroutine
        }

        try {
            val requestId = MediaManager.get()
                .upload(imageUri)
                .unsigned(UPLOAD_PRESET)
                .option("folder", folder)
                .option("resource_type", "image")
                .callback(object : UploadCallback {
                    override fun onStart(requestId: String) {
                        Log.d(TAG, "Upload started: $requestId")
                    }

                    override fun onProgress(requestId: String, bytes: Long, totalBytes: Long) {
                        val progress = (bytes.toDouble() / totalBytes * 100).toInt()
                        Log.d(TAG, "Upload progress: $progress%")
                    }

                    override fun onSuccess(
                        requestId: String,
                        resultData: Map<*, *>
                    ) {
                        val secureUrl = resultData["secure_url"] as? String
                        if (secureUrl != null) {
                            Log.d(TAG, "Upload successful: $secureUrl")
                            if (continuation.isActive) {
                                continuation.resume(Result.success(secureUrl))
                            }
                        } else {
                            val error = Exception("No secure_url in response")
                            Log.e(TAG, "Upload failed: No secure_url", error)
                            if (continuation.isActive) {
                                continuation.resume(Result.failure(error))
                            }
                        }
                    }

                    override fun onError(requestId: String, error: ErrorInfo) {
                        val exception = Exception("Cloudinary upload failed: ${error.description}")
                        Log.e(TAG, "Upload error: ${error.description}", exception)
                        if (continuation.isActive) {
                            continuation.resume(Result.failure(exception))
                        }
                    }

                    override fun onReschedule(requestId: String, error: ErrorInfo) {
                        Log.w(TAG, "Upload rescheduled: ${error.description}")
                    }
                })
                .dispatch()

            continuation.invokeOnCancellation {
                try {
                    MediaManager.get().cancelRequest(requestId)
                    Log.d(TAG, "Upload cancelled: $requestId")
                } catch (e: Exception) {
                    Log.e(TAG, "Error cancelling upload", e)
                }
            }

        } catch (e: Exception) {
            Log.e(TAG, "Failed to start upload", e)
            if (continuation.isActive) {
                continuation.resume(Result.failure(e))
            }
        }
    }

    /**
     * Upload image with custom options
     */
    suspend fun uploadImageWithOptions(
        imageUri: Uri,
        folder: String = "mentor_match",
        publicId: String? = null,
        tags: List<String> = emptyList()
    ): Result<CloudinaryUploadResult> = suspendCancellableCoroutine { continuation ->

        if (!isInitialized) {
            continuation.resumeWithException(
                IllegalStateException("Cloudinary not initialized")
            )
            return@suspendCancellableCoroutine
        }

        try {
            var uploadRequest = MediaManager.get()
                .upload(imageUri)
                .unsigned(UPLOAD_PRESET)
                .option("folder", folder)
                .option("resource_type", "image")

            if (publicId != null) {
                uploadRequest = uploadRequest.option("public_id", publicId)
            }

            if (tags.isNotEmpty()) {
                uploadRequest = uploadRequest.option("tags", tags.joinToString(","))
            }

            val requestId = uploadRequest
                .callback(object : UploadCallback {
                    override fun onStart(requestId: String) {
                        Log.d(TAG, "Upload started: $requestId")
                    }

                    override fun onProgress(requestId: String, bytes: Long, totalBytes: Long) {
                        val progress = (bytes.toDouble() / totalBytes * 100).toInt()
                        Log.d(TAG, "Upload progress: $progress%")
                    }

                    override fun onSuccess(requestId: String, resultData: Map<*, *>) {
                        val result = CloudinaryUploadResult(
                            secureUrl = resultData["secure_url"] as? String ?: "",
                            publicId = resultData["public_id"] as? String ?: "",
                            format = resultData["format"] as? String ?: "",
                            width = (resultData["width"] as? Number)?.toInt() ?: 0,
                            height = (resultData["height"] as? Number)?.toInt() ?: 0,
                            bytes = (resultData["bytes"] as? Number)?.toLong() ?: 0L
                        )

                        Log.d(TAG, "Upload successful: ${result.secureUrl}")
                        if (continuation.isActive) {
                            continuation.resume(Result.success(result))
                        }
                    }

                    override fun onError(requestId: String, error: ErrorInfo) {
                        val exception = Exception("Upload failed: ${error.description}")
                        Log.e(TAG, "Upload error", exception)
                        if (continuation.isActive) {
                            continuation.resume(Result.failure(exception))
                        }
                    }

                    override fun onReschedule(requestId: String, error: ErrorInfo) {
                        Log.w(TAG, "Upload rescheduled: ${error.description}")
                    }
                })
                .dispatch()

            continuation.invokeOnCancellation {
                try {
                    MediaManager.get().cancelRequest(requestId)
                } catch (e: Exception) {
                    Log.e(TAG, "Error cancelling upload", e)
                }
            }

        } catch (e: Exception) {
            Log.e(TAG, "Failed to start upload", e)
            if (continuation.isActive) {
                continuation.resume(Result.failure(e))
            }
        }
    }
}

/**
 * Result data class for Cloudinary upload
 */
data class CloudinaryUploadResult(
    val secureUrl: String,
    val publicId: String,
    val format: String,
    val width: Int,
    val height: Int,
    val bytes: Long
)

