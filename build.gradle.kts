plugins {
    id("com.android.application") version "7.4.0" apply false
    id("com.android.library") version "7.4.0" apply false
    kotlin("android") version "1.8.0" apply false
}

tasks.register("clean", Delete::class.java).configure { delete(project.buildDir) }
