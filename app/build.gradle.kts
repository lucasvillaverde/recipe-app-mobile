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
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
    implementation(project(Modules.common))
    implementation(project(Modules.Features.favorite_recipes))
    implementation(project(Modules.Features.recipes))

    implementation(Dependencies.AndroidX.dependencies)
    implementation(Dependencies.Kotlin.dependencies)

    implementation(Dependencies.DependecyInjection.Implementation.dependencies)
    kapt(Dependencies.DependecyInjection.Kapt.dependencies)
}

kapt {
    correctErrorTypes = true
}