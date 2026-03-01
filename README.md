# MentorMatch 🎓

A modern Android application connecting students with mentors for personalized learning experiences.

## 📱 Features

### Authentication
- ✅ User Registration with Firebase Auth
- ✅ Email/Password Login
- ✅ Password Reset
- ✅ Role-based Access (Student/Mentor)

### User Interface
- ✅ Dark/Light Mode Toggle
- ✅ Material Design 3
- ✅ Responsive Layouts
- ✅ Clean & Simplified UI

### Profile Management
- ✅ User Profile Page
- ✅ Profile Image Upload to Firebase Storage
- ✅ Edit User Information
- ✅ Account Settings

### Mentor Matching
- ✅ Browse Available Mentors
- ✅ View Mentor Profiles
- ✅ Send Match Requests
- ✅ Accept/Reject Requests
- ✅ Manage Sessions

### Image Upload
- ✅ Upload Images to Firebase Storage
- ✅ Real-time Upload Progress
- ✅ Image Validation (Size/Type)
- ✅ Organized Storage Structure

## 🛠️ Tech Stack

### Language
- **Kotlin** - Modern Android development

### UI Framework
- **Jetpack Compose** - Declarative UI
- **Material Design 3** - Modern UI components

### Architecture
- **MVVM** - Model-View-ViewModel
- **Clean Architecture** - Separation of concerns
- **Repository Pattern** - Data abstraction

### Libraries

#### Firebase
- `firebase-auth` - Authentication
- `firebase-firestore` - Database
- `firebase-storage` - File storage

#### Jetpack
- `compose-ui` - UI toolkit
- `navigation-compose` - Navigation
- `lifecycle-viewmodel` - State management
- `core-splashscreen` - Splash screen

#### Image Loading
- `coil-compose` - Image loading & caching

#### Networking
- `kotlinx-coroutines` - Async operations

## 📂 Project Structure

```
app/src/main/java/com/example/myapplication/
├── auth/                   # Authentication logic
│   ├── AuthViewModel.kt
│   └── AuthState.kt
├── components/             # Reusable UI components
│   └── ImageUploadButton.kt
├── models/                 # Data models
│   ├── UserRole.kt
│   ├── Mentor.kt
│   └── MentorMatchRequest.kt
├── navigation/             # Navigation setup
│   ├── Navigation.kt
│   └── Screen.kt
├── pages/                  # App screens
│   ├── HomePage.kt
│   ├── LoginPage.kt
│   ├── RegisterPage.kt
│   ├── ProfilePage.kt
│   ├── StudentDashboardPage.kt
│   ├── MentorDashboardPage.kt
│   └── ...
├── ui/theme/              # Theme & styling
│   ├── Color.kt
│   ├── Theme.kt
│   ├── ThemeViewModel.kt
│   └── Typography.kt
├── utils/                 # Utility classes
│   └── ImageUploadHelper.kt
├── viewmodels/            # ViewModels
│   └── MentorMatchViewModel.kt
└── MainActivity.kt        # Entry point
```

## 🚀 Getting Started

### Prerequisites

- **Android Studio** (Arctic Fox or newer)
- **JDK** 11 or higher
- **Android SDK** (API 26+)
- **Firebase Account**

### Setup

1. **Clone the repository**
   ```bash
   git clone <your-repo-url>
   cd MentorMatch
   ```

2. **Open in Android Studio**
   - File → Open → Select MentorMatch folder

3. **Configure Firebase**
   - Create a new Firebase project
   - Add Android app to Firebase
   - Download `google-services.json`
   - Place in `app/` directory

4. **Enable Firebase Services**
   - **Authentication:** Email/Password
   - **Firestore Database**
   - **Storage**

5. **Sync Gradle**
   ```
   File → Sync Project with Gradle Files
   ```

6. **Run the app**
   - Click Run ▶️ or press Shift+F10

## ⚙️ Configuration

### Firebase Storage Rules

```
rules_version = '2';
service firebase.storage {
  match /b/{bucket}/o {
    match /profile_images/{userId}/{imageId} {
      allow read: if true;
      allow write: if request.auth != null && request.auth.uid == userId;
    }
    match /post_images/{userId}/{imageId} {
      allow read: if true;
      allow write: if request.auth != null && request.auth.uid == userId;
    }
  }
}
```

### Firestore Security Rules

```
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /users/{userId} {
      allow read: if true;
      allow write: if request.auth != null && request.auth.uid == userId;
    }
    match /matchRequests/{requestId} {
      allow read: if request.auth != null;
      allow write: if request.auth != null;
    }
  }
}
```

## 🎨 Theming

The app supports both Light and Dark modes:

- **Toggle:** Floating button (bottom-right) or Profile settings
- **Persistence:** Theme persists during app session
- **Colors:** Automatic adaptation for readability

## 📸 Image Upload

Upload images anywhere in the app:

```kotlin
ImageUploadButton(
    imageUrl = profileImageUrl,
    onImageUploaded = { url ->
        // Save URL to database
    },
    size = 120.dp,
    folder = "profile_images"
)
```

Features:
- ✅ Real-time progress tracking
- ✅ Image validation (max 5MB)
- ✅ Error handling
- ✅ Firebase Storage integration

## 🏗️ Build

### Debug Build
```bash
./gradlew assembleDebug
```

### Release Build
```bash
./gradlew assembleRelease
```

### Run Tests
```bash
./gradlew test
```

## 📱 Minimum Requirements

- **Android:** 8.0 (API 26) or higher
- **RAM:** 2GB minimum
- **Storage:** 50MB for app

## 🔐 Security

- ✅ Firebase Authentication
- ✅ Secure password storage
- ✅ User-specific data access
- ✅ Firestore security rules
- ✅ Storage access control

## 🤝 Contributing

1. Fork the repository
2. Create feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open Pull Request

## 📄 License

This project is licensed under the MIT License.

## 👥 Authors

- **Your Name** - Initial work

## 🙏 Acknowledgments

- Firebase for backend services
- Jetpack Compose team
- Material Design guidelines
- Open source community

## 📞 Support

For support, email: support@mentormatch.com

## 🗺️ Roadmap

- [ ] Video call integration
- [ ] In-app messaging
- [ ] Payment integration
- [ ] Mentor reviews & ratings
- [ ] Session scheduling
- [ ] Push notifications
- [ ] Analytics dashboard

---

**Built with ❤️ using Kotlin & Jetpack Compose**

**Version:** 1.0.0  
**Last Updated:** February 28, 2026

