version = ANDROID_LIBRARY_VERSION

plugins {
    id("com.android.library")
    kotlin("android")
    id("kotlin-android-extensions")
    kotlin("kapt")
    id("kotlin-android")
    id("androidx.navigation.safeargs.kotlin")
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

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk7"))
    implementation(project(":core"))
    implementation(project(":nasarepository"))
    implementation(project(":di"))
    implementation(project(":home:public"))
    implementation(project(":model"))
    implementation(EXOPLAYER_CORE_LIBRARY)
    implementation(EXOPLAYER_DASH_LIBRARY)
    implementation(EXOPLAYER_UI_LIBRARY)
    implementAndroidDefaultLibraries()
    implementTestLibraries()
    implementAndroidTestLibraries()
    implementation(ARROW_CORE)
    implementation(COROUTINES)
    implementation(COIL)
    kapt(ARROW_SYNTAX)
    testImplementation(project(":testing"))
    testImplementation(project(":nasarepository:fake"))
    testImplementation(project(":network:fake"))
    testImplementation(project(":home:fake"))
    testImplementation(COROUTINES_TEST)
    testImplementation(ANDROIDX_CORE_TESTING)
}
