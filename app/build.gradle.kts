plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose") //For Kotlin 2.0
    id("com.google.gms.google-services")

}

android {
    namespace = "com.example.budgettracking"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.budgettracking"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11" // Compatible with Kotlin 2.0
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    val composeBom = platform("androidx.compose:compose-bom:2024.05.00")
    implementation(composeBom)
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation("io.coil-kt:coil-compose:2.7.0")
    implementation("io.coil-kt:coil-svg:2.7.0")
    implementation("androidx.compose.ui:ui-text-google-fonts:1.9.4")


    // Compose UI dependencies
    implementation("androidx.activity:activity-compose:1.9.0") //Explicit version compatible with Kotlin 2.0
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.navigation:navigation-compose:2.9.5")
    implementation("androidx.compose.foundation:foundation:1.9.4")
// or newer
    // Tooling for preview/debug
    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation("com.google.firebase:firebase-auth")
    implementation(platform("com.google.firebase:firebase-bom:34.5.0"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")



}