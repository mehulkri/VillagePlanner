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

## Improved Capabilities since 2.4

1. Reminders can be more reliably loaded, added, edited, and deleted. In the previous iteration, the reminders were "buggy" according to Jack Jobes
2. Sucessful uploading and storing of profile picture images on Firebase
3. Fixed bugs associated with notifications not popping up
4. Sorting of reminders by arrival time on reminders page
5. Ability to like reminders and have a heart show up on the reminders
6. Colors to indicate reminder status. Red means it has been passed a reminder's arrival time, orange means it is between the arrival time and leave time, and white means it is before the leave time.
7. Homepage now automatically zooms into user location.
8. Account page has been added. This page allows the user to sign out and change their profile picture.
9. Routing now accurately displays the travel time, as calculated by the Google Directions API.
10. Routing also displays the queue time of the store being routed to.
