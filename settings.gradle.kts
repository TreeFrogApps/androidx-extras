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
include(":kwork")
include(":paging")

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

    versionCatalogs {
        create("libs") {

            version("coroutines", "1.6.4")
            version("kotlin", "1.8.10")
            version("compose-ui", "1.4.1")
            version("compose-compiler", "1.4.6")

            library("kotlinx-coroutines-android", "org.jetbrains.kotlinx", "kotlinx-coroutines-android").versionRef("coroutines")
            library("kotlinx-coroutines-core", "org.jetbrains.kotlinx", "kotlinx-coroutines-core").versionRef("coroutines")
            library("kotlin-stdlib", "org.jetbrains.kotlin", "kotlin-stdlib").versionRef("kotlin")
            library("kotlin-stdlib-common", "org.jetbrains.kotlin", "kotlin-stdlib-common").versionRef("kotlin")

            library("androidx-activity-ktx", "androidx.activity:activity-ktx:1.6.1")
            library("androidx-core-ktx", "androidx.core:core-ktx:1.9.0")
            library("androidx-lifecycle-ktx", "androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
            library("androidx-workmanager-ktx", "androidx.work:work-runtime-ktx:2.7.1")
            library("androidx-window", "androidx.window:window:1.0.0")
            library("androidx-paging-runtime", "androidx.paging:paging-runtime:3.2.0-alpha04")
            library("androidx-activity-compose", "androidx.activity:activity-compose:1.6.1")
            library("androidx-navigation-compose", "androidx.navigation:navigation-compose:2.5.3")
            library("androidx-compose-material", "androidx.compose.material:material:1.4.2")
            library("androidx-compose-foundation", "androidx.compose.foundation:foundation:1.4.2")
            library("androidx-compose-runtime", "androidx.compose.runtime:runtime:1.4.2")
            library("androidx-paging-compose", "androidx.paging:paging-compose:1.0.0-alpha18")
            library("androidx-compose-ui", "androidx.compose.ui", "ui").versionRef("compose-ui")
            library("androidx-compose-ui-tooling", "androidx.compose.ui", "ui-tooling").versionRef("compose-ui")
            library("androidx-compose-ui-tooling-preview", "androidx.compose.ui", "ui-tooling-preview").versionRef("compose-ui")
            library("androidx-compose-ui-test", "androidx.compose.ui", "ui-test-junit4").versionRef("compose-ui")
            library("androidx-compose-ui-test-manifest", "androidx.compose.ui", "ui-test-manifest").versionRef("compose-ui")
            library("androidx-test-junit", "androidx.test.ext:junit:1.1.4")
            library("androidx-test-espresso", "androidx.test.espresso:espresso-core:3.5.0")

            library("chrisbanes-snapper", "dev.chrisbanes.snapper:snapper:0.3.0")

            library("junit", "junit:junit:4.13.2")
        }
    }
}
