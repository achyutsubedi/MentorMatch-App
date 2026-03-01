package com.example.myapplication.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myapplication.RegisterPage
import com.example.myapplication.auth.AuthViewModel
import com.example.myapplication.ui.theme.MyApplicationTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test for RegisterPage
 * Tests registration form UI and validation
 */
@RunWith(AndroidJUnit4::class)
class RegisterPageTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun registerPage_displaysAllComponents() {
        // Given
        val viewModel = AuthViewModel()
        composeTestRule.setContent {
            MyApplicationTheme {
                RegisterPage(
                    onNavigateToLogin = {},
                    onNavigateToRoleSelection = {},
                    authViewModel = viewModel
                )
            }
        }

        // Then
        composeTestRule.onNodeWithText("Create Account").assertIsDisplayed()
        composeTestRule.onNodeWithText("Join MentorMatch today").assertIsDisplayed()
    }

    @Test
    fun registerPage_nameField_acceptsInput() {
        // Given
        val viewModel = AuthViewModel()
        composeTestRule.setContent {
            MyApplicationTheme {
                RegisterPage(
                    onNavigateToLogin = {},
                    onNavigateToRoleSelection = {},
                    authViewModel = viewModel
                )
            }
        }

        // When
        val testName = "John Doe"
        composeTestRule.onNodeWithText("Full Name").performTextInput(testName)

        // Then
        composeTestRule.onNodeWithText(testName).assertIsDisplayed()
    }

    @Test
    fun registerPage_emailField_acceptsInput() {
        // Given
        val viewModel = AuthViewModel()
        composeTestRule.setContent {
            MyApplicationTheme {
                RegisterPage(
                    onNavigateToLogin = {},
                    onNavigateToRoleSelection = {},
                    authViewModel = viewModel
                )
            }
        }

        // When
        val testEmail = "john@example.com"
        composeTestRule.onNodeWithText("Email").performTextInput(testEmail)

        // Then
        composeTestRule.onNodeWithText(testEmail).assertIsDisplayed()
    }

    @Test
    fun registerPage_passwordField_acceptsInput() {
        // Given
        val viewModel = AuthViewModel()
        composeTestRule.setContent {
            MyApplicationTheme {
                RegisterPage(
                    onNavigateToLogin = {},
                    onNavigateToRoleSelection = {},
                    authViewModel = viewModel
                )
            }
        }

        // When
        composeTestRule.onNodeWithText("Password").performTextInput("password123")

        // Then - Password field should exist
        composeTestRule.onNodeWithText("Password").assertExists()
    }

    @Test
    fun registerPage_displaysRegisterButton() {
        // Given
        val viewModel = AuthViewModel()
        composeTestRule.setContent {
            MyApplicationTheme {
                RegisterPage(
                    onNavigateToLogin = {},
                    onNavigateToRoleSelection = {},
                    authViewModel = viewModel
                )
            }
        }

        // Then
        composeTestRule.onNodeWithText("Register").assertIsDisplayed()
        composeTestRule.onNodeWithText("Register").assertHasClickAction()
    }

    @Test
    fun registerPage_displaysLoginLink() {
        // Given
        val viewModel = AuthViewModel()
        composeTestRule.setContent {
            MyApplicationTheme {
                RegisterPage(
                    onNavigateToLogin = {},
                    onNavigateToRoleSelection = {},
                    authViewModel = viewModel
                )
            }
        }

        // Then
        composeTestRule.onNodeWithText("Already have an account?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Login").assertIsDisplayed()
    }

    @Test
    fun registerPage_loginLink_isClickable() {
        // Given
        val viewModel = AuthViewModel()
        var clicked = false
        composeTestRule.setContent {
            MyApplicationTheme {
                RegisterPage(
                    onNavigateToLogin = { clicked = true },
                    onNavigateToRoleSelection = {},
                    authViewModel = viewModel
                )
            }
        }

        // When
        composeTestRule.onNodeWithText("Login").performClick()

        // Then
        assert(clicked) { "Login link should trigger navigation" }
    }

    @Test
    fun registerPage_canFillCompleteForm() {
        // Given
        val viewModel = AuthViewModel()
        composeTestRule.setContent {
            MyApplicationTheme {
                RegisterPage(
                    onNavigateToLogin = {},
                    onNavigateToRoleSelection = {},
                    authViewModel = viewModel
                )
            }
        }

        // When - Fill all fields
        composeTestRule.onNodeWithText("Full Name").performTextInput("Jane Smith")
        composeTestRule.onNodeWithText("Email").performTextInput("jane@example.com")
        composeTestRule.onNodeWithText("Password").performTextInput("securepass123")

        // Then - All fields should have content
        composeTestRule.onNodeWithText("Jane Smith").assertIsDisplayed()
        composeTestRule.onNodeWithText("jane@example.com").assertIsDisplayed()
    }
}

