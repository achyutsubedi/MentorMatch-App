package com.example.myapplication.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ui.theme.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode

data class FeaturedMentor(
    val name: String,
    val subject: String,
    val rating: String,
    val sessions: String,
    val imageRes: Int
)

@Composable
fun HomePage(
    onNavigateToRoleSelection: () -> Unit,
    onNavigateToLogin: () -> Unit,
    themeViewModel: ThemeViewModel = viewModel()
) {
    val scrollState = rememberScrollState()
    val isDarkMode = themeViewModel.isDarkMode
    val backgroundColor = if (isDarkMode) Color(0xFF0F172A) else Color.White
    val textColor = if (isDarkMode) Color.White else Color(0xFF0F172A)

    val featuredMentors = remember {
        listOf(
            FeaturedMentor("Dr. Sarah Johnson", "Physics & Mathematics", "4.9", "1200+", R.drawable.jane),
            FeaturedMentor("Prof. Michael Chen", "Chemistry", "4.8", "980+", R.drawable.chris),
            FeaturedMentor("Dr. Emily Roberts", "Biology", "4.9", "1500+", R.drawable.emily),
            FeaturedMentor("Samuel Anderson", "Computer Science", "4.7", "750+", R.drawable.samuel)
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(backgroundColor)
        ) {
            // Hero Section
            HeroSection(
                onGetStarted = onNavigateToRoleSelection,
                onLogin = onNavigateToLogin,
                isDarkMode = isDarkMode,
                textColor = textColor
            )

            // Stats Section
            StatsSection(isDarkMode = isDarkMode, textColor = textColor)

            // Featured Mentors
            FeaturedMentorsSection(mentors = featuredMentors)

            // Call to Action
            CTASection(onJoinNow = onNavigateToRoleSelection)

            // Footer Spacer
            Spacer(modifier = Modifier.height(40.dp))
        }


        // Floating Dark Mode Toggle Button - Bottom Right
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

@Composable
fun HeroSection(
    onGetStarted: () -> Unit,
    onLogin: () -> Unit,
    isDarkMode: Boolean = false,
    textColor: Color = Color(0xFF0F172A)
) {
    val backgroundColor = if (isDarkMode) Color(0xFF1E293B) else Color(0xFFF0FDF4)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(horizontal = 24.dp, vertical = 60.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Trust Badge
            Surface(
                shape = RoundedCornerShape(50),
                color = if (isDarkMode) Color(0xFF334155) else Color.White,
                shadowElevation = 2.dp,
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("🚀", fontSize = 16.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Trusted by 10,000+ Students",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MentorGreenDark
                    )
                }
            }

            // Main Heading
            Text(
                text = "Find Your Perfect",
                fontSize = 48.sp,
                fontWeight = FontWeight.Black,
                color = textColor,
                textAlign = TextAlign.Center,
                lineHeight = 52.sp
            )
            Text(
                text = "Mentor Today",
                fontSize = 48.sp,
                fontWeight = FontWeight.Black,
                color = MentorGreen,
                textAlign = TextAlign.Center,
                lineHeight = 52.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Subheading
            Text(
                text = "Connect with expert tutors and mentors for personalized learning experiences",
                fontSize = 16.sp,
                color = Gray600,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // CTA Buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                Button(
                    onClick = onGetStarted,
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MentorGreen
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        "Get Started",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                OutlinedButton(
                    onClick = onLogin,
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MentorGreen
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        "Login",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun StatsSection(
    isDarkMode: Boolean = false,
    textColor: Color = Color(0xFF0F172A)
) {
    val cardColor = if (isDarkMode) Color(0xFF1E293B) else Color.White

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(cardColor)
            .padding(vertical = 40.dp, horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        StatItem(value = "98%", label = "Success Rate", textColor = textColor)
        StatItem(value = "500+", label = "Expert Mentors", textColor = textColor)
        StatItem(value = "24/7", label = "Support", textColor = textColor)
    }
}

@Composable
fun StatItem(value: String, label: String, textColor: Color = Gray600) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            fontSize = 32.sp,
            fontWeight = FontWeight.Black,
            color = MentorGreen
        )
        Text(
            text = label,
            fontSize = 14.sp,
            color = Gray600,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun FeaturedMentorsSection(mentors: List<FeaturedMentor>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Gray50)
            .padding(vertical = 48.dp)
    ) {
        Text(
            text = "Featured Mentors",
            fontSize = 32.sp,
            fontWeight = FontWeight.Black,
            color = Gray900,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(mentors) { mentor ->
                MentorCard(mentor)
            }
        }
    }
}

@Composable
fun MentorCard(mentor: FeaturedMentor) {
    Card(
        modifier = Modifier
            .width(280.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            // Mentor Image
            Image(
                painter = painterResource(id = mentor.imageRes),
                contentDescription = mentor.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Name
            Text(
                text = mentor.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Gray900
            )

            // Subject
            Text(
                text = mentor.subject,
                fontSize = 14.sp,
                color = Gray600,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Rating and Sessions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = Color(0xFFFBBF24),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = mentor.rating,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Gray900
                    )
                }

                Text(
                    text = "${mentor.sessions} sessions",
                    fontSize = 12.sp,
                    color = Gray600
                )
            }
        }
    }
}

@Composable
fun CTASection(onJoinNow: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        MentorGreenDark,
                        MentorGreen
                    )
                )
            )
            .padding(vertical = 60.dp, horizontal = 24.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Ready to Start Learning?",
                fontSize = 36.sp,
                fontWeight = FontWeight.Black,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Join thousands of students achieving their goals",
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.9f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onJoinNow,
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    "Join Now",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MentorGreen
                )
            }
        }
    }
}

