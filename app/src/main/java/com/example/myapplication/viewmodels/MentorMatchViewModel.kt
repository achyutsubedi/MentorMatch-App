package com.example.myapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MentorMatchViewModel : ViewModel() {

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    private val _userRole = MutableStateFlow<UserRole>(UserRole.STUDENT)
    val userRole: StateFlow<UserRole> = _userRole.asStateFlow()

    private val _mentors = MutableStateFlow<List<Mentor>>(emptyList())
    val mentors: StateFlow<List<Mentor>> = _mentors.asStateFlow()

    private val _matchRequests = MutableStateFlow<List<MatchRequest>>(emptyList())
    val matchRequests: StateFlow<List<MatchRequest>> = _matchRequests.asStateFlow()

    private val _upcomingSessions = MutableStateFlow<List<Session>>(emptyList())
    val upcomingSessions: StateFlow<List<Session>> = _upcomingSessions.asStateFlow()

    init {
        loadSampleData()
    }

    fun setUserRole(role: UserRole) {
        _userRole.value = role
    }

    fun setCurrentUser(user: User) {
        _currentUser.value = user
    }

    private fun loadSampleData() {
        viewModelScope.launch {
            // Sample mentors data
            _mentors.value = listOf(
                Mentor(
                    id = "1",
                    name = "Dr. Kathryn Murphy",
                    email = "kathryn@mentormatch.com",
                    bio = "Expert in Quantum Physics with 15+ years of teaching experience. Specialized in NEB Class 11 & 12 curriculum.",
                    expertise = listOf("Physics", "Quantum Mechanics", "Thermodynamics", "Electronics"),
                    rating = 4.9f,
                    totalSessions = 1250,
                    yearsOfExperience = 15,
                    hourlyRate = 1500,
                    isAvailable = true,
                    education = "PhD in Physics, MIT",
                    achievements = listOf("Top Rated Mentor 2025", "100+ Students Cleared Entrance")
                ),
                Mentor(
                    id = "2",
                    name = "Arjun Sharma",
                    email = "arjun@mentormatch.com",
                    bio = "Mathematics wizard specializing in Calculus, Trigonometry, and Algebra. Helping students ace board exams.",
                    expertise = listOf("Mathematics", "Calculus", "Trigonometry", "Algebra", "Statistics"),
                    rating = 5.0f,
                    totalSessions = 2100,
                    yearsOfExperience = 12,
                    hourlyRate = 1200,
                    isAvailable = true,
                    education = "M.Sc Mathematics, Oxford",
                    achievements = listOf("Perfect 5.0 Rating", "2000+ Sessions Completed")
                ),
                Mentor(
                    id = "3",
                    name = "Dr. Sneha Rao",
                    email = "sneha@mentormatch.com",
                    bio = "Biology expert with focus on Botany & Zoology. Extensive experience with medical entrance preparation.",
                    expertise = listOf("Biology", "Botany", "Zoology", "Genetics", "Ecology"),
                    rating = 4.8f,
                    totalSessions = 950,
                    yearsOfExperience = 10,
                    hourlyRate = 1400,
                    isAvailable = true,
                    education = "PhD in Biology, Stanford",
                    achievements = listOf("Medical Entrance Expert", "Top 10 Mentor")
                ),
                Mentor(
                    id = "4",
                    name = "Rajesh Kumar",
                    email = "rajesh@mentormatch.com",
                    bio = "Chemistry enthusiast with expertise in Organic, Inorganic, and Physical Chemistry.",
                    expertise = listOf("Chemistry", "Organic Chemistry", "Physical Chemistry", "Lab Techniques"),
                    rating = 4.7f,
                    totalSessions = 780,
                    yearsOfExperience = 8,
                    hourlyRate = 1100,
                    isAvailable = true,
                    education = "M.Sc Chemistry, Cambridge",
                    achievements = listOf("Best Chemistry Mentor 2024")
                ),
                Mentor(
                    id = "5",
                    name = "Priya Patel",
                    email = "priya@mentormatch.com",
                    bio = "English Language and Literature specialist. Expert in grammar, creative writing, and exam preparation.",
                    expertise = listOf("English", "Literature", "Grammar", "Creative Writing", "Communication"),
                    rating = 4.9f,
                    totalSessions = 1100,
                    yearsOfExperience = 11,
                    hourlyRate = 1000,
                    isAvailable = false,
                    education = "MA English Literature, Yale",
                    achievements = listOf("Published Author", "Communication Expert")
                )
            )

            // Sample match requests for mentors
            _matchRequests.value = listOf(
                MatchRequest(
                    id = "req1",
                    studentId = "s1",
                    mentorId = "1",
                    studentName = "Amit Thapa",
                    studentEmail = "amit@student.com",
                    subject = "Quantum Physics",
                    message = "I need help preparing for my upcoming board exams in Physics. Particularly struggling with Quantum Mechanics concepts.",
                    status = RequestStatus.PENDING
                ),
                MatchRequest(
                    id = "req2",
                    studentId = "s2",
                    mentorId = "1",
                    studentName = "Sita Sharma",
                    studentEmail = "sita@student.com",
                    subject = "Thermodynamics",
                    message = "Looking for guidance in Thermodynamics and wave mechanics for entrance exam preparation.",
                    status = RequestStatus.PENDING
                ),
                MatchRequest(
                    id = "req3",
                    studentId = "s3",
                    mentorId = "1",
                    studentName = "Hari Prasad",
                    studentEmail = "hari@student.com",
                    subject = "General Physics",
                    message = "Need comprehensive support for NEB Class 12 Physics curriculum.",
                    status = RequestStatus.PENDING
                )
            )

            // Sample upcoming sessions
            _upcomingSessions.value = listOf(
                Session(
                    id = "sess1",
                    mentorName = "Dr. Kathryn Murphy",
                    studentName = "Amit Thapa",
                    subject = "Quantum Physics",
                    date = "Today",
                    time = "4:00 PM",
                    duration = "60 mins"
                ),
                Session(
                    id = "sess2",
                    mentorName = "Arjun Sharma",
                    studentName = "Sita Sharma",
                    subject = "Calculus",
                    date = "Tomorrow",
                    time = "10:00 AM",
                    duration = "90 mins"
                ),
                Session(
                    id = "sess3",
                    mentorName = "Dr. Sneha Rao",
                    studentName = "Hari Prasad",
                    subject = "Botany",
                    date = "Mar 1",
                    time = "2:00 PM",
                    duration = "60 mins"
                )
            )
        }
    }

    fun requestMatch(mentorId: String, subject: String, message: String) {
        viewModelScope.launch {
            val newRequest = MatchRequest(
                id = "req_${System.currentTimeMillis()}",
                studentId = _currentUser.value?.id ?: "student_001",
                mentorId = mentorId,
                studentName = _currentUser.value?.name ?: "Student",
                studentEmail = _currentUser.value?.email ?: "",
                subject = subject,
                message = message,
                status = RequestStatus.PENDING
            )
            // In real app, this would be saved to Firebase/backend
            // For now, we'll just add it to the list
            _matchRequests.value = _matchRequests.value + newRequest
        }
    }

    fun acceptRequest(requestId: String) {
        viewModelScope.launch {
            _matchRequests.value = _matchRequests.value.map { request ->
                if (request.id == requestId) {
                    request.copy(status = RequestStatus.ACCEPTED)
                } else {
                    request
                }
            }
        }
    }

    fun declineRequest(requestId: String) {
        viewModelScope.launch {
            _matchRequests.value = _matchRequests.value.map { request ->
                if (request.id == requestId) {
                    request.copy(status = RequestStatus.DECLINED)
                } else {
                    request
                }
            }
        }
    }

    fun getPendingRequests(): List<MatchRequest> {
        return _matchRequests.value.filter { it.status == RequestStatus.PENDING }
    }

    fun getAcceptedRequests(): List<MatchRequest> {
        return _matchRequests.value.filter { it.status == RequestStatus.ACCEPTED }
    }
}

