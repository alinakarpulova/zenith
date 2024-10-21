plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.zenith"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.zenith"
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

    sourceSets{
        getByName("main"){
            res.srcDirs(
                "src/main/res/layouts/exercises",
                "src/main/res/layouts/general",
                "src/main/res/layouts/home",
                "src/main/res/layouts/workouts",
                "src/main/res/layouts/statistics",
                "src/main/res/layouts",
                "src/main/res",
            )
        }
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
    implementation("com.jakewharton.threetenabp:threetenabp:1.3.1")
    implementation(libs.glide)
    annotationProcessor(libs.compiler)
}