version = LIBRARY_VERSION

plugins {
    id("java-library")
    kotlin("jvm")
    kotlin("kapt")
    kotlin(SERIALIZATION_PLUGIN) version KOTLIN
}

dependencies {
    implementation(kotlin("stdlib-jdk7"))
    implementation(project(":network"))
    implementation(project(":model"))
    implementation(project(":network:apimodel"))
    implementation(project(":network:nasaapi"))
    implementation(project(":network:fakenasaapi"))
    implementation(project(":datasource"))
    implementation(project(":network:fake"))
    implementation(KOTLIN_SERIALIZATION)
    implementation(RETROFIT)
    implementation(KOTLIN_SERIALIZATION_ADAPTER)
    implementation(MOCK_RETROFIT)
    implementation(ARROW_CORE)
    implementation(OKHTTP)
    implementation(KOIN_CORE)
    implementation(OKHTTP_LOG_INTERCEPTOR)
    kapt(ARROW_SYNTAX)
    implementTestLibraries()
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
