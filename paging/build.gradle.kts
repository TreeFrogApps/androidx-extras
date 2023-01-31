plugins {
    id("com.android.library")
    kotlin("android")
    `maven-publish`
}

android {
    namespace = "com.treefrogapps.androidx.paging"
    compileSdk = 33
    buildToolsVersion = "33.0.0"

    defaultConfig {
        minSdk = 26
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    publishing {
        singleVariant(variantName = "release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

publishing {
    val repositoryUri : String by extra

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri(repositoryUri)
            credentials {
                username = project.findProperty("gpr_user") as String
                password = project.findProperty("gpr_key") as String
            }
        }
    }

    publications {
        register<MavenPublication>(name = "release") {
            groupId = "com.treefrogapps.androidx.paging"
            artifactId = "paging"
            version = "1.1.0"

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}

dependencies {

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.stdlib.common)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.paging.runtime)
}