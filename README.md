# 🐾 PetShopper - Android App

An android extension of the Petshopper app whose backend is in Spring and Kotlin.

## 📱 Overview

PetShopper is a native Android application that connects to a Spring Boot + Kotlin backend, providing a seamless shopping experience for pet owners. Built with the latest Android development tools and following MVVM architecture with Clean Architecture patterns.

---

## 🏗️ Architecture

The app follows **MVVM (Model-View-ViewModel)** with **Clean Architecture** principles:

```
┌─────────────────────────────────────────────────────────────┐
│                         Presentation Layer                   │
│  ┌─────────────┐         ┌──────────────┐                  │
│  │  Jetpack    │ ◄────── │  ViewModel   │                  │
│  │  Compose UI │         │   (State)    │                  │
│  └─────────────┘         └──────────────┘                  │
└─────────────────────────────────────────────────────────────┘
                                  │
                                  ▼
┌─────────────────────────────────────────────────────────────┐
│                         Domain Layer                         │
│  ┌─────────────┐         ┌──────────────┐                  │
│  │  Use Cases  │ ────►   │    Models    │                  │
│  │ (Business)  │         │  (Entities)  │                  │
│  └─────────────┘         └──────────────┘                  │
└─────────────────────────────────────────────────────────────┘
                                  │
                                  ▼
┌─────────────────────────────────────────────────────────────┐
│                          Data Layer                          │
│  ┌─────────────┐    ┌──────────┐    ┌─────────────┐       │
│  │ Repository  │ ◄──│  Mapper  │◄───│     DTO     │       │
│  │    Impl     │    │          │    │  (Network)  │       │
│  └─────────────┘    └──────────┘    └─────────────┘       │
└─────────────────────────────────────────────────────────────┘
```

### Data Flow

```
API Response (DTO) 
    → Mapper 
        → Domain Model 
            → Repository 
                → Use Case 
                    → ViewModel 
                        → UI State 
                            → Compose UI
```

---

## 🛠️ Tech Stack

### Core Technologies
- **Language**: Kotlin
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 36
- **Build System**: Gradle with Kotlin DSL

### Architecture & Patterns
- **MVVM** - Model-View-ViewModel pattern
- **Clean Architecture** - Separation of concerns with layers
- **Repository Pattern** - Abstract data sources
- **Use Cases** - Single responsibility business logic
- **Dependency Injection** - Hilt for DI

### UI & Design
- **Jetpack Compose** - Modern declarative UI toolkit
- **Material 3** - Google's latest design system
- **Navigation Compose** - Type-safe navigation
- **Coil** - Image loading and caching

### Networking & Data
- **Retrofit** - Type-safe HTTP client
- **OkHttp** - HTTP client with interceptors
- **Gson** - JSON serialization/deserialization
- **DataStore** - Modern data storage solution

### Dependency Injection
- **Hilt** - Compile-time dependency injection

### Asynchronous Programming
- **Coroutines** - Kotlin's native async programming
- **Flow** - Reactive streams for state management

---

## 📦 Dependencies

### Core Dependencies
```kotlin
// Jetpack Compose
androidx.compose.bom:2025.10.01
androidx.compose.material3
androidx.navigation.compose:2.9.5

// Dependency Injection
com.google.dagger:hilt-android:2.51.1
androidx.hilt:hilt-navigation-compose:1.3.0

// Networking
com.squareup.retrofit2:retrofit:2.11.0
com.squareup.okhttp3:logging-interceptor:4.12.0
com.google.code.gson:gson:2.11.0

// Image Loading
io.coil-kt:coil-compose:2.7.0

// Local Storage
androidx.datastore:datastore-preferences:1.1.7

// Coroutines
org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2
```

---

## 💡 Architecture Highlights

### Why Clean Architecture?

1. **Separation of Concerns** - Each layer has a single responsibility
2. **Testability** - Business logic is isolated and easy to test
3. **Maintainability** - Changes in one layer don't affect others
4. **Scalability** - Easy to add new features without breaking existing code

### Layer Responsibilities

#### Presentation Layer
- Jetpack Compose UI components
- ViewModels managing UI state
- Navigation logic

#### Domain Layer
- Business logic (Use Cases)
- Domain models (Entities)
- Repository interfaces

#### Data Layer
- Repository implementations
- Network API calls (Retrofit)
- DTOs and Mappers
- Local storage (DataStore)

---

**Built with ❤️ using Kotlin and Jetpack Compose**
