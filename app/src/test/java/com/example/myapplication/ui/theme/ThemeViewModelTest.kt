package com.example.myapplication.ui.theme

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

/**
 * Unit tests for ThemeViewModel
 * Tests theme switching functionality
 */
@OptIn(ExperimentalCoroutinesApi::class)
class ThemeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: ThemeViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ThemeViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial theme should be light mode`() = runTest {
        // Given - ViewModel is created in setup()

        // When - Check initial state
        val isDarkMode = viewModel.isDarkMode

        // Then - Should be false (light mode)
        assertFalse("Initial theme should be light mode", isDarkMode)
    }

    @Test
    fun `toggleTheme should switch from light to dark`() = runTest {
        // Given
        val initialMode = viewModel.isDarkMode
        assertFalse("Should start in light mode", initialMode)

        // When
        viewModel.toggleTheme()

        // Then
        assertTrue("Should switch to dark mode", viewModel.isDarkMode)
    }

    @Test
    fun `toggleTheme should switch from dark to light`() = runTest {
        // Given
        viewModel.toggleTheme() // Switch to dark mode first
        assertTrue("Should be in dark mode", viewModel.isDarkMode)

        // When
        viewModel.toggleTheme()

        // Then
        assertFalse("Should switch back to light mode", viewModel.isDarkMode)
    }

    @Test
    fun `multiple toggles should alternate correctly`() = runTest {
        // Given - Start in light mode
        assertFalse(viewModel.isDarkMode)

        // When/Then - Toggle multiple times
        viewModel.toggleTheme()
        assertTrue("First toggle - should be dark", viewModel.isDarkMode)

        viewModel.toggleTheme()
        assertFalse("Second toggle - should be light", viewModel.isDarkMode)

        viewModel.toggleTheme()
        assertTrue("Third toggle - should be dark", viewModel.isDarkMode)

        viewModel.toggleTheme()
        assertFalse("Fourth toggle - should be light", viewModel.isDarkMode)
    }
}

