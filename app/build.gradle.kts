plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = 31

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    defaultConfig {
        applicationId = "dev.lucasvillaverde.recipeapp"
        minSdk = Dependencies.Application.minSdk
        targetSdk = Dependencies.Application.targetSdk
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    buildFeatures.compose = true

    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.JetpackCompose.jetpackComposeVersion
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
    implementation(Dependencies.JetpackCompose.Implementation.dependencies)
    implementation(Dependencies.Kotlin.dependencies)

    implementation(Dependencies.DependecyInjection.Implementation.dependencies)
    kapt(Dependencies.DependecyInjection.Kapt.dependencies)

    androidTestImplementation(Dependencies.Testing.AndroidTestImplementation.barista) {
        exclude("org.jetbrains.kotlin", "org.jetbrains.kotlin")
    }
    androidTestImplementation(Dependencies.JetpackCompose.AndroidTestImplementation.dependencies)
    androidTestImplementation(Dependencies.Testing.AndroidTestImplementation.dependencies)
    androidTestUtil(Dependencies.Testing.AndroidTestUtil.dependencies)
    debugImplementation(Dependencies.JetpackCompose.DebugImplementation.dependencies)
    kaptAndroidTest(Dependencies.Testing.KaptAndroidTest.dependencies)
}

kapt {
    correctErrorTypes = true
}