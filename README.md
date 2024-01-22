# Language Shaker Lib
## Description
This is an android library that allows users to easily shake their device to change the language of the app.
When the user shakes the device again, the language is changed back to the original language.

Developer can easily integrate this library into their app and provide a *key-locale*.
The *key-locale* only contains the keys of the strings that should be translated.
The translation provider can then easily see which keys belong to which screen and provide the correct translations.

## Features
Following features can be used by the developer and are configurable (enable/disable):
- Shake to change language (default: enabled)
- Show toast with current language after shake (default: enabled)
- Shake Acceleration (default: 12)
- Shake duration (default: 3000ms)
- Key-locale

## Installation
### Gradle
1. Add the JitPack repository to your build file
    ```gradle
    dependencyResolutionManagement {
        repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
        repositories {
            // ...
            maven { url "https://jitpack.io" }
        }
    }
    ```
2. Add the dependency in your build.gradle file.
    ```gradle
    dependencies {
        implementation 'com.github.benjamin-luescher:language-shaker-lib:1.0.0'
    }
    ```

## Getting Started
### Setup
Create a new `ApplicationClass` (eg `MyApplication`) and extend it from `ApplicationLanguageShakerApplication` like below:
```kotlin
class MyApplication : ApplicationLanguageShakerApplication(
   isActive = BuildConfig.DEBUG, // only active in debug mode
   keyLocale = Locale.forLanguageTag("zu"), // define the "key locale" language
   timeDiff = 3000, // time difference between two shakes in milliseconds
   shakeAcceleration = 12, // shake acceleration
   showToast = false // show toast message when language is changed
) {
    // ...
}
```

Make sure to add the `MyApplication` class to your `AndroidManifest.xml` file within the `application` tag like below:
```xml
 <application
     android:name=".MyShakerApplication">
     <!-- ... -->
 </application>
```
Done! Now you can shake your device to change the language of your app to the defined *key-locale* (eg: `zu`).
When you shake the device again, the language is changed back to the device locale.

### Prepare translations
Make sure you have the latest `strings.xml` file in your project.
Copy your `strings.xml` file from `src/main/res/values` into the `src/main/res/values-<key-locale>` folder.

### Replace the values
Replace the values of the strings with the keys of the strings. Open your `src/main/res/values-<key-locale>/strings.xml` file and replace the values with the keys.
Do this via `Android Studio -> Edit -> Find -> Replace ...` and select `Regular Expression`.
Search for `name="(.*)">([^<]*)` and replace it with `name="$1">$1`.