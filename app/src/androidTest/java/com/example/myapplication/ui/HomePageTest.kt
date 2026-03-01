package com.example.myapplication.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myapplication.pages.HomePage
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.ThemeViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class HomePageTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun homePage_displaysWelcomeText() {
        // Given
        composeTestRule.setContent {
            MyApplicationTheme {
                HomePage(
                    onNavigateToRoleSelection = {},
                    onNavigateToLogin = {}
                )
            }
        }

        // Then - Check for key text elements
        composeTestRule.onNodeWithText("Find Your Perfect").assertIsDisplayed()
        composeTestRule.onNodeWithText("Mentor Today").assertIsDisplayed()
    }

    @Test
    fun homePage_displaysGetStartedButton() {
        // Given
        composeTestRule.setContent {
            MyApplicationTheme {
                HomePage(
                    onNavigateToRoleSelection = {},
                    onNavigateToLogin = {}
                )
            }
        }

        // Then
        composeTestRule.onNodeWithText("Get Started").assertIsDisplayed()
        composeTestRule.onNodeWithText("Get Started").assertHasClickAction()
    }

    @Test
    fun homePage_displaysLoginButton() {
        // Given
        composeTestRule.setContent {
            MyApplicationTheme {
                HomePage(
                    onNavigateToRoleSelection = {},
                    onNavigateToLogin = {}
                )
            }
        }

        // Then
        composeTestRule.onNodeWithText("Login").assertIsDisplayed()
        composeTestRule.onNodeWithText("Login").assertHasClickAction()
    }

    @Test
    fun homePage_getStartedButton_isClickable() {
        // Given
        var clicked = false
        composeTestRule.setContent {
            MyApplicationTheme {
                HomePage(
                    onNavigateToRoleSelection = { clicked = true },
                    onNavigateToLogin = {}
                )
            }
        }

        // When
        composeTestRule.onNodeWithText("Get Started").performClick()

        // Then
        assert(clicked) { "Get Started button should trigger navigation" }
    }

    @Test
    fun homePage_loginButton_isClickable() {
        // Given
        var clicked = false
        composeTestRule.setContent {
            MyApplicationTheme {
                HomePage(
                    onNavigateToRoleSelection = {},
                    onNavigateToLogin = { clicked = true }
                )
            }
        }

        // When
        composeTestRule.onNodeWithText("Login").performClick()

        // Then
        assert(clicked) { "Login button should trigger navigation" }
    }

    @Test
    fun homePage_displaysStatsSection() {
        // Given
        composeTestRule.setContent {
            MyApplicationTheme {
                HomePage(
                    onNavigateToRoleSelection = {},
                    onNavigateToLogin = {}
                )
            }
        }

        // Then - Check for stats
        composeTestRule.onNodeWithText("98%").assertIsDisplayed()
        composeTestRule.onNodeWithText("Success Rate").assertIsDisplayed()
        composeTestRule.onNodeWithText("500+").assertIsDisplayed()
        composeTestRule.onNodeWithText("Expert Mentors").assertIsDisplayed()
    }

    @Test
    fun homePage_displaysFeaturedMentorsSection() {
        // Given
        composeTestRule.setContent {
            MyApplicationTheme {
                HomePage(
                    onNavigateToRoleSelection = {},
                    onNavigateToLogin = {}
                )
            }
        }

        // Then
        composeTestRule.onNodeWithText("Featured Mentors").assertIsDisplayed()
    }

    @Test
    fun homePage_darkModeToggle_isDisplayed() {
        // Given
        val themeViewModel = ThemeViewModel()
        composeTestRule.setContent {
            MyApplicationTheme(darkTheme = themeViewModel.isDarkMode) {
                HomePage(
                    onNavigateToRoleSelection = {},
                    onNavigateToLogin = {},
                    themeViewModel = themeViewModel
                )
            }
        }

        // Then
        composeTestRule.onNodeWithContentDescription("Toggle Dark Mode").assertIsDisplayed()
    }

    @Test
    fun homePage_darkModeToggle_changesTheme() {
        // Given
        val themeViewModel = ThemeViewModel()
        composeTestRule.setContent {
            MyApplicationTheme(darkTheme = themeViewModel.isDarkMode) {
                HomePage(
                    onNavigateToRoleSelection = {},
                    onNavigateToLogin = {},
                    themeViewModel = themeViewModel
                )
            }
        }

        // When
        val initialDarkMode = themeViewModel.isDarkMode
        composeTestRule.onNodeWithContentDescription("Toggle Dark Mode").performClick()

        // Then
        assert(themeViewModel.isDarkMode != initialDarkMode) {
            "Dark mode should toggle when button is clicked"
        }
    }
}

