plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    `maven-publish`
}

android {
    namespace = "com.treefrogapps.androidx.compose.pager"
    compileSdk = libs.versions.android.compilesdk.get().toInt()
    buildToolsVersion = libs.versions.android.buildtools.get()

    defaultConfig {
        minSdk = libs.versions.android.minsdk.get().toInt()
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
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

    publishing {
        singleVariant(variantName = "release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

publishing {
    val repositoryUri: String by extra

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
            groupId = "com.treefrogapps.androidx.compose"
            artifactId = "compose-pager"
            version = "1.4.0"

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

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.foundation)

    debugImplementation(libs.androidx.compose.ui.tooling)
}