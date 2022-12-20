plugins {
    id("com.android.application") version "7.3.1" apply false
    id("com.android.library") version "7.3.1" apply false
    kotlin("android") version "1.7.20" apply false
}

tasks.register("clean", Delete::class.java).configure { delete(project.buildDir) }
