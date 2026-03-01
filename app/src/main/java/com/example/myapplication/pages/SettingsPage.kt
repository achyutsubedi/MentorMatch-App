package com.example.myapplication.pages

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.components.ImageUploadButton
import com.example.myapplication.ui.theme.ThemeViewModel
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsPage(
    onBack: () -> Unit,
    themeViewModel: ThemeViewModel = viewModel()
) {
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    val db = FirebaseFirestore.getInstance()
    val scope = rememberCoroutineScope()

    // Theme colors
    val isDarkMode = themeViewModel.isDarkMode
    val backgroundColor = if (isDarkMode) Color(0xFF0F172A) else Color(0xFFF8FAFC)
    val cardColor = if (isDarkMode) Color(0xFF1E293B) else Color.White
    val textColor = if (isDarkMode) Color.White else Color(0xFF0F172A)
    val secondaryTextColor = if (isDarkMode) Color(0xFF94A3B8) else Color(0xFF64748B)

    // Edit Profile State
    var showEditProfileDialog by remember { mutableStateOf(false) }
    var editName by remember { mutableStateOf(currentUser?.displayName ?: "") }
    var editEmail by remember { mutableStateOf(currentUser?.email ?: "") }
    var profileImageUrl by remember { mutableStateOf<String?>(null) }
    var isSavingProfile by remember { mutableStateOf(false) }
    var profileMessage by remember { mutableStateOf("") }

    // Change Password State
    var showChangePasswordDialog by remember { mutableStateOf(false) }
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showCurrentPassword by remember { mutableStateOf(false) }
    var showNewPassword by remember { mutableStateOf(false) }
    var showConfirmPassword by remember { mutableStateOf(false) }
    var isChangingPassword by remember { mutableStateOf(false) }
    var passwordMessage by remember { mutableStateOf("") }

    // Notification Settings State
    var emailNotifications by remember { mutableStateOf(true) }
    var pushNotifications by remember { mutableStateOf(true) }
    var matchNotifications by remember { mutableStateOf(true) }
    var messageNotifications by remember { mutableStateOf(true) }

    // Load notification settings from Firestore
    LaunchedEffect(currentUser) {
        currentUser?.uid?.let { uid ->
            try {
                val doc = db.collection("users").document(uid).get().await()
                emailNotifications = doc.getBoolean("emailNotifications") ?: true
                pushNotifications = doc.getBoolean("pushNotifications") ?: true
                matchNotifications = doc.getBoolean("matchNotifications") ?: true
                messageNotifications = doc.getBoolean("messageNotifications") ?: true
                profileImageUrl = doc.getString("profileImageUrl")
            } catch (e: Exception) {
                // Use defaults
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = cardColor,
                    titleContentColor = textColor,
                    navigationIconContentColor = textColor
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Account Section
            SectionTitle("Account", textColor)

            SettingsCard(cardColor) {
                SettingsItem(
                    icon = Icons.Default.Person,
                    title = "Edit Profile",
                    subtitle = "Update your name and email",
                    textColor = textColor,
                    secondaryTextColor = secondaryTextColor,
                    onClick = { showEditProfileDialog = true }
                )

                Divider(color = if (isDarkMode) Color(0xFF334155) else Color(0xFFE2E8F0))

                SettingsItem(
                    icon = Icons.Default.Lock,
                    title = "Change Password",
                    subtitle = "Update your password",
                    textColor = textColor,
                    secondaryTextColor = secondaryTextColor,
                    onClick = { showChangePasswordDialog = true }
                )
            }

            // Notifications Section
            SectionTitle("Notifications", textColor)

            SettingsCard(cardColor) {
                NotificationToggleItem(
                    icon = Icons.Default.Email,
                    title = "Email Notifications",
                    subtitle = "Receive updates via email",
                    checked = emailNotifications,
                    textColor = textColor,
                    secondaryTextColor = secondaryTextColor,
                    onCheckedChange = {
                        emailNotifications = it
                        scope.launch {
                            currentUser?.uid?.let { uid ->
                                db.collection("users").document(uid)
                                    .update("emailNotifications", it)
                            }
                        }
                    }
                )

                Divider(color = if (isDarkMode) Color(0xFF334155) else Color(0xFFE2E8F0))

                NotificationToggleItem(
                    icon = Icons.Default.Notifications,
                    title = "Push Notifications",
                    subtitle = "Receive push notifications",
                    checked = pushNotifications,
                    textColor = textColor,
                    secondaryTextColor = secondaryTextColor,
                    onCheckedChange = {
                        pushNotifications = it
                        scope.launch {
                            currentUser?.uid?.let { uid ->
                                db.collection("users").document(uid)
                                    .update("pushNotifications", it)
                            }
                        }
                    }
                )

                Divider(color = if (isDarkMode) Color(0xFF334155) else Color(0xFFE2E8F0))

                NotificationToggleItem(
                    icon = Icons.Default.People,
                    title = "Match Notifications",
                    subtitle = "Get notified about new matches",
                    checked = matchNotifications,
                    textColor = textColor,
                    secondaryTextColor = secondaryTextColor,
                    onCheckedChange = {
                        matchNotifications = it
                        scope.launch {
                            currentUser?.uid?.let { uid ->
                                db.collection("users").document(uid)
                                    .update("matchNotifications", it)
                            }
                        }
                    }
                )

                Divider(color = if (isDarkMode) Color(0xFF334155) else Color(0xFFE2E8F0))

                NotificationToggleItem(
                    icon = Icons.Default.Message,
                    title = "Message Notifications",
                    subtitle = "Get notified about new messages",
                    checked = messageNotifications,
                    textColor = textColor,
                    secondaryTextColor = secondaryTextColor,
                    onCheckedChange = {
                        messageNotifications = it
                        scope.launch {
                            currentUser?.uid?.let { uid ->
                                db.collection("users").document(uid)
                                    .update("messageNotifications", it)
                            }
                        }
                    }
                )
            }

            // Theme Section
            SectionTitle("Appearance", textColor)

            SettingsCard(cardColor) {
                NotificationToggleItem(
                    icon = if (isDarkMode) Icons.Default.DarkMode else Icons.Default.LightMode,
                    title = "Dark Mode",
                    subtitle = "Switch between light and dark theme",
                    checked = isDarkMode,
                    textColor = textColor,
                    secondaryTextColor = secondaryTextColor,
                    onCheckedChange = { themeViewModel.toggleTheme() }
                )
            }

            // About Section
            SectionTitle("About", textColor)

            SettingsCard(cardColor) {
                SettingsItem(
                    icon = Icons.Default.Info,
                    title = "App Version",
                    subtitle = "1.0.0",
                    textColor = textColor,
                    secondaryTextColor = secondaryTextColor,
                    onClick = { }
                )

                Divider(color = if (isDarkMode) Color(0xFF334155) else Color(0xFFE2E8F0))

                SettingsItem(
                    icon = Icons.Default.PrivacyTip,
                    title = "Privacy Policy",
                    subtitle = "Read our privacy policy",
                    textColor = textColor,
                    secondaryTextColor = secondaryTextColor,
                    onClick = { }
                )

                Divider(color = if (isDarkMode) Color(0xFF334155) else Color(0xFFE2E8F0))

                SettingsItem(
                    icon = Icons.Default.Description,
                    title = "Terms of Service",
                    subtitle = "Read our terms",
                    textColor = textColor,
                    secondaryTextColor = secondaryTextColor,
                    onClick = { }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    // Edit Profile Dialog
    if (showEditProfileDialog) {
        AlertDialog(
            onDismissRequest = { showEditProfileDialog = false },
            title = { Text("Edit Profile", fontWeight = FontWeight.Bold) },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Profile Image Upload
                    Text(
                        "Profile Picture",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.align(Alignment.Start)
                    )

                    ImageUploadButton(
                        imageUrl = profileImageUrl,
                        onImageUploaded = { downloadUrl ->
                            profileImageUrl = downloadUrl
                            // Save to Firestore
                            scope.launch {
                                try {
                                    currentUser?.uid?.let { uid ->
                                        db.collection("users").document(uid)
                                            .update("profileImageUrl", downloadUrl)
                                            .await()
                                    }
                                } catch (e: Exception) {
                                    profileMessage = "Error saving image: ${e.message}"
                                }
                            }
                        },
                        onError = { error ->
                            profileMessage = error
                        },
                        size = 100.dp,
                        folder = "profile_images"
                    )

                    OutlinedTextField(
                        value = editName,
                        onValueChange = { editName = it },
                        label = { Text("Name") },
                        leadingIcon = { Icon(Icons.Default.Person, null) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = editEmail,
                        onValueChange = { editEmail = it },
                        label = { Text("Email") },
                        leadingIcon = { Icon(Icons.Default.Email, null) },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = false
                    )

                    if (profileMessage.isNotEmpty()) {
                        Text(
                            profileMessage,
                            fontSize = 12.sp,
                            color = if (profileMessage.contains("success", ignoreCase = true))
                                Color(0xFF22C55E) else Color(0xFFEF4444)
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        isSavingProfile = true
                        scope.launch {
                            try {
                                // Update Firebase Auth profile
                                val profileUpdates = com.google.firebase.auth.UserProfileChangeRequest.Builder()
                                    .setDisplayName(editName)
                                    .build()
                                currentUser?.updateProfile(profileUpdates)?.await()

                                // Update Firestore
                                currentUser?.uid?.let { uid ->
                                    val updates = mutableMapOf<String, Any>("name" to editName)
                                    profileImageUrl?.let { updates["profileImageUrl"] = it }

                                    db.collection("users").document(uid)
                                        .update(updates)
                                        .await()
                                }

                                profileMessage = "Profile updated successfully!"
                                isSavingProfile = false
                                kotlinx.coroutines.delay(1500)
                                showEditProfileDialog = false
                                profileMessage = ""
                            } catch (e: Exception) {
                                profileMessage = "Error: ${e.message}"
                                isSavingProfile = false
                            }
                        }
                    },
                    enabled = !isSavingProfile && editName.isNotBlank()
                ) {
                    if (isSavingProfile) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                    } else {
                        Text("Save")
                    }
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditProfileDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Change Password Dialog
    if (showChangePasswordDialog) {
        AlertDialog(
            onDismissRequest = {
                showChangePasswordDialog = false
                currentPassword = ""
                newPassword = ""
                confirmPassword = ""
                passwordMessage = ""
            },
            title = { Text("Change Password", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = currentPassword,
                        onValueChange = { currentPassword = it },
                        label = { Text("Current Password") },
                        leadingIcon = { Icon(Icons.Default.Lock, null) },
                        trailingIcon = {
                            IconButton(onClick = { showCurrentPassword = !showCurrentPassword }) {
                                Icon(
                                    if (showCurrentPassword) Icons.Default.Visibility
                                    else Icons.Default.VisibilityOff,
                                    null
                                )
                            }
                        },
                        visualTransformation = if (showCurrentPassword)
                            VisualTransformation.None else PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = newPassword,
                        onValueChange = { newPassword = it },
                        label = { Text("New Password") },
                        leadingIcon = { Icon(Icons.Default.Lock, null) },
                        trailingIcon = {
                            IconButton(onClick = { showNewPassword = !showNewPassword }) {
                                Icon(
                                    if (showNewPassword) Icons.Default.Visibility
                                    else Icons.Default.VisibilityOff,
                                    null
                                )
                            }
                        },
                        visualTransformation = if (showNewPassword)
                            VisualTransformation.None else PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = { Text("Confirm New Password") },
                        leadingIcon = { Icon(Icons.Default.Lock, null) },
                        trailingIcon = {
                            IconButton(onClick = { showConfirmPassword = !showConfirmPassword }) {
                                Icon(
                                    if (showConfirmPassword) Icons.Default.Visibility
                                    else Icons.Default.VisibilityOff,
                                    null
                                )
                            }
                        },
                        visualTransformation = if (showConfirmPassword)
                            VisualTransformation.None else PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (passwordMessage.isNotEmpty()) {
                        Text(
                            passwordMessage,
                            fontSize = 12.sp,
                            color = if (passwordMessage.contains("success", ignoreCase = true))
                                Color(0xFF22C55E) else Color(0xFFEF4444)
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        when {
                            currentPassword.isEmpty() -> passwordMessage = "Enter current password"
                            newPassword.isEmpty() -> passwordMessage = "Enter new password"
                            newPassword.length < 6 -> passwordMessage = "Password must be at least 6 characters"
                            newPassword != confirmPassword -> passwordMessage = "Passwords don't match"
                            else -> {
                                isChangingPassword = true
                                scope.launch {
                                    try {
                                        val user = currentUser
                                        val email = user?.email
                                        if (user != null && email != null) {
                                            // Re-authenticate
                                            val credential = EmailAuthProvider.getCredential(email, currentPassword)
                                            user.reauthenticate(credential).await()

                                            // Update password
                                            user.updatePassword(newPassword).await()

                                            passwordMessage = "Password changed successfully!"
                                            isChangingPassword = false
                                            kotlinx.coroutines.delay(1500)
                                            showChangePasswordDialog = false
                                            currentPassword = ""
                                            newPassword = ""
                                            confirmPassword = ""
                                            passwordMessage = ""
                                        }
                                    } catch (e: Exception) {
                                        passwordMessage = "Error: ${e.message}"
                                        isChangingPassword = false
                                    }
                                }
                            }
                        }
                    },
                    enabled = !isChangingPassword
                ) {
                    if (isChangingPassword) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                    } else {
                        Text("Change Password")
                    }
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showChangePasswordDialog = false
                    currentPassword = ""
                    newPassword = ""
                    confirmPassword = ""
                    passwordMessage = ""
                }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun SectionTitle(title: String, textColor: Color) {
    Text(
        text = title,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = textColor.copy(alpha = 0.6f),
        modifier = Modifier.padding(start = 4.dp, top = 8.dp)
    )
}

@Composable
fun SettingsCard(
    cardColor: Color,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(4.dp)
        ) {
            content()
        }
    }
}

@Composable
fun SettingsItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    textColor: Color,
    secondaryTextColor: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(0xFF22C55E).copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = Color(0xFF22C55E),
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = textColor
            )
            Text(
                subtitle,
                fontSize = 12.sp,
                color = secondaryTextColor
            )
        }

        Icon(
            Icons.Default.ChevronRight,
            contentDescription = null,
            tint = secondaryTextColor
        )
    }
}

@Composable
fun NotificationToggleItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    textColor: Color,
    secondaryTextColor: Color,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(0xFF22C55E).copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = Color(0xFF22C55E),
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = textColor
            )
            Text(
                subtitle,
                fontSize = 12.sp,
                color = secondaryTextColor
            )
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color(0xFF22C55E)
            )
        )
    }
}

