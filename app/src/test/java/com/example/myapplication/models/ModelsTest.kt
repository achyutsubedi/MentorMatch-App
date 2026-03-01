package com.example.myapplication.models

import org.junit.Test
import org.junit.Assert.*

/**
 * Unit tests for data models
 */
class ModelsTest {

    @Test
    fun userRoleEnumHasCorrectValues() {
        val roles = UserRole.entries
        assertEquals(2, roles.size)
        assertTrue(roles.contains(UserRole.STUDENT))
        assertTrue(roles.contains(UserRole.MENTOR))
    }

    @Test
    fun userRoleStudentHasCorrectStringValue() {
        val role = UserRole.STUDENT.toString()
        assertEquals("STUDENT", role)
    }

    @Test
    fun userRoleMentorHasCorrectStringValue() {
        val role = UserRole.MENTOR.toString()
        assertEquals("MENTOR", role)
    }

    @Test
    fun mentorModelCreatedCorrectly() {
        val id = "mentor123"
        val name = "Dr. John Doe"
        val email = "john@example.com"
        val bio = "Expert in Computer Science"
        val expertise = listOf("AI", "Machine Learning")
        val rating = 4.8f
        val totalSessions = 120
        val profileImageUrl = "https://example.com/image.jpg"

        val mentor = Mentor(
            id = id,
            name = name,
            email = email,
            bio = bio,
            expertise = expertise,
            rating = rating,
            totalSessions = totalSessions,
            profileImageUrl = profileImageUrl
        )

        assertEquals(id, mentor.id)
        assertEquals(name, mentor.name)
        assertEquals(email, mentor.email)
        assertEquals(bio, mentor.bio)
        assertEquals(expertise, mentor.expertise)
        assertEquals(rating, mentor.rating, 0.01f)
        assertEquals(totalSessions, mentor.totalSessions)
        assertEquals(profileImageUrl, mentor.profileImageUrl)
    }

    @Test
    fun mentorModelWithEmptyImageWorks() {
        val mentor = Mentor(
            id = "mentor456",
            name = "Jane Smith",
            email = "jane@example.com",
            bio = "Math Expert",
            expertise = listOf("Calculus"),
            rating = 4.9f,
            totalSessions = 200,
            profileImageUrl = ""
        )

        assertNotNull(mentor)
        assertEquals("", mentor.profileImageUrl)
    }

    @Test
    fun mentorModelWithDefaultValuesWorks() {
        val mentor = Mentor()

        assertNotNull(mentor)
        assertEquals("", mentor.id)
        assertEquals("", mentor.name)
        assertEquals(0f, mentor.rating, 0.01f)
        assertEquals(0, mentor.totalSessions)
        assertTrue(mentor.expertise.isEmpty())
    }

    @Test
    fun twoMentorObjectsWithSameDataAreEqual() {
        val mentor1 = Mentor(
            id = "mentor789",
            name = "Test Mentor",
            email = "test@example.com",
            bio = "Physics Expert",
            expertise = listOf("Quantum Mechanics"),
            rating = 4.7f,
            totalSessions = 150,
            profileImageUrl = "https://example.com/test.jpg"
        )

        val mentor2 = Mentor(
            id = "mentor789",
            name = "Test Mentor",
            email = "test@example.com",
            bio = "Physics Expert",
            expertise = listOf("Quantum Mechanics"),
            rating = 4.7f,
            totalSessions = 150,
            profileImageUrl = "https://example.com/test.jpg"
        )

        assertEquals(mentor1, mentor2)
    }

    @Test
    fun twoMentorObjectsWithDifferentIdsAreNotEqual() {
        val mentor1 = Mentor(id = "mentor001", name = "Test Mentor")
        val mentor2 = Mentor(id = "mentor002", name = "Test Mentor")

        assertNotEquals(mentor1, mentor2)
    }

    @Test
    fun requestStatusEnumHasCorrectValues() {
        val statuses = RequestStatus.entries

        assertEquals(3, statuses.size)
        assertTrue(statuses.contains(RequestStatus.PENDING))
        assertTrue(statuses.contains(RequestStatus.ACCEPTED))
        assertTrue(statuses.contains(RequestStatus.DECLINED))
    }

    @Test
    fun studentModelCreatedCorrectly() {
        val id = "student123"
        val name = "Alice Wonder"
        val email = "alice@example.com"
        val interests = listOf("Math", "Science")

        val student = Student(
            id = id,
            name = name,
            email = email,
            interests = interests
        )

        assertEquals(id, student.id)
        assertEquals(name, student.name)
        assertEquals(email, student.email)
        assertEquals(interests, student.interests)
    }
}

