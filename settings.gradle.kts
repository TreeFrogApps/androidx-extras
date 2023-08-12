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
}
