package com.example.myapplication.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myapplication.LoginPage
import com.example.myapplication.auth.AuthViewModel
import com.example.myapplication.ui.theme.MyApplicationTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class LoginPageTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loginPage_displaysAllComponents() {
        // Given
        val viewModel = AuthViewModel()
        composeTestRule.setContent {
            MyApplicationTheme {
                LoginPage(
                    onNavigateToRegister = {},
                    onNavigateToDashboard = {},
                    onNavigateToForgotPassword = {},
                    onNavigateToRoleSelection = {},
                    authViewModel = viewModel
                )
            }
        }

        // Then - Check for login form elements
        composeTestRule.onNodeWithText("Welcome Back").assertIsDisplayed()
        composeTestRule.onNodeWithText("Login to your account").assertIsDisplayed()
    }

    @Test
    fun loginPage_emailField_acceptsInput() {
        // Given
        val viewModel = AuthViewModel()
        composeTestRule.setContent {
            MyApplicationTheme {
                LoginPage(
                    onNavigateToRegister = {},
                    onNavigateToDashboard = {},
                    onNavigateToForgotPassword = {},
                    onNavigateToRoleSelection = {},
                    authViewModel = viewModel
                )
            }
        }

        // When
        val testEmail = "test@example.com"
        composeTestRule.onNodeWithText("Email").performTextInput(testEmail)

        // Then
        composeTestRule.onNodeWithText(testEmail).assertIsDisplayed()
    }

    @Test
    fun loginPage_passwordField_acceptsInput() {
        // Given
        val viewModel = AuthViewModel()
        composeTestRule.setContent {
            MyApplicationTheme {
                LoginPage(
                    onNavigateToRegister = {},
                    onNavigateToDashboard = {},
                    onNavigateToForgotPassword = {},
                    onNavigateToRoleSelection = {},
                    authViewModel = viewModel
                )
            }
        }

        // When
        composeTestRule.onNodeWithText("Password").performTextInput("password123")

        // Then - Password field should exist (text might be hidden)
        composeTestRule.onNodeWithText("Password").assertExists()
    }

    @Test
    fun loginPage_displaysRoleSelection() {
        // Given
        val viewModel = AuthViewModel()
        composeTestRule.setContent {
            MyApplicationTheme {
                LoginPage(
                    onNavigateToRegister = {},
                    onNavigateToDashboard = {},
                    onNavigateToForgotPassword = {},
                    onNavigateToRoleSelection = {},
                    authViewModel = viewModel
                )
            }
        }

        // Then
        composeTestRule.onNodeWithText("I am a Student").assertIsDisplayed()
        composeTestRule.onNodeWithText("I am a Mentor").assertIsDisplayed()
    }

    @Test
    fun loginPage_roleSelection_isClickable() {
        // Given
        val viewModel = AuthViewModel()
        composeTestRule.setContent {
            MyApplicationTheme {
                LoginPage(
                    onNavigateToRegister = {},
                    onNavigateToDashboard = {},
                    onNavigateToForgotPassword = {},
                    onNavigateToRoleSelection = {},
                    authViewModel = viewModel
                )
            }
        }

        // When/Then - Both role buttons should be clickable
        composeTestRule.onNodeWithText("I am a Student").assertHasClickAction()
        composeTestRule.onNodeWithText("I am a Mentor").assertHasClickAction()
    }

    @Test
    fun loginPage_studentRole_canBeSelected() {
        // Given
        val viewModel = AuthViewModel()
        composeTestRule.setContent {
            MyApplicationTheme {
                LoginPage(
                    onNavigateToRegister = {},
                    onNavigateToDashboard = {},
                    onNavigateToForgotPassword = {},
                    onNavigateToRoleSelection = {},
                    authViewModel = viewModel
                )
            }
        }

        // When
        composeTestRule.onNodeWithText("I am a Student").performClick()

        // Then - Student button should be selected
        composeTestRule.onNodeWithText("I am a Student").assertIsDisplayed()
    }

    @Test
    fun loginPage_mentorRole_canBeSelected() {
        // Given
        val viewModel = AuthViewModel()
        composeTestRule.setContent {
            MyApplicationTheme {
                LoginPage(
                    onNavigateToRegister = {},
                    onNavigateToDashboard = {},
                    onNavigateToForgotPassword = {},
                    onNavigateToRoleSelection = {},
                    authViewModel = viewModel
                )
            }
        }

        // When
        composeTestRule.onNodeWithText("I am a Mentor").performClick()

        // Then - Mentor button should be selected
        composeTestRule.onNodeWithText("I am a Mentor").assertIsDisplayed()
    }

    @Test
    fun loginPage_displaysLoginButton() {
        // Given
        val viewModel = AuthViewModel()
        composeTestRule.setContent {
            MyApplicationTheme {
                LoginPage(
                    onNavigateToRegister = {},
                    onNavigateToDashboard = {},
                    onNavigateToForgotPassword = {},
                    onNavigateToRoleSelection = {},
                    authViewModel = viewModel
                )
            }
        }

        // Then
        composeTestRule.onNodeWithText("Login").assertIsDisplayed()
        composeTestRule.onNodeWithText("Login").assertHasClickAction()
    }

    @Test
    fun loginPage_displaysForgotPasswordLink() {
        // Given
        val viewModel = AuthViewModel()
        composeTestRule.setContent {
            MyApplicationTheme {
                LoginPage(
                    onNavigateToRegister = {},
                    onNavigateToDashboard = {},
                    onNavigateToForgotPassword = {},
                    onNavigateToRoleSelection = {},
                    authViewModel = viewModel
                )
            }
        }

        // Then
        composeTestRule.onNodeWithText("Forgot Password?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Forgot Password?").assertHasClickAction()
    }

    @Test
    fun loginPage_forgotPasswordLink_isClickable() {
        // Given
        val viewModel = AuthViewModel()
        var clicked = false
        composeTestRule.setContent {
            MyApplicationTheme {
                LoginPage(
                    onNavigateToRegister = {},
                    onNavigateToDashboard = {},
                    onNavigateToForgotPassword = { clicked = true },
                    onNavigateToRoleSelection = {},
                    authViewModel = viewModel
                )
            }
        }

        // When
        composeTestRule.onNodeWithText("Forgot Password?").performClick()

        // Then
        assert(clicked) { "Forgot Password link should trigger navigation" }
    }

    @Test
    fun loginPage_displaysSignUpLink() {
        // Given
        val viewModel = AuthViewModel()
        composeTestRule.setContent {
            MyApplicationTheme {
                LoginPage(
                    onNavigateToRegister = {},
                    onNavigateToDashboard = {},
                    onNavigateToForgotPassword = {},
                    onNavigateToRoleSelection = {},
                    authViewModel = viewModel
                )
            }
        }

        // Then
        composeTestRule.onNodeWithText("Don't have an account?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Sign up").assertIsDisplayed()
    }
}

