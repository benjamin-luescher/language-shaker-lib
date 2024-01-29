# Language Shaker Lib
[![](https://jitpack.io/v/benjamin-luescher/language-shaker-lib.svg)](https://jitpack.io/#benjamin-luescher/language-shaker-lib)

## Description
This is an android library that allows users to easily shake their device to change the language of the app.
When the user shakes the device again, the language is changed back to the original language.

Developer can easily integrate this library into their app and provide a *key-locale*.
The *key-locale* only contains the keys of the strings that should be translated.
The translation provider can then easily see which keys belong to which screen and provide the correct translations.

![Device Locale (before shake)](/screenshots/jpg/before.jpeg "Device Locale (before shake)")
![Key Locale (after shake)](/screenshots/jpg/after.jpeg "Key Locale (after shake)")

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
        implementation 'com.github.benjamin-luescher:language-shaker-lib:1.0.2'
    }
    ```

## Getting Started
### Extend Application Class
Create a new `ApplicationClass` (eg `MyApplication`) and extend it from `ApplicationLanguageShakerApplication` like below:
```kotlin
class MyApplication : LanguageShakerApplication(
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

### Replace Translations
You have to replace the translations of your app with the keys of the strings. This can be done manually or automatically.
Automatically is recommended because it is faster and less error-prone and the configuration is only done once.

#### Automatically
In your module’s `build.gradle` file add the following snippet and replace keyLocale value with your keyLocale, 
defined as parameter in `LanguageShakerApplication`.
```gradle
plugins {
    // ...
}
android {
    // ...
}

// :::::::::::::::::::::::::::::::::::::
// ::::: LANGUAGE SHAKER LIB COPY ::::::
// :::::::::::::::::::::::::::::::::::::

val keyLocale = "zu" // <-- replace with your key-locale
val targetResDir = "src/main/res/values-$keyLocale"

androidComponents {
    onVariants(selector().withBuildType("debug")) {
        // prepare key locale for debug builds
        prepareKeyLocale()
    }
}

/**
 * This task copies the source strings.xml to the target strings.xml
 * and replaces the values with the key.
 */
fun prepareKeyLocale() {
    val sourceResDir = "src/main/res/values"

    val sourceStringsXml = file("$sourceResDir/strings.xml")
    val targetStringsXml = file("$targetResDir/strings.xml")

    // create targetResDir if not exists
    val targetResDir = file(targetResDir)
    if (!targetResDir.exists()) {
        targetResDir.mkdirs()
    }

    // create targetStringsXml if not exists
    if (!targetStringsXml.exists()) {
        targetStringsXml.createNewFile()
    }

    if (sourceStringsXml.exists()) {
        targetStringsXml.writeText(sourceStringsXml.readText())

        val sourceValues = Regex("""<string name="(.+?)">(.+?)</string>""")
            .findAll(sourceStringsXml.readText())

        val updatedContent = sourceValues.fold(targetStringsXml.readText()) { acc, match ->
            val key = match.groupValues[1]
            acc.replace(
                """<string name="$key">(.+?)</string>""".toRegex(),
                """<string name="$key">$key</string>"""
            )
        }

        targetStringsXml.writeText(updatedContent)
        println("Updated target file.")
    } else {
        println("Error: strings.xml-file not found in source or target")
    }
}
```
Your `strings.xml` file in your *key-locale* folder will now be automatically updated with the
keys of the strings every time you build your app.

#### Manually
1. Prepare Translations
   - Make sure you have the latest `strings.xml` file in your project.
   - Copy your `strings.xml` file from `src/main/res/values` into the `src/main/res/values-<key-locale>` folder.
2. Replace the values
   - Replace the values of the strings with the keys of the strings. Open your `src/main/res/values-<key-locale>/strings.xml` file and replace the values with the keys.
   - Do this via `Android Studio -> Edit -> Find -> Replace ...` and select `Regular Expression`. Search for `name="(.*)">([^<]*)` and replace it with `name="$1">$1`.