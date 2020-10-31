version = LIBRARY_VERSION

plugins {
    id("java-library")
    kotlin("jvm")
    kotlin("kapt")
    kotlin(SERIALIZATION_PLUGIN)
}

dependencies {
    implementation(kotlin("stdlib-jdk7"))
    implementation(project(":network:apimodel"))
    implementation(project(":network:nasaapi"))
    implementation(KOTLIN_SERIALIZATION)
    implementation(RETROFIT)
    implementation(KOIN_CORE)
    implementation(KOTLIN_SERIALIZATION_ADAPTER)
    implementation(MOCK_RETROFIT)
    implementTestLibraries()
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
