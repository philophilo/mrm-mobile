# MRM Android Application
[![CircleCI](https://circleci.com/gh/andela/mrm-mobile/tree/chore%2F157688043%2Fintegrate-firebase-in-circleci.svg?style=svg)](https://circleci.com/gh/andela/mrm-mobile/tree/chore%2F157688043%2Fintegrate-firebase-in-circleci) [![Coverage Status](https://coveralls.io/repos/github/andela/mrm-mobile/badge.svg?branch=develop)](https://coveralls.io/github/andela/mrm-mobile?branch=develop) [![Maintainability](https://api.codeclimate.com/v1/badges/a333e35d4f89a6b3283b/maintainability)](https://codeclimate.com/repos/5b164ef55882d702b20005b2/maintainability) [![Test Coverage](https://api.codeclimate.com/v1/badges/a333e35d4f89a6b3283b/test_coverage)](https://codeclimate.com/repos/5b164ef55882d702b20005b2/test_coverage)

The MRM (Meeting Room Management) Android app.

## Project Setup

This project is built with Gradle, the [Android Gradle plugin](http://tools.android.com/tech-docs/new-build-system/user-guide). Follow the steps below to setup the project localy.

* Clone [MRM Android](https://github.com/andela/mrm-mobile) inside your working folder.
* Start Android Studio
* Select "Open Project" and select the generated root Project folder
* You may be prompted with "Unlinked gradle project" -> Select "Import gradle project" and select
the option to use the gradle wrapper
* You may also be prompted to change to the appropriate SDK folder for your local machine
* Once the project has compiled -> run the project!

## Running the application on an emulator or actual device

There can be many ways of running your appplication in android studio.
* First off, lets checkout running on an emulator
    * We shall start by installing the emulator into your android studio.

    Installing the Android SDK manager is the first step. Follow these steps to install the system image.

     1. Start the Android SDK Manager(Start)
     2. Go to SDK manager.
     3. Select the Android version.
     4. click install

    * We shall then proceed to running the emulator:

     1. Open your the project in your android studio.
     2. click the run button in android studio
     3. Proceed to `create new virtual device`
     4. Select the category below:
        - `Tablet`
     5. Proceed to the type of tablet to use and click next.
     6. Select the latest version and the prefered API version.
     7. Click finish.
     8. Now when you click run, you will be able to select the device chosen and proceed to viewing your work on the emulator

     * Installing the google play services on our emulator.
     Follow this link
     https://medium.com/@dai_shi/installing-google-play-services-on-an-android-studio-emulator-fffceb2c28a1

     * While updating the google play services one can follow this link
     https://developers.google.com/android/guides/setup
     http://www.androiddocs.com/google/play-services/setup.html#