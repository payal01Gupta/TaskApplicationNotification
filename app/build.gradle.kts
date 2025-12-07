plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.example.taskapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.taskapplication"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

        implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
        implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
        implementation("com.squareup.retrofit2:retrofit:2.9.0")
        implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation(platform("com.google.firebase:firebase-bom:34.5.0"))
    implementation ("com.google.firebase:firebase-messaging")


    implementation("com.google.firebase:firebase-analytics")
    implementation ("com.google.firebase:firebase-auth")
    implementation ("com.google.firebase:firebase-database")

    implementation("androidx.work:work-runtime:2.7.0")

    implementation("androidx.media:media:1.6.0") // MediaSessionCompat & Notification helper
}