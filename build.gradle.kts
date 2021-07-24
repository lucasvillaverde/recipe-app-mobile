// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.2.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.10")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.38")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        jcenter()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
extra.apply {
    set("minSdkVersion", 21)
    set("targetSdkVersion", 30)
}