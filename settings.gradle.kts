pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
    //plugins {
      //  id("com.android.application") version "8.1.0"
       // id("org.jetbrains.kotlin.android") version "1.9.22"
   // }
}

rootProject.name = "Calculador1.1"
include(":app")
 