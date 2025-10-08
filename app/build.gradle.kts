plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinCompose)
}

android {
    namespace = "com.orizzonter.app"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.orizzonter.app"
        minSdk = 29
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.coil.compose)
    implementation(libs.compose.material.icons)
    implementation(libs.material)
    implementation(libs.compose.foundation)
    implementation(libs.navigationCompose)
    implementation(libs.material3)
    implementation(libs.material3WindowSizeClass)
    implementation(libs.coreSplashscreen)
    implementation(libs.coreKtx)
    implementation(libs.lifecycleRuntimeKtx)
    implementation(libs.activityCompose)
    implementation(platform(libs.composeBom))
    implementation(libs.composeUi)
    implementation(libs.composeUiGraphics)
    implementation(libs.composeUiToolingPreview)
    debugImplementation(libs.composeUiTooling)
    debugImplementation(libs.composeUiTestManifest)

    testImplementation(libs.junit)
    androidTestImplementation(libs.junitExt)
    androidTestImplementation(libs.espressoCore)
    androidTestImplementation(platform(libs.composeBom))
    androidTestImplementation(libs.composeUiTestJunit4)

    // Networking
    implementation(libs.retrofit)
    implementation(libs.retrofitConverterGson)
    implementation(libs.okhttpLoggingInterceptor)

// Coroutines
    implementation(libs.coroutinesAndroid)

// Lifecycle
    implementation(libs.lifecycleViewmodelKtx)
    implementation(libs.lifecycleRuntimeKtxNew) // Solo si quieres esta versión y no la del alias anterior

// Security Crypto
    implementation(libs.securityCrypto)

// Navigation (ya tienes uno, solo usar este si quieres forzar 2.7.5)
    implementation(libs.navigationComposeLegacy)

}