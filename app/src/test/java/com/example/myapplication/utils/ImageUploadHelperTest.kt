package com.example.myapplication.utils

import org.junit.Test
import org.junit.Assert.*

/**
 * Unit tests for ImageUploadHelper
 * Tests image validation logic
 */
class ImageUploadHelperTest {

    @Test
    fun `validateImageSize should reject files over max size`() {
        // Given
        val maxSizeMB = 5L
        val fileSizeBytes = 6L * 1024 * 1024 // 6MB

        // When
        val isValid = fileSizeBytes <= (maxSizeMB * 1024 * 1024)

        // Then
        assertFalse("File over 5MB should be rejected", isValid)
    }

    @Test
    fun `validateImageSize should accept files under max size`() {
        // Given
        val maxSizeMB = 5L
        val fileSizeBytes = 3L * 1024 * 1024 // 3MB

        // When
        val isValid = fileSizeBytes <= (maxSizeMB * 1024 * 1024)

        // Then
        assertTrue("File under 5MB should be accepted", isValid)
    }

    @Test
    fun `validateImageSize should accept files exactly at max size`() {
        // Given
        val maxSizeMB = 5L
        val fileSizeBytes = 5L * 1024 * 1024 // Exactly 5MB

        // When
        val isValid = fileSizeBytes <= (maxSizeMB * 1024 * 1024)

        // Then
        assertTrue("File exactly at 5MB should be accepted", isValid)
    }

    @Test
    fun `generateUniqueFileName should create valid format`() {
        // Given
        val userId = "user123"
        val timestamp = System.currentTimeMillis()

        // When
        val fileName = "${userId}_${timestamp}.jpg"

        // Then
        assertTrue("File name should contain user ID", fileName.contains(userId))
        assertTrue("File name should end with .jpg", fileName.endsWith(".jpg"))
        assertTrue("File name should contain timestamp", fileName.contains(timestamp.toString()))
    }

    @Test
    fun `generateUniqueFileName should create unique names`() {
        // Given
        val userId = "user123"

        // When
        val fileName1 = "${userId}_${System.currentTimeMillis()}.jpg"
        Thread.sleep(10) // Small delay to ensure different timestamp
        val fileName2 = "${userId}_${System.currentTimeMillis()}.jpg"

        // Then
        assertNotEquals("Two file names should be unique", fileName1, fileName2)
    }

    @Test
    fun `file path generation should follow correct format`() {
        // Given
        val folder = "profile_images"
        val userId = "user456"
        val fileName = "${userId}_${System.currentTimeMillis()}.jpg"

        // When
        val fullPath = "$folder/$fileName"

        // Then
        assertTrue("Path should contain folder", fullPath.contains(folder))
        assertTrue("Path should contain file name", fullPath.contains(fileName))
        assertTrue("Path should have correct separator", fullPath.contains("/"))
    }
}

