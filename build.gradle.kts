buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.6")
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
    id ("com.android.library") version "8.2.0" apply false
    id("com.google.devtools.ksp") version "1.9.0-1.0.13" apply false

}