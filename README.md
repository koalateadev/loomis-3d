# Loomis 3D

An Android app that helps artists practice drawing the human head using the Loomis method on the go.

## Overview

Loomis 3D is a mobile learning tool for artists who want to master drawing the human head. The app provides interactive 3D models that demonstrate different simplification stages of the human head based on Andrew Loomis's famous drawing method. By overlaying these models over your device's camera view, you can practice your drawing technique anywhere, anytime.

## Features

- **Five Progressive Models**: View different levels of head simplification, from basic geometric forms to more detailed representations
- **3D Model Manipulation**: Freely rotate, scale, and reposition the 3D head model to match any angle or perspective
- **Camera Integration**: Overlay the 3D model on your camera feed to use as a reference while drawing
- **Intuitive Controls**: 
  - Model selection to switch between different Loomis simplification stages
  - Rotation controls for finding the perfect angle
  - Scale adjustment to match your reference size
  - Position controls for precise placement
  - Camera controls for adjusting the view
  - Lock/unlock feature to prevent accidental movements
- **Collapsible UI**: Minimalist interface that can be expanded or collapsed for an unobstructed view

## Screenshots

_Screenshots will be added in future updates_

## Requirements

- **Minimum Android SDK**: 26 (Android 8.0 Oreo)
- **Target Android SDK**: 33 (Android 13)
- **Camera Permission**: Required for camera overlay functionality

## Installation

### From Source

1. Clone the repository:
   ```bash
   git clone https://github.com/koalateadev/loomis-3d.git
   cd loomis-3d
   ```

2. Open the project in Android Studio (Arctic Fox or newer recommended)

3. Build and run the project on your Android device or emulator:
   ```bash
   ./gradlew assembleDebug
   ```

## Usage

1. **Launch the app** - Grant camera permission when prompted
2. **Select a model** - Tap the model selection button to choose from five different Loomis head simplifications
3. **Position the model** - Use the position controls to move the model to your desired location
4. **Rotate the head** - Adjust the rotation to match the angle you want to practice drawing
5. **Scale as needed** - Resize the model to fit your reference
6. **Lock when ready** - Use the lock button to prevent accidental touches while drawing
7. **Toggle controls** - Tap the floating action button to show/hide the control panel

### Control Buttons

- **Model Selection**: Switch between the five different head model simplifications
- **Position**: Move the model in 3D space
- **Rotation**: Rotate the model along different axes
- **Scale**: Resize the model
- **Camera**: Adjust camera settings
- **Lock/Unlock**: Prevent or allow model manipulation

## The Loomis Method

The Loomis method, developed by Andrew Loomis, is a widely-used approach for drawing the human head. It breaks down the complex form of the head into simple geometric shapes, making it easier for artists to understand proportions and construct heads from any angle. This app provides visual references of these simplification stages to help you practice and internalize the method.

## Technical Details

### Built With

- **Kotlin** - Primary programming language
- **Android Jetpack**:
  - ViewModel and LiveData for MVVM architecture
  - Data Binding and View Binding
  - Fragment KTX
  - Camera2 and CameraX for camera functionality
- **Sceneform** - 3D rendering and model manipulation
- **Dagger Hilt** - Dependency injection
- **Material Design Components** - UI elements

### Architecture

The app follows the MVVM (Model-View-ViewModel) architectural pattern with:
- `MainActivity`: Host activity managing fragments
- `MainViewModel`: Central state management for the app
- Display fragments for camera and 3D model rendering
- Control fragments for user interactions
- 3D models stored as GLB files in the raw resources folder

### 3D Models

The app includes five GLB (GL Transmission Format) models representing different stages of head simplification according to the Loomis method, ranging from basic geometric shapes to more refined forms.

## Contributing

Contributions are welcome! If you'd like to contribute:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is available for educational purposes. Please check with the repository owner for specific licensing terms.

## Acknowledgments

- Andrew Loomis for the fundamental drawing method
- The artist community for inspiration and feedback
- Contributors to the Sceneform library for 3D rendering capabilities

## Contact

For questions, suggestions, or feedback, please open an issue on the GitHub repository.

---

**Happy Drawing! 🎨**
