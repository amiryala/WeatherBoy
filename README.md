# WeatherBoy

## Overview

WeatherBoy is a modern Android weather application built with Jetpack Compose that demonstrates clean architecture, modern Android development practices, and a polished user interface. The app provides real-time weather information based on the user's current location or any searched city.

## Features

- **Location-Based Weather**: Automatically detects and displays weather for the user's current location
- **City Search**: Allows users to search for weather information in any city globally
- **Temperature Unit Toggle**: Easily switch between Celsius and Fahrenheit
- **Detailed Weather Information**: Displays temperature, "feels like" temperature, humidity, wind speed, sunrise, and sunset times
- **Animated Splash Screen**: Features custom animations showcasing weather elements
- **Responsive Design**: Adapts to different screen sizes and orientations
- **Dark Theme**: Modern UI with a sleek dark theme for better visibility and reduced eye strain

## Technology Stack

- **Kotlin**: 100% Kotlin codebase
- **Jetpack Compose**: Modern declarative UI toolkit for building native Android UI
- **MVVM Architecture**: Clean separation of concerns with ViewModel and Repository pattern
- **Coroutines & Flow**: For asynchronous programming
- **Hilt**: For dependency injection
- **Retrofit**: For networking and API calls
- **OpenWeatherMap API**: For weather data
- **Google Location Services**: For accurate location detection
- **Material 3 Design**: Following latest Material Design guidelines

## Architecture

WeatherBoy follows Clean Architecture principles with these main components:

- **Data Layer**: Contains the API service, repository implementation, and data models
- **Domain Layer**: Contains the repository interfaces and domain models
- **Presentation Layer**: Contains the UI components, ViewModels, and state management

## Future Enhancements

- Weather forecasts for upcoming days
- Weather notifications
- Widgets for home screen
- Additional weather metrics
- User preferences for startup behavior
- Light theme option

## License

This project is licensed under the MIT License - see the LICENSE file for details.
