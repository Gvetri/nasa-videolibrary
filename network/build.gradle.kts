version = LIBRARY_VERSION

plugins {
    id("java-library")
    kotlin("jvm")
    kotlin(SERIALIZATION_PLUGIN) version KOTLIN
    kotlin("kapt")
}

dependencies {
    implementation(kotlin("stdlib-jdk7"))
    implementation(project(":datasource"))
    implementation(project(":model"))
    implementation(project(":network:apimodel"))
    implementation(project(":network:nasaapi"))
    implementation(KOTLIN_SERIALIZATION)
    implementation(RETROFIT)
    implementation(KOTLIN_SERIALIZATION_ADAPTER)
    implementation(KOIN_FRAGMENT)
    implementation(KOIN_VIEWMODEL)
    implementation(OKHTTP)
    implementation(OKHTTP_LOG_INTERCEPTOR)
    implementation(COROUTINES)
    implementation(ARROW_CORE)
    implementation(MOCK_RETROFIT)
    kapt(ARROW_SYNTAX)
    implementTestLibraries()
    testImplementation(project(":testing"))
    testImplementation(project(":network:apimodel"))
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
