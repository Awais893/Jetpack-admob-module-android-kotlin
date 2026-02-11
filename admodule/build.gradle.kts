plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
    id("org.jetbrains.kotlin.plugin.serialization")

}

android {
    namespace = "com.shadowarm.admodule"
    compileSdk = 36

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    buildFeatures {
        compose = true   // ✅ REQUIRED
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
        // OR use BOM (recommended)
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

    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }
}

dependencies {

    implementation(libs.material)
    implementation(libs.material3)
    implementation(libs.koin.core)
    implementation(libs.androidx.material3.android)
    testImplementation(libs.junit)
    implementation(libs.koin.android)
    implementation(libs.androidx.core.ktx)
    implementation(libs.play.services.ads)
    implementation(libs.androidx.appcompat)
    implementation(libs.koin.androidx.compose)
    implementation(libs.user.messaging.platform)
    androidTestImplementation(libs.androidx.junit)
    implementation(libs.kotlinx.coroutines.android)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.ui)
    implementation(libs.androidx.compose.bom.v2026010101)

    implementation(libs.androidx.compose.ui.ui)
    implementation(libs.androidx.compose.material3.material3)
    implementation(libs.ui.tooling.preview)

    debugImplementation("androidx.compose.ui:ui-tooling")
    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:34.9.0"))

    // Add the dependency for the Firebase SDK for Google Analytics
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-config")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.10.0")
    implementation("androidx.datastore:datastore-preferences:1.2.0")

}