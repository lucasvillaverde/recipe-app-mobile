object Dependencies {

    object Application {

    }

    object Network {
        //  API - Retrofit/GSON
        const val gson = "com.google.code.gson:gson:2.8.7"
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
            const val roomRunTime = "androidx.room:room-runtime:2.3.0"
            const val room = "androidx.room:room-ktx:2.3.0"

            val dependencies = arrayListOf(
                roomRunTime,
                room
            )
        }

        object Kapt {
            const val roomCompiler = "androidx.room:room-compiler:2.3.0"

            val dependencies = arrayListOf(
                roomCompiler
            )
        }
    }

    object Kotlin {
        // Kotlin components
        const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.5.20"

        const val androidCoreKtx = "androidx.core:core-ktx:1.6.0"
        const val fragmentKtx = "androidx.fragment:fragment-ktx:1.3.5"

        // Nav component - Kotlin
        const val navigationFragmentKtx =
            "androidx.navigation:navigation-fragment-ktx:2.3.5"
        const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:2.3.5"

        // Kotlin + coroutines
        const val lifecycleRunTimeKtx =
            "androidx.lifecycle:lifecycle-runtime-ktx:2.4.0-alpha02"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1"
        const val coroutinesAndroid =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.0"
        const val livedataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:2.4.0-alpha02"

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
            const val hilt = "com.google.dagger:hilt-android:2.38"

            val dependencies = arrayListOf(
                hilt
            )
        }

        object Kapt {
            const val hiltCompiler = "com.google.dagger:hilt-compiler:2.38"

            val dependencies = arrayListOf(
                hiltCompiler
            )
        }
    }

    object AndroidX {
        const val appCompat = "androidx.appcompat:appcompat:1.3.1"
        const val androidXCore = "androidx.core:core-ktx:1.6.0"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.4"
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
        const val recyclerView = "androidx.recyclerview:recyclerview:1.2.1"

        // Picasso
        const val picasso = "com.squareup.picasso:picasso:2.71828"
        const val picassoTransformation = "jp.wasabeef:picasso-transformations:2.2.1"

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
            const val junit = "androidx.test.ext:junit:1.1.3"

            // Cucumber
            const val cucumber = "io.cucumber:cucumber-android:4.8.3"

            // Espresso
            const val espressoCore = "androidx.test.espresso:espresso-core:3.4.0"
            const val testRules = "androidx.test:rules:1.4.0"
            const val testRunner = "androidx.test:runner:1.4.0"

            // Barista
            const val barista = "com.github.SchibstedSpain:Barista:3.7.0"

            // Hilt
            const val hiltTesting = "com.google.dagger:hilt-android-testing:2.38"

            val dependencies = arrayListOf(
                junit,
                cucumber,
                espressoCore,
                testRules,
                testRunner,
                hiltTesting
            )
        }

        object AndroidTestUtil {
            val orchestrator = "androidx.test:orchestrator:1.4.0"

            val dependencies = arrayListOf(
                orchestrator
            )
        }

        object KaptAndroidTest {
            const val hilt = "com.google.dagger:hilt-android-compiler:2.38"

            val dependencies = arrayListOf(
                hilt
            )
        }

        object TestImplementation {
            const val coreTesting = "androidx.arch.core:core-testing:2.1.0"

            // Roboletric
            const val roboletric = "androidx.test:core:1.4.0"

            // Mockk
            const val mockk = "io.mockk:mockk:1.12.0"

            const val junit = "junit:junit:4.13.2"

            val dependencies = arrayListOf(
                coreTesting,
                roboletric,
                mockk,
                junit
            )
        }
    }
}