
plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-android-extensions")
    id("kotlin-android")
}

android {
    compileSdkVersion(COMPILE_SDK_VERSION)

    defaultConfig {
        minSdkVersion(MIN_SDK_VERSION)
        targetSdkVersion(TARGET_SDK_VERSION)

        applicationId = APP_ID
        versionCode = APP_VERSION_CODE
        versionName = APP_VERSION_NAME
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
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

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    lintOptions {
        isWarningsAsErrors = true
        isAbortOnError = true
    }

    buildFeatures{
        viewBinding = true
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk7"))
    implementation(project(":core"))
    implementation(project(":home"))
    implementation(project(":di"))
    implementation(project(":download"))
    implementation(project(":settings"))
    implementation(project(":repository"))
    implementation(project(":datasource"))
    implementation(project(":network"))
    implementation(project(":network:nasaapi"))
    implementAndroidDebugLibraries()
    implementAndroidDefaultLibraries()
    implementTestLibraries()
    implementAndroidTestLibraries()
}
