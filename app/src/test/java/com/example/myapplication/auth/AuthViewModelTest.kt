package com.example.myapplication.auth

import app.cash.turbine.test
import com.example.myapplication.models.UserRole
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

/**
 * Unit tests for AuthViewModel
 */
@OptIn(ExperimentalCoroutinesApi::class)
class AuthViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: AuthViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = AuthViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun initialStateShouldBeIdle() = runTest {
        val initialState = viewModel.authState.value
        assertTrue(initialState is AuthState.Idle)
    }

    @Test
    fun loginUserWithEmptyEmailShowsError() = runTest {
        viewModel.loginUser("", "password123", UserRole.STUDENT)
        advanceUntilIdle()

        viewModel.authState.test {
            val state = awaitItem()
            assertTrue(state is AuthState.Error)
        }
    }

    @Test
    fun registerUserWithEmptyFieldsShowsError() = runTest {
        viewModel.registerUser("", "")
        advanceUntilIdle()

        viewModel.authState.test {
            val state = awaitItem()
            assertTrue(state is AuthState.Error)
        }
    }

    @Test
    fun setUserRoleShouldUpdateRole() = runTest {
        viewModel.setUserRole(UserRole.STUDENT)
        advanceUntilIdle()

        viewModel.userRole.test {
            val role = awaitItem()
            assertEquals(UserRole.STUDENT, role)
        }
    }
}

