@file:Suppress("UnstableApiUsage")

rootProject.name = "androidx-extras"

include(":sample-app")
include(":activity")
include(":compose-activity")
include(":compose-material")
include(":compose-navigation")
include(":compose-pager")
include(":compose-paging")
include(":compose-runtime")
include(":compose-ui")
include(":compose-ui-tooling-preview")
include(":kwork")
include(":paging")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
