package com.example.myapplication.pages

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.ui.theme.ThemeViewModel
import com.example.myapplication.utils.ImageUploadHelper
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage(
    onBack: () -> Unit,
    themeViewModel: ThemeViewModel = viewModel()
) {
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var profileImageUrl by remember { mutableStateOf<String?>(null) }
    var name by remember { mutableStateOf(currentUser?.displayName ?: "User") }
    var email by remember { mutableStateOf(currentUser?.email ?: "user@example.com") }
    var isUploading by remember { mutableStateOf(false) }
    var uploadProgress by remember { mutableStateOf(0f) }
    var uploadError by remember { mutableStateOf<String?>(null) }

    val isDarkMode = themeViewModel.isDarkMode
    val backgroundColor = if (isDarkMode) Color(0xFF0F172A) else Color(0xFFF8FAFC)
    val cardColor = if (isDarkMode) Color(0xFF1E293B) else Color.White
    val textColor = if (isDarkMode) Color.White else Color(0xFF0F172A)
    val secondaryTextColor = if (isDarkMode) Color(0xFF94A3B8) else Color(0xFF64748B)

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { selectedUri ->
            // Validate image size (max 5MB)
            val validation = ImageUploadHelper.validateImage(context, selectedUri, maxSizeMB = 5)

            validation.fold(
                onSuccess = {
                    imageUri = selectedUri
                    uploadError = null

                    // Upload to Firebase Storage
                    currentUser?.uid?.let { userId ->
                        isUploading = true
                        scope.launch {
                            val result = ImageUploadHelper.uploadProfileImage(
                                imageUri = selectedUri,
                                userId = userId,
                                onProgress = { progress ->
                                    uploadProgress = progress
                                }
                            )

                            result.fold(
                                onSuccess = { downloadUrl ->
                                    profileImageUrl = downloadUrl
                                    isUploading = false
                                    uploadProgress = 0f
                                },
                                onFailure = { error ->
                                    uploadError = error.message ?: "Upload failed"
                                    isUploading = false
                                    uploadProgress = 0f
                                }
                            )
                        }
                    }
                },
                onFailure = { error ->
                    uploadError = error.message ?: "Image validation failed"
                }
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                actions = {
                    // Theme toggle button
                    IconButton(onClick = { themeViewModel.toggleTheme() }) {
                        Icon(
                            if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = "Toggle theme"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = cardColor,
                    titleContentColor = textColor,
                    navigationIconContentColor = textColor,
                    actionIconContentColor = textColor
                )
            )
        },
        containerColor = backgroundColor
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Image Section
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(if (isDarkMode) Color(0xFF334155) else Color(0xFFE2E8F0))
                    .border(3.dp, Color(0xFF22C55E), CircleShape)
                    .clickable {
                        if (!isUploading) {
                            imagePickerLauncher.launch("image/*")
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                // Show uploaded image or local image
                if (profileImageUrl != null) {
                    Image(
                        painter = rememberAsyncImagePainter(profileImageUrl),
                        contentDescription = "Profile Picture",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else if (imageUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(imageUri),
                        contentDescription = "Profile Picture",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Default Avatar",
                        modifier = Modifier.size(60.dp),
                        tint = secondaryTextColor
                    )
                }

                // Show upload progress
                if (isUploading) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.5f)),
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

            Spacer(modifier = Modifier.height(8.dp))

            // Upload status
            if (isUploading) {
                Text(
                    "Uploading... ${uploadProgress.toInt()}%",
                    fontSize = 12.sp,
                    color = Color(0xFF22C55E),
                    fontWeight = FontWeight.Bold
                )
            } else if (uploadError != null) {
                Text(
                    uploadError!!,
                    fontSize = 12.sp,
                    color = Color(0xFFEF4444),
                    fontWeight = FontWeight.Medium
                )
            } else {
                TextButton(onClick = { imagePickerLauncher.launch("image/*") }) {
                    Text("Change Photo", color = Color(0xFF22C55E))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // User Info Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = cardColor)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    // Name
                    InfoItem(
                        icon = Icons.Default.Person,
                        label = "Name",
                        value = name,
                        textColor = textColor,
                        secondaryTextColor = secondaryTextColor
                    )

                    Divider(
                        modifier = Modifier.padding(vertical = 16.dp),
                        color = if (isDarkMode) Color(0xFF334155) else Color(0xFFE2E8F0)
                    )

                    // Email
                    InfoItem(
                        icon = Icons.Default.Email,
                        label = "Email",
                        value = email,
                        textColor = textColor,
                        secondaryTextColor = secondaryTextColor
                    )

                    Divider(
                        modifier = Modifier.padding(vertical = 16.dp),
                        color = if (isDarkMode) Color(0xFF334155) else Color(0xFFE2E8F0)
                    )

                    // User ID
                    InfoItem(
                        icon = Icons.Default.Badge,
                        label = "User ID",
                        value = currentUser?.uid?.take(12) ?: "N/A",
                        textColor = textColor,
                        secondaryTextColor = secondaryTextColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Settings Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = cardColor)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        "Settings",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Theme Toggle
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { themeViewModel.toggleTheme() }
                            .padding(vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                if (isDarkMode) Icons.Default.DarkMode else Icons.Default.LightMode,
                                contentDescription = null,
                                tint = Color(0xFF22C55E)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                "Dark Mode",
                                fontSize = 16.sp,
                                color = textColor
                            )
                        }
                        Switch(
                            checked = isDarkMode,
                            onCheckedChange = { themeViewModel.toggleTheme() },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = Color(0xFF22C55E)
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Logout Button
            Button(
                onClick = {
                    auth.signOut()
                    onBack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFEF4444)
                )
            ) {
                Icon(Icons.Default.Logout, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Logout", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun InfoItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    textColor: Color,
    secondaryTextColor: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = Color(0xFF22C55E),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                label,
                fontSize = 12.sp,
                color = secondaryTextColor
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                value,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = textColor
            )
        }
    }
}

