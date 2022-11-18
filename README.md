# VillagePlanner

VillagePlanner is an Android application that seeks to make the user's experience at the USC Village more convenient. This is achieved through the use of account logins, reminders, and a map able to plan and time routes between stores.

## Installation

1. Download the project and extract it to a directory of your choosing.

2. Open the directory in Android Studio. The recommended version is Android Studio Dolphin | 2021.3.1.

3. Build the app. Sync Gradle. Update everything possible. This app requires the cutting edge of software to run.

4. Open `VillagePlanner\local.properties`. Add the line `MAPS_API_KEY=AIzaSyBe27v3Nkt9BwKJ8h2MyggirRIen7A0eEE` at the bottom.

5. Create your emulator. The recommended API level is API 33. The recommended Android Virtual Device (AVD) is Pixel 2 + Tiramisu 33 (Android 7.0). Ensure you are using the correct version with Google Play enabled (this will be denoted by a triangle Google Play logo next to the device's name when looking through the available devices).

6. Start the emulator. Open it's extended controls (triple dot) and go to your current address. Click, set location. This is necessary since the emulator doesn't know your true location.

7. Run the app and enjoy (if login does not work, use guest login).

Note: If firebase connection is not live, it will not display reminders.