package com.example.myapplication.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.models.UserRole

@Composable
fun RoleSelectionPage(
    onRoleSelected: (UserRole) -> Unit
) {
    var selectedRole by remember { mutableStateOf<UserRole?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Welcome to MentorMatch",
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFF0F172A),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "How would you like to join?",
                fontSize = 18.sp,
                color = Color(0xFF64748B),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Student Card
            RoleCard(
                title = "Join as Student",
                icon = "📚",
                description = "Find expert mentors to help you excel in your studies and achieve your academic goals",
                features = listOf(
                    "Browse verified mentors",
                    "Book personalized sessions",
                    "Track your progress",
                    "24/7 support access"
                ),
                isSelected = selectedRole == UserRole.STUDENT,
                onClick = { selectedRole = UserRole.STUDENT }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Mentor Card
            RoleCard(
                title = "Join as Mentor",
                icon = "👨‍🏫",
                description = "Share your expertise, help students succeed, and earn while making an impact",
                features = listOf(
                    "Set your own schedule",
                    "Choose your subjects",
                    "Earn competitive rates",
                    "Build your reputation"
                ),
                isSelected = selectedRole == UserRole.MENTOR,
                onClick = { selectedRole = UserRole.MENTOR }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Continue Button
            Button(
                onClick = {
                    selectedRole?.let { onRoleSelected(it) }
                },
                enabled = selectedRole != null,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF22C55E),
                    disabledContainerColor = Color(0xFFE2E8F0)
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    "Continue",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun RoleCard(
    title: String,
    icon: String,
    description: String,
    features: List<String>,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .then(
                if (isSelected) {
                    Modifier.border(
                        width = 3.dp,
                        color = Color(0xFF22C55E),
                        shape = RoundedCornerShape(24.dp)
                    )
                } else {
                    Modifier
                }
            ),
        shape = RoundedCornerShape(24.dp),
        color = if (isSelected) Color(0xFFF0FDF4) else Color.White,
        shadowElevation = if (isSelected) 8.dp else 2.dp
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    icon,
                    fontSize = 40.sp
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                description,
                fontSize = 14.sp,
                color = Color(0xFF64748B),
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            features.forEach { feature ->
                Row(
                    modifier = Modifier.padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "✓",
                        color = Color(0xFF22C55E),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        feature,
                        fontSize = 14.sp,
                        color = Color(0xFF475569)
                    )
                }
            }
        }
    }
}

