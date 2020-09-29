version = LIBRARY_VERSION

plugins {
    id("java-library")
    kotlin("jvm")
    kotlin("plugin.serialization") version "1.4.0"
    kotlin("kapt")
}

dependencies {
    implementation(kotlin("stdlib-jdk7"))
    implementation(project(":datasource"))
    implementation(project(":model"))
    implementation(KOTLIN_SERIALIZATION)
    implementation(RETROFIT)
    implementation(KOTLIN_SERIALIZATION_ADAPTER)
    implementation("io.arrow-kt:arrow-core:$ARROW_VERSION")
    implementation("io.arrow-kt:arrow-syntax:$ARROW_VERSION")
    kapt("io.arrow-kt:arrow-syntax:$ARROW_VERSION")
    implementTestLibraries()
    implementation("com.squareup.retrofit2:retrofit-mock:$RETROFIT_VERSION")
    testImplementation(project(":testing"))
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
