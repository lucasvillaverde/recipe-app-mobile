plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdkVersion(30)

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    defaultConfig {
        applicationId = "dev.lucasvillaverde.recipeapp"
        testApplicationId = "dev.lucasvillaverde.recipeapp.runner"
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "dev.lucasvillaverde.recipeapp.runner.CucumberTestRunner"
        testInstrumentationRunnerArguments["clearPackageData"] = "true"
    }

    packagingOptions {
        exclude("META-INF/atomicfu.kotlin_module")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures.viewBinding = true

    sourceSets {
        getByName("androidTest").assets.srcDirs("src/androidTest/assets")
    }

    testOptions {
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Dependencies.AndroidX.dependencies)
    implementation(Dependencies.DependecyInjection.Implementation.dependencies)
    implementation(Dependencies.Kotlin.dependencies)
    implementation(Dependencies.Network.dependencies)
    implementation(Dependencies.Storage.Implementation.dependencies)
    implementation(Dependencies.Others.dependencies)

    kapt(Dependencies.DependecyInjection.Kapt.dependencies)
    kapt(Dependencies.Storage.Kapt.dependencies)

    testImplementation(Dependencies.Testing.TestImplementation.dependencies)
    androidTestImplementation(Dependencies.Testing.AndroidTestImplementation.dependencies)
    kaptAndroidTest(Dependencies.Testing.KaptAndroidTest.dependencies)
}

kapt {
    correctErrorTypes = true
}