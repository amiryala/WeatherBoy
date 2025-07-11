plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("org.jetbrains.kotlin.kapt") version "2.0.21" apply false // Match your Kotlin version
    id("com.google.dagger.hilt.android") version "2.48" apply false
}