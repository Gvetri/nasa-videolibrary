version = ANDROID_LIBRARY_VERSION

plugins {
    id("com.android.library")
    kotlin("android")
    id("kotlin-android-extensions")
    kotlin("kapt")
    kotlin(SERIALIZATION_PLUGIN) version KOTLIN
}

android {
    compileSdkVersion(COMPILE_SDK_VERSION)

    defaultConfig {
        minSdkVersion(MIN_SDK_VERSION)
        targetSdkVersion(TARGET_SDK_VERSION)

        versionCode = LIBRARY_VERSION_CODE
        versionName = LIBRARY_VERSION

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    lintOptions {
        isWarningsAsErrors = true
        isAbortOnError = true
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk7"))
    implementation(project(":core"))
    implementation(project(":model"))
    implementation(project(":datasource"))
    implementation(project(":network"))
    testImplementation(project(":testing"))
    // TODO Remove this dependency when the DI module is ready
    implementation(project(":network:nasaapi"))
    implementation(KOTLIN_SERIALIZATION)
    implementation(OKHTTP)
    implementation(OKHTTP_LOG_INTERCEPTOR)
    implementation(RETROFIT)
    implementation(ARROW_CORE)
    kapt(ARROW_SYNTAX)
    implementAndroidDefaultLibraries()
    implementTestLibraries()
    implementAndroidTestLibraries()
}
