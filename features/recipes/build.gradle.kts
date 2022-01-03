plugins {
    id("com.android.library")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = 31

    defaultConfig {
        minSdk = Dependencies.Application.minSdk
        targetSdk = Dependencies.Application.targetSdk
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

    buildFeatures.viewBinding = true
    buildFeatures.compose = true

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.1.0-rc01"
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(project(Modules.common))

    implementation(Dependencies.AndroidX.dependencies)
    implementation(Dependencies.Kotlin.dependencies)
    implementation(Dependencies.JetpackCompose.Implementation.dependencies)
    // Remove when all views are build with Jetpack Compose
    implementation(Dependencies.Others.dependencies)

    implementation(Dependencies.DependecyInjection.Implementation.dependencies)
    kapt(Dependencies.DependecyInjection.Kapt.dependencies)

    implementation(Dependencies.Storage.Implementation.dependencies)
    kapt(Dependencies.Storage.Kapt.dependencies)

    androidTestImplementation(Dependencies.Testing.AndroidTestImplementation.dependencies)
    kaptAndroidTest(Dependencies.Testing.KaptAndroidTest.dependencies)

    testImplementation(Dependencies.Testing.TestImplementation.dependencies)
}