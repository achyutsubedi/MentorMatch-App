package com.example.myapplication

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.auth.AuthViewModel
import com.example.myapplication.auth.AuthState
import com.example.myapplication.models.UserRole
import com.example.myapplication.ui.theme.ThemeViewModel

@Composable
fun LoginPage(
    onNavigateToRegister: () -> Unit,
    onNavigateToDashboard: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    onNavigateToRoleSelection: () -> Unit = {},
    authViewModel: AuthViewModel = viewModel(),
    themeViewModel: ThemeViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var selectedRole by remember { mutableStateOf<UserRole?>(null) }
    val authState by authViewModel.authState.collectAsState()
    var showUserNotFoundDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    // Theme-aware colors
    val isDarkMode = themeViewModel.isDarkMode
    val backgroundColor = if (isDarkMode) Color(0xFF0F172A) else Color(0xFFF8FAFC)
    val cardColor = if (isDarkMode) Color(0xFF1E293B) else Color.White
    val textColor = if (isDarkMode) Color.White else Color(0xFF0F172A)
    val secondaryTextColor = if (isDarkMode) Color(0xFF94A3B8) else Color(0xFF64748B)

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Authenticated -> {
                // Navigation handled by LoginPageWithRole wrapper
            }
            is AuthState.UserNotFound -> {
                showUserNotFoundDialog = true
                errorMessage = "Account not found"
            }
            is AuthState.Error -> {
                errorMessage = (authState as AuthState.Error).message
            }
            else -> {}
        }
    }

    if (showUserNotFoundDialog) {
        AlertDialog(
            onDismissRequest = {
                showUserNotFoundDialog = false
                authViewModel.resetState()
            },
            title = {
                Text(
                    "Account Not Found",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text("No account was found with this email. Would you like to create a new account?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        showUserNotFoundDialog = false
                        authViewModel.resetState()
                        onNavigateToRegister()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF22C55E)
                    )
                ) {
                    Text("Create Account")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showUserNotFoundDialog = false
                        authViewModel.resetState()
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // Logo/Branding
            Text(
                "mentor",
                fontSize = 36.sp,
                fontWeight = FontWeight.Black,
                color = textColor
            )
            Text(
                "Match",
                fontSize = 36.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFF22C55E)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "Welcome Back! 👋",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )

            Text(
                "Login to continue your learning journey",
                fontSize = 14.sp,
                color = secondaryTextColor,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Login Form Card
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                color = cardColor,
                shadowElevation = 4.dp
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    // Email Field
                    Text(
                        "Email Address",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = textColor
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            errorMessage = ""
                        },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Enter your email", color = Color(0xFF94A3B8)) },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Email,
                                contentDescription = null,
                                tint = Color(0xFF22C55E)
                            )
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color(0xFFE2E8F0),
                            focusedBorderColor = Color(0xFF22C55E),
                            unfocusedContainerColor = Color(0xFFF8FAFC),
                            focusedContainerColor = Color(0xFFF8FAFC)
                        ),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Password Field
                    Text(
                        "Password",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = textColor
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            errorMessage = ""
                        },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Enter your password", color = Color(0xFF94A3B8)) },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Lock,
                                contentDescription = null,
                                tint = Color(0xFF22C55E)
                            )
                        },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Text(
                                    if (passwordVisible) "👁️" else "🔒",
                                    fontSize = 18.sp
                                )
                            }
                        },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color(0xFFE2E8F0),
                            focusedBorderColor = Color(0xFF22C55E),
                            unfocusedContainerColor = Color(0xFFF8FAFC),
                            focusedContainerColor = Color(0xFFF8FAFC)
                        ),
                        singleLine = true
                    )

                    // Error Message
                    if (errorMessage.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            errorMessage,
                            color = Color(0xFFEF4444),
                            fontSize = 13.sp
                        )
                    }

                    // Forgot Password
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = onNavigateToForgotPassword) {
                            Text(
                                "Forgot Password?",
                                color = Color(0xFF22C55E),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Role Selection
                    Text(
                        "Login as",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = textColor
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Student Button
                        OutlinedButton(
                            onClick = { selectedRole = UserRole.STUDENT },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = if (selectedRole == UserRole.STUDENT) Color(0xFFF0FDF4) else Color.Transparent,
                                contentColor = if (selectedRole == UserRole.STUDENT) Color(0xFF22C55E) else Color(0xFF64748B)
                            ),
                            border = BorderStroke(
                                2.dp,
                                if (selectedRole == UserRole.STUDENT) Color(0xFF22C55E) else Color(0xFFE2E8F0)
                            )
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(vertical = 8.dp)
                            ) {
                                Text("📚", fontSize = 24.sp)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    "Student",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            }
                        }

                        // Mentor Button
                        OutlinedButton(
                            onClick = { selectedRole = UserRole.MENTOR },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = if (selectedRole == UserRole.MENTOR) Color(0xFFF0FDF4) else Color.Transparent,
                                contentColor = if (selectedRole == UserRole.MENTOR) Color(0xFF22C55E) else Color(0xFF64748B)
                            ),
                            border = BorderStroke(
                                2.dp,
                                if (selectedRole == UserRole.MENTOR) Color(0xFF22C55E) else Color(0xFFE2E8F0)
                            )
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(vertical = 8.dp)
                            ) {
                                Text("👨‍🏫", fontSize = 24.sp)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    "Mentor",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Login Button
                    Button(
                        onClick = {
                            if (email.isBlank() || password.isBlank()) {
                                errorMessage = "Please fill in all fields"
                            } else if (selectedRole == null) {
                                errorMessage = "Please select your role (Student or Mentor)"
                            } else {
                                authViewModel.loginUser(email, password, selectedRole!!)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF22C55E)
                        ),
                        enabled = authState != AuthState.Loading
                    ) {
                        if (authState is AuthState.Loading) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        } else {
                            Text(
                                "Login",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Sign Up Link
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Don't have an account?",
                    fontSize = 14.sp,
                    color = Color(0xFF64748B)
                )
                TextButton(onClick = onNavigateToRegister) {
                    Text(
                        "Sign Up",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF22C55E)
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }

        // Floating Dark Mode Toggle Button
        FloatingActionButton(
            onClick = { themeViewModel.toggleTheme() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = if (isDarkMode) Color(0xFF22C55E) else Color(0xFF0F172A)
        ) {
            Icon(
                imageVector = if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                contentDescription = "Toggle Dark Mode",
                tint = Color.White
            )
        }
    }
}

