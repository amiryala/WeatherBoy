plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    alias(libs.plugins.hilt) // This should work now since you've added it to libs.versions.toml
}

// Add this import at the top
import java.util.Properties
import java.io.FileInputStream

android {
    namespace = "com.lotus.weatherboy"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.lotus.weatherboy"
        minSdk = 21
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Replace the gradleLocalProperties with this approach
        val localProperties = Properties()
        val localPropertiesFile = rootProject.file("local.properties")
        if (localPropertiesFile.exists()) {
            localProperties.load(FileInputStream(localPropertiesFile))
            val apiKey = localProperties.getProperty("WEATHER_API_KEY", "")
            buildConfigField("String", "WEATHER_API_KEY", "\"$apiKey\"")
        } else {
            buildConfigField("String", "WEATHER_API_KEY", "\"\"") // Empty default
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true // Enable BuildConfig
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.6.0" // For Kotlin 2.0.x
    }
}

dependencies {
    // Existing dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Additional dependencies for our weather app
    // Retrofit for API calls
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp.logging)

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)

    // Dagger Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // Location Services
    implementation(libs.play.services.location)

    // Coil for Image Loading (Compose-friendly alternative to Glide)
    implementation(libs.coil.compose)

    // Testing dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}