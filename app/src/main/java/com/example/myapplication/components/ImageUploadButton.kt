package com.example.myapplication.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.utils.ImageUploadHelper
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

/**
 * ImageUploadButton - Reusable component for image upload
 *
 * Features:
 * - Pick image from gallery
 * - Upload to Firebase Storage
 * - Show upload progress
 * - Error handling
 * - Customizable appearance
 */
@Composable
fun ImageUploadButton(
    imageUrl: String? = null,
    onImageUploaded: (String) -> Unit,
    onError: (String) -> Unit = {},
    modifier: Modifier = Modifier,
    size: Dp = 120.dp,
    shape: androidx.compose.ui.graphics.Shape = CircleShape,
    folder: String = "images",
    placeholderIcon: @Composable () -> Unit = {
        Icon(
            Icons.Default.AddPhotoAlternate,
            contentDescription = "Upload Image",
            modifier = Modifier.size(48.dp),
            tint = Color(0xFF22C55E)
        )
    }
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val currentUser = FirebaseAuth.getInstance().currentUser

    var localImageUri by remember { mutableStateOf<Uri?>(null) }
    var isUploading by remember { mutableStateOf(false) }
    var uploadProgress by remember { mutableStateOf(0f) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { selectedUri ->
            // Validate image
            val validation = ImageUploadHelper.validateImage(context, selectedUri, maxSizeMB = 5)

            validation.fold(
                onSuccess = {
                    localImageUri = selectedUri

                    // Upload to Firebase
                    currentUser?.uid?.let { userId ->
                        isUploading = true
                        scope.launch {
                            val result = ImageUploadHelper.uploadImage(
                                imageUri = selectedUri,
                                folder = folder,
                                userId = userId,
                                onProgress = { progress ->
                                    uploadProgress = progress
                                }
                            )

                            result.fold(
                                onSuccess = { downloadUrl ->
                                    onImageUploaded(downloadUrl)
                                    isUploading = false
                                    uploadProgress = 0f
                                },
                                onFailure = { error ->
                                    onError(error.message ?: "Upload failed")
                                    isUploading = false
                                    uploadProgress = 0f
                                }
                            )
                        }
                    } ?: run {
                        onError("User not authenticated")
                    }
                },
                onFailure = { error ->
                    onError(error.message ?: "Image validation failed")
                }
            )
        }
    }

    Box(
        modifier = modifier
            .size(size)
            .clip(shape)
            .background(Color(0xFFF1F5F9))
            .border(2.dp, Color(0xFF22C55E), shape)
            .clickable {
                if (!isUploading) {
                    imagePickerLauncher.launch("image/*")
                }
            },
        contentAlignment = Alignment.Center
    ) {
        // Show uploaded image or local preview
        if (imageUrl != null) {
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = "Uploaded Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else if (localImageUri != null) {
            Image(
                painter = rememberAsyncImagePainter(localImageUri),
                contentDescription = "Selected Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            placeholderIcon()
        }

        // Show upload progress
        if (isUploading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.6f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    progress = uploadProgress / 100f,
                    color = Color(0xFF22C55E),
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}

/**
 * ImageUploadCard - Card-style image upload component
 */
@Composable
fun ImageUploadCard(
    imageUrl: String? = null,
    onImageUploaded: (String) -> Unit,
    onError: (String) -> Unit = {},
    title: String = "Upload Image",
    modifier: Modifier = Modifier,
    folder: String = "images"
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val currentUser = FirebaseAuth.getInstance().currentUser

    var localImageUri by remember { mutableStateOf<Uri?>(null) }
    var isUploading by remember { mutableStateOf(false) }
    var uploadProgress by remember { mutableStateOf(0f) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { selectedUri ->
            val validation = ImageUploadHelper.validateImage(context, selectedUri, maxSizeMB = 5)

            validation.fold(
                onSuccess = {
                    localImageUri = selectedUri
                    errorMessage = null

                    currentUser?.uid?.let { userId ->
                        isUploading = true
                        scope.launch {
                            val result = ImageUploadHelper.uploadImage(
                                imageUri = selectedUri,
                                folder = folder,
                                userId = userId,
                                onProgress = { progress ->
                                    uploadProgress = progress
                                }
                            )

                            result.fold(
                                onSuccess = { downloadUrl ->
                                    onImageUploaded(downloadUrl)
                                    isUploading = false
                                    uploadProgress = 0f
                                },
                                onFailure = { error ->
                                    errorMessage = error.message ?: "Upload failed"
                                    onError(errorMessage!!)
                                    isUploading = false
                                    uploadProgress = 0f
                                }
                            )
                        }
                    } ?: run {
                        errorMessage = "User not authenticated"
                        onError(errorMessage!!)
                    }
                },
                onFailure = { error ->
                    errorMessage = error.message ?: "Image too large (max 5MB)"
                    onError(errorMessage!!)
                }
            )
        }
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Image preview
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF1F5F9))
                    .clickable {
                        if (!isUploading) {
                            imagePickerLauncher.launch("image/*")
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                if (imageUrl != null) {
                    Image(
                        painter = rememberAsyncImagePainter(imageUrl),
                        contentDescription = "Uploaded Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else if (localImageUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(localImageUri),
                        contentDescription = "Selected Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Default.Upload,
                            contentDescription = "Upload",
                            modifier = Modifier.size(48.dp),
                            tint = Color(0xFF22C55E)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Tap to select image",
                            fontSize = 14.sp,
                            color = Color(0xFF64748B)
                        )
                    }
                }

                // Upload progress overlay
                if (isUploading) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.6f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator(
                                progress = uploadProgress / 100f,
                                color = Color(0xFF22C55E)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "Uploading... ${uploadProgress.toInt()}%",
                                color = Color.White,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }

            // Error message
            if (errorMessage != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    errorMessage!!,
                    fontSize = 12.sp,
                    color = Color(0xFFEF4444),
                    fontWeight = FontWeight.Medium
                )
            }

            // Upload button (when no image selected)
            if (imageUrl == null && localImageUri == null && !isUploading) {
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = { imagePickerLauncher.launch("image/*") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF22C55E)
                    )
                ) {
                    Icon(Icons.Default.Upload, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Choose Image")
                }
            }
        }
    }
}

