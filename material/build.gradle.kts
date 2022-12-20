plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

val composeCompilerVersion : String by extra

android {
    namespace = "com.treefrogapps.compose.material"
    compileSdk = 33
    buildToolsVersion = "33.0.0"

    defaultConfig {
        minSdk = 24
        targetSdk = 33
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

    buildFeatures {
        compose =  true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = composeCompilerVersion
    }
}



dependencies {

    implementation("androidx.compose.ui:ui:1.3.2")
    implementation("androidx.compose.ui:ui-tooling-preview:1.3.2")
    implementation("androidx.compose.foundation:foundation:1.3.1")
    implementation("androidx.compose.material:material:1.3.1")
    implementation("androidx.window:window:1.0.0")

    debugImplementation("androidx.compose.ui:ui-tooling:1.3.2")
}