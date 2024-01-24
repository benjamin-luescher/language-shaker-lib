pluginManagement {
    repositories {
        google()
        maven {
            url = uri("https://jitpack.io")
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        maven {
            url = uri("https://jitpack.io")
        }
        mavenCentral()
    }
}

rootProject.name = "Language Shaker App"
include(":app")
include(":languageshakerlib")