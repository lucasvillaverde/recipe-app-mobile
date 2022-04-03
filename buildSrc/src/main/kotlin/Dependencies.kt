object Dependencies {

    object Application {
        const val minSdk = 23
        const val targetSdk = 31
    }

    object Network {
        //  API - Retrofit/GSON
        const val gson = "com.google.code.gson:gson:2.8.9"
        const val gsonConverter = "com.squareup.retrofit2:converter-gson:2.9.0"
        const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"

        val dependencies = arrayListOf(
            gson,
            gsonConverter,
            retrofit
        )
    }

    object Storage {
        object Implementation {
            // Room components
            const val roomRunTime = "androidx.room:room-runtime:2.4.1"
            const val room = "androidx.room:room-ktx:2.4.1"

            val dependencies = arrayListOf(
                roomRunTime,
                room
            )
        }

        object Kapt {
            const val roomCompiler = "androidx.room:room-compiler:2.4.1"

            val dependencies = arrayListOf(
                roomCompiler
            )
        }
    }

    object Kotlin {
        // Kotlin components
        const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.6.10"

        const val androidCoreKtx = "androidx.core:core-ktx:1.8.0-alpha03"
        const val fragmentKtx = "androidx.fragment:fragment-ktx:1.5.0-alpha01"

        // Nav component - Kotlin
        const val navigationFragmentKtx =
            "androidx.navigation:navigation-fragment-ktx:2.5.0-alpha01"
        const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:2.5.0-alpha01"

        // Kotlin + coroutines
        const val lifecycleRunTimeKtx =
            "androidx.lifecycle:lifecycle-runtime-ktx:2.5.0-alpha01"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0"
        const val coroutinesAndroid =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0"
        const val livedataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:2.5.0-alpha01"

        val dependencies = arrayListOf(
            kotlinStdLib,
            androidCoreKtx,
            fragmentKtx,
            navigationFragmentKtx,
            navigationUiKtx,
            lifecycleRunTimeKtx,
            coroutines,
            coroutinesAndroid,
            livedataKtx
        )
    }

    object DependecyInjection {
        object Implementation {
            // Dagger - Hilt
            const val hilt = "com.google.dagger:hilt-android:2.40.5"
            const val composeNavigation = "androidx.hilt:hilt-navigation-compose:1.0.0"

            val dependencies = arrayListOf(
                hilt,
                composeNavigation
            )
        }

        object Kapt {
            const val hiltCompiler = "com.google.dagger:hilt-compiler:2.40.5"

            val dependencies = arrayListOf(
                hiltCompiler
            )
        }
    }

    object AndroidX {
        const val appCompat = "androidx.appcompat:appcompat:1.4.1"
        const val androidXCore = "androidx.core:core-ktx:1.6.10"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.3"
        const val androidXLegacy = "androidx.legacy:legacy-support-v4:1.0.0"

        val dependencies = arrayListOf(
            appCompat,
            androidXCore,
            constraintLayout,
            androidXLegacy
        )
    }

    object Others {
        // RecyclerView
        const val recyclerView = "androidx.recyclerview:recyclerview:1.3.0-alpha01"

        // Picasso
        const val picasso = "com.squareup.picasso:picasso:2.71828"
        const val picassoTransformation = "jp.wasabeef:picasso-transformations:2.4.0"

        // Material
        const val materialDesign = "com.google.android.material:material:1.4.0"

        val dependencies = arrayListOf(
            recyclerView,
            picasso,
            picassoTransformation,
            materialDesign
        )
    }

    object Testing {
        object AndroidTestImplementation {
            const val junit = "androidx.test.ext:junit:1.1.4-alpha03"

            // Espresso
            const val espressoCore = "androidx.test.espresso:espresso-core:3.5.0-alpha03"
            const val testRules = "androidx.test:rules:1.4.1-alpha03"
            const val testRunner = "androidx.test:runner:1.4.1-alpha03"

            // Barista
            const val barista = "com.github.SchibstedSpain:Barista:3.10.0"

            // Hilt
            const val hiltTesting = "com.google.dagger:hilt-android-testing:2.40.5"

            val dependencies = arrayListOf(
                junit,
                espressoCore,
                testRules,
                testRunner,
                hiltTesting
            )
        }

        object AndroidTestUtil {
            val orchestrator = "androidx.test:orchestrator:1.4.1"

            val dependencies = arrayListOf(
                orchestrator
            )
        }

        object KaptAndroidTest {
            const val hilt = "com.google.dagger:hilt-android-compiler:2.40.5"

            val dependencies = arrayListOf(
                hilt
            )
        }

        object TestImplementation {
            const val coreTesting = "androidx.arch.core:core-testing:2.1.0"

            // Roboletric
            const val roboletric = "androidx.test:core:1.4.1-alpha03"

            // Mockk
            const val mockk = "io.mockk:mockk:1.12.2"

            const val junit = "junit:junit:4.13.2"

            val dependencies = arrayListOf(
                coreTesting,
                roboletric,
                mockk,
                junit
            )
        }
    }

    object JetpackCompose {
        const val jetpackComposeVersion = "1.2.0-alpha02"
        const val jetpackComposeNavigationVersion = "2.4.1"

        object Implementation {
            // Integration with activities
            val activityCompose = "androidx.activity:activity-compose:1.5.0-alpha01"

            // Compose Material Design
            val composeMaterial = "androidx.compose.material:material:$jetpackComposeVersion"

            // Animations
            val composeAnimation = "androidx.compose.animation:animation:$jetpackComposeVersion"

            // Tooling support (Previews, etc.)
            val composeUi = "androidx.compose.ui:ui-tooling:$jetpackComposeVersion"

            // LiveData
            val livedata = "androidx.compose.runtime:runtime-livedata:$jetpackComposeVersion"

            // Integration with ViewModels
            val lifecycleCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:2.5.0-alpha01"

            val navigation = "androidx.navigation:navigation-compose:$jetpackComposeNavigationVersion"

            // Coil
            val coil = "io.coil-kt:coil-compose:2.0.0-alpha06"

            val swipeRefresh = "com.google.accompanist:accompanist-swiperefresh:0.24.0-alpha"

            val dependencies = listOf(
                activityCompose,
                composeMaterial,
                composeAnimation,
                composeUi,
                lifecycleCompose,
                livedata,
                coil,
                swipeRefresh
            )
        }

        object AndroidTestImplementation {
            // UI Tests
            val composeUiTest = "androidx.compose.ui:ui-test-junit4:$jetpackComposeVersion"

            val dependencies = listOf(
                composeUiTest
            )
        }

        object DebugImplementation {
            val composeUiTest = "androidx.compose.ui:ui-test-manifest:$jetpackComposeVersion"

            val dependencies = listOf(
                composeUiTest
            )
        }
    }
}