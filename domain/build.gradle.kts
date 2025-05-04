plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization") version "2.0.21"
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
    id("androidx.room")
}

android {
    namespace = "com.example.domain"
    compileSdk = 35
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = 26
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    room {
        schemaDirectory("$projectDir/schemas")
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    testImplementation(libs.junit)

    // serialization
    implementation(libs.kotlinx.serialization.json)

    // hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    // circuit
    implementation(libs.circuit.foundation)
    implementation(libs.circuit.backstack)
    implementation(libs.circuit.runtime)
    implementation(libs.circuit.runtime.presenter)
    implementation(libs.circuit.runtime.ui)
    implementation(libs.circuit.codegen.annotations)
    ksp(libs.circuit.codegen)
}
