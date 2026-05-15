# Namma Shasane

An Android application for managing damage reports and location tracking.

## Project Structure

- **adapter/** - Adapter classes for RecyclerView and other UI components
- **data/** - Data access objects (DAO) and database management
- **model/** - Data models and entity classes
- **ui/** - Activity and Fragment classes for the user interface
- **utils/** - Utility classes and helper functions
- **viewmodel/** - ViewModel classes for MVVM architecture

## Key Features

- Camera integration for damage report documentation
- Map-based location tracking
- User authentication (Login/Create Account)
- Damage report management and listing
- Story view functionality

## Technologies Used

- Kotlin
- Android Architecture Components (ViewModel, Room Database)
- Android Fragments & Activities
- Google Maps API (for MapFragment)
- Camera API

## Getting Started

1. Clone the repository
2. Open the project in Android Studio
3. Build and run the application on an Android device or emulator

## Project Components

### Activities
- `MainActivity` - Main entry point
- `SplashActivity` - Splash screen
- `LoginActivity` - User login
- `CreateAccountActivity` - User account creation
- `CameraActivity` - Camera functionality for damage reports
- `StoryViewActivity` - Story viewing

### Fragments
- `ListFragment` - Display list of reports
- `MapFragment` - Map view for locations
- `AddShasanaFragment` - Add new damage report
- `StoryFragment` - Story management

### Database
- `ShasanaDatabase` - Room database configuration
- `ShasanaDao` - Data access operations
- `ShasanaRepository` - Repository pattern implementation

## License

This project is created for educational purposes.

## Author

Arjun
