# MentorMatch - Android App

A modern Android application built with Jetpack Compose that connects students with mentors.

## Features

- ✅ User Authentication (Firebase Auth)
- ✅ Role-based Access (Student/Mentor)
- ✅ Profile Management with Image Upload
- ✅ Dark/Light Theme Toggle
- ✅ Settings Page (Edit Profile, Change Password, Notifications)
- ✅ Clean Modern UI
- ✅ Firebase Realtime Database & Firestore Integration
- ✅ Cloud Image Storage (Firebase Storage)

## Tech Stack

- **Language:** Kotlin
- **UI Framework:** Jetpack Compose
- **Architecture:** MVVM
- **Navigation:** Jetpack Navigation Compose
- **Backend:** Firebase (Auth, Firestore, Realtime Database, Storage)
- **Image Loading:** Coil
- **Dependency Injection:** ViewModels with Hilt-ready structure
- **Testing:** JUnit, Espresso, Compose UI Testing

## Project Structure

```
app/src/main/java/com/example/myapplication/
├── components/          # Reusable UI components
│   └── ImageUploadButton.kt
├── models/             # Data models
│   └── User.kt
├── navigation/         # Navigation setup
│   ├── NavigationNew.kt
│   └── Screen.kt
├── pages/              # Screen composables
│   ├── HomePage.kt
│   ├── LoginPage.kt
│   ├── RegisterPage.kt
│   ├── RoleSelectionPage.kt
│   ├── StudentDashboardPage.kt
│   ├── MentorDashboardPage.kt
│   ├── ProfilePage.kt
│   ├── SettingsPage.kt
│   └── ForgotPasswordPage.kt
├── ui/theme/           # Theme and styling
│   ├── Color.kt
│   ├── Theme.kt
│   ├── Type.kt
│   └── ThemeViewModel.kt
├── viewmodels/         # Business logic
│   ├── AuthViewModel.kt
│   └── MentorMatchViewModel.kt
└── MainActivity.kt     # App entry point
```

## Setup Instructions

### Prerequisites
- Android Studio Ladybug or newer
- JDK 11 or higher
- Android SDK API 26+
- Firebase project set up

### Firebase Configuration
1. Create a Firebase project at [Firebase Console](https://console.firebase.google.com/)
2. Add Android app to your Firebase project
3. Download `google-services.json` and place it in `app/` directory
4. Enable Authentication (Email/Password)
5. Set up Firestore Database
6. Set up Realtime Database
7. Set up Firebase Storage

### Build & Run

```bash
# Clone the repository
git clone <your-repo-url>
cd MentorMatch

# Make gradlew executable
chmod +x gradlew

# Clean build
./gradlew clean

# Build the project
./gradlew build

# Run on connected device/emulator
./gradlew installDebug
```

## Running Tests

```bash
# Unit tests
./gradlew test

# Instrumented tests (requires connected device/emulator)
./gradlew connectedAndroidTest
```

## App Flow

1. **Home Screen** → User can choose to Login or Register
2. **Registration** → User creates account and selects role (Student/Mentor)
3. **Login** → User logs in with email/password
4. **Dashboard** → Role-based dashboard (Student or Mentor view)
5. **Profile** → View and edit profile, upload profile picture
6. **Settings** → Edit profile, change password, manage notifications

## Key Features Explained

### Theme Toggle
- Users can switch between light and dark mode
- Theme preference is persisted using ViewModel
- Available in Settings page

### Profile Management
- Users can upload profile pictures
- Images are stored in Firebase Storage
- Profile data synced with Firestore

### Settings Page
- Edit Profile: Update user information
- Change Password: Update account password
- Notifications: Manage notification preferences (coming soon)
- Logout: Sign out from the app

## Dependencies

Key libraries used:
- Jetpack Compose BOM 2024.09.00
- Firebase Auth 24.0.1
- Firebase Firestore 25.1.4
- Firebase Storage 21.0.1
- Navigation Compose 2.9.6
- Coil 2.7.0
- Material Icons Extended
- Coroutines 1.10.2

## Known Issues

None currently.

## Recent Fixes

- ✅ Fixed Kotlin version mismatch (updated to 2.1.0)
- ✅ Removed deprecated Navigation.kt file
- ✅ Fixed unit test compilation errors
- ✅ Cleaned up unused files

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is for educational purposes.

## Support

For issues or questions, please create an issue in the repository.

---

**Last Updated:** March 2026  
**Version:** 1.0.0

