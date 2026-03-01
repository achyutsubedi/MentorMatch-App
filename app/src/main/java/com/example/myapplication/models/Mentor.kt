package com.example.myapplication.models

data class Mentor(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val profileImageUrl: String = "",
    val bio: String = "",
    val expertise: List<String> = emptyList(),
    val rating: Float = 0f,
    val totalSessions: Int = 0,
    val yearsOfExperience: Int = 0,
    val hourlyRate: Int = 0,
    val isAvailable: Boolean = true,
    val education: String = "",
    val achievements: List<String> = emptyList()
)

data class Student(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val profileImageUrl: String = "",
    val interests: List<String> = emptyList(),
    val grade: String = "",
    val goals: String = ""
)

data class MatchRequest(
    val id: String = "",
    val studentId: String = "",
    val mentorId: String = "",
    val studentName: String = "",
    val studentEmail: String = "",
    val studentProfileUrl: String = "",
    val subject: String = "",
    val message: String = "",
    val status: RequestStatus = RequestStatus.PENDING,
    val timestamp: Long = System.currentTimeMillis()
)

enum class RequestStatus {
    PENDING,
    ACCEPTED,
    DECLINED
}

data class Session(
    val id: String = "",
    val mentorName: String = "",
    val studentName: String = "",
    val subject: String = "",
    val date: String = "",
    val time: String = "",
    val duration: String = "60 mins",
    val status: SessionStatus = SessionStatus.SCHEDULED
)

enum class SessionStatus {
    SCHEDULED,
    COMPLETED,
    CANCELLED
}

