version = ANDROID_LIBRARY_VERSION

plugins {
    id("com.android.library")
    kotlin("android")
    id("kotlin-android-extensions")
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
        disable("VectorPath")
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk7"))
    implementation(project(":repository"))
    implementation(project(":nasarepository"))
    implementation(project(":datasource"))
    implementation(project(":network"))
    implementation(project(":network:nasaapi"))
    implementation(project(":home:public"))
    implementation(project(":core"))
    implementation(KOIN_TEST)
    implementation(OKHTTP)
    implementAndroidDefaultLibraries()
    implementTestLibraries()
    implementAndroidTestLibraries()
}
