plugins {
    id("com.android.library")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.3")

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(30)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    implementation(Dependencies.AndroidX.dependencies)
    implementation(Dependencies.Kotlin.dependencies)

    implementation(Dependencies.DependecyInjection.Implementation.dependencies)
    kapt(Dependencies.DependecyInjection.Kapt.dependencies)

    implementation(Dependencies.Storage.Implementation.dependencies)
    kapt(Dependencies.Storage.Kapt.dependencies)

    api(Dependencies.Network.dependencies)
}