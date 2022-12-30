plugins {
    id("com.android.library")
    kotlin("android")
    `maven-publish`
}

val composeCompilerVersion : String by extra
val repositoryUri : String by extra

android {
    namespace = "com.treefrogapps.compose.navigation"
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

    publishing {
        singleVariant(variantName = "release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

publishing {

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
            groupId = "com.treefrogapps.compose"
            artifactId = "compose-navigation"
            version = "1.0.0"

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}

dependencies {

    implementation("androidx.compose.ui:ui:1.3.2")
    implementation("androidx.compose.ui:ui-tooling-preview:1.3.2")
    implementation("androidx.compose.foundation:foundation:1.3.1")
    implementation("androidx.navigation:navigation-compose:2.5.3")

    debugImplementation("androidx.compose.ui:ui-tooling:1.3.2")
}