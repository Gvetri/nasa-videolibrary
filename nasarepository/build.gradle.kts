version = LIBRARY_VERSION

plugins {
    id("java-library")
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib-jdk7"))
    implementation(project(":model"))
    implementation(OKHTTP)
    implementation(ARROW_CORE)
    implementTestLibraries()
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
