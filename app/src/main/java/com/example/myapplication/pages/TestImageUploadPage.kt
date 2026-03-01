package com.example.myapplication.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.components.ImageUploadButton
import com.example.myapplication.components.ImageUploadCard
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

/**
 * TestImageUploadPage - Simple page to test image upload functionality
 *
 * Access this page to test uploading images without going through full app flow
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestImageUploadPage(
    onBack: (() -> Unit)? = null
) {
    var profileImageUrl by remember { mutableStateOf<String?>(null) }
    var postImageUrl by remember { mutableStateOf<String?>(null) }
    var uploadError by remember { mutableStateOf<String?>(null) }
    var uploadSuccess by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Test Image Upload") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF22C55E),
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Header
            Text(
                "Image Upload Test",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A)
            )

            Text(
                "Test all image upload features here!",
                fontSize = 14.sp,
                color = Color(0xFF64748B)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Success/Error Messages
            if (uploadSuccess) {
                Surface(
                    color = Color(0xFFDCFCE7),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "✅ Upload Successful!",
                        modifier = Modifier.padding(16.dp),
                        color = Color(0xFF16A34A),
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            if (uploadError != null) {
                Surface(
                    color = Color(0xFFFEE2E2),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "❌ Upload Error:",
                            color = Color(0xFFEF4444),
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            uploadError!!,
                            color = Color(0xFFEF4444),
                            fontSize = 12.sp
                        )
                    }
                }
            }

            // Test 1: Circular Profile Image Upload
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Test 1: Circular Profile Image",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    ImageUploadButton(
                        imageUrl = profileImageUrl,
                        onImageUploaded = { url ->
                            profileImageUrl = url
                            uploadSuccess = true
                            uploadError = null
                        },
                        onError = { error ->
                            uploadError = error
                            uploadSuccess = false
                        },
                        size = 120.dp,
                        shape = CircleShape,
                        folder = "test_profile_images"
                    )

                    if (profileImageUrl != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Image uploaded!",
                            fontSize = 12.sp,
                            color = Color(0xFF22C55E),
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "URL: ${profileImageUrl!!.take(50)}...",
                            fontSize = 10.sp,
                            color = Color(0xFF64748B)
                        )
                    }
                }
            }

            // Test 2: Square Image Upload
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Test 2: Square Image",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    ImageUploadButton(
                        imageUrl = null,
                        onImageUploaded = { url ->
                            uploadSuccess = true
                            uploadError = null
                        },
                        onError = { error ->
                            uploadError = error
                            uploadSuccess = false
                        },
                        size = 150.dp,
                        shape = RoundedCornerShape(16.dp),
                        folder = "test_square_images"
                    )
                }
            }

            // Test 3: Card Upload
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        "Test 3: Card-Style Upload",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    ImageUploadCard(
                        imageUrl = postImageUrl,
                        onImageUploaded = { url ->
                            postImageUrl = url
                            uploadSuccess = true
                            uploadError = null
                        },
                        onError = { error ->
                            uploadError = error
                            uploadSuccess = false
                        },
                        title = "Upload Test Image",
                        folder = "test_post_images"
                    )
                }
            }

            // Instructions
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF0FDF4)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        "📋 How to Test:",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF16A34A)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "1. Tap any image area above\n" +
                        "2. Select image from gallery\n" +
                        "3. Watch upload progress\n" +
                        "4. See success message\n" +
                        "5. Image displays from Firebase!",
                        fontSize = 14.sp,
                        color = Color(0xFF0F172A)
                    )
                }
            }

            // Firebase Setup Status
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFF7ED)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        "⚠️ Setup Required:",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFF59E0B)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "If upload fails:\n" +
                        "1. Sync Gradle\n" +
                        "2. Enable Firebase Storage\n" +
                        "3. Set Storage Rules\n" +
                        "4. Try again!",
                        fontSize = 14.sp,
                        color = Color(0xFF0F172A)
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

