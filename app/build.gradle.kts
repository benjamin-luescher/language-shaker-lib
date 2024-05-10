plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "ch.benlu.languageshakerapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "ch.benlu.languageshakerapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

androidComponents {
    onVariants {
        val sourceResDir = "src/main/res/values"
        val targetResDir = "src/main/res/values-zu"

        val sourceStringsXml = file("$sourceResDir/strings.xml")
        val targetStringsXml = file("$targetResDir/strings.xml")

        if (sourceStringsXml.exists()) {
            targetStringsXml.writeText(sourceStringsXml.readText())

            val sourceValues =
                Regex("""<string name="(.+?)">(.+?)</string>""")
                    .findAll(sourceStringsXml.readText())

            val updatedContent =
                sourceValues.fold(targetStringsXml.readText()) { acc, match ->
                    val key = match.groupValues[1]
                    acc.replace(
                        """<string name="$key">(.+?)</string>""".toRegex(),
                        """<string name="$key">$key</string>""",
                    )
                }

            targetStringsXml.writeText(updatedContent)
            println("Updated target file.")
        } else {
            println("Error: strings.xml-file not found in source or target")
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation(project(":languageshakerlib"))
    testImplementation("junit:junit:4.14-SNAPSHOT")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Dagger-Hilt
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.46")
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}

tasks.withType<org.jlleitschuh.gradle.ktlint.tasks.BaseKtLintCheckTask> {
    workerMaxHeapSize.set("512m")
}

apply(plugin = "org.jlleitschuh.gradle.ktlint")

// Optionally configure plugin
configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
    debug.set(true)
    disabledRules.set(setOf("no-wildcard-imports", "final-newline"))
}
