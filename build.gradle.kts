plugins {
    id("com.android.application") version AGP apply false
    id("com.android.library") version AGP apply false
    kotlin("android") version KOTLIN apply false
    id("io.gitlab.arturbosch.detekt") version DETEKT
    id("org.jlleitschuh.gradle.ktlint") version KTLINT_PLUGIN
    id("com.github.ben-manes.versions") version VERSIONS_PLUGIN
}

allprojects {
    repositories {
        google()
        mavenCentral()
        jcenter()
        maven { setUrl("https://dl.bintray.com/arrow-kt/arrow-kt/") }
    }
}

subprojects {
    apply {
        plugin("io.gitlab.arturbosch.detekt")
        plugin("org.jlleitschuh.gradle.ktlint")
    }

    ktlint {
        debug.set(false)
        version.set(KTLINT)
        verbose.set(true)
        android.set(false)
        outputToConsole.set(true)
        ignoreFailures.set(false)
        enableExperimentalRules.set(true)
        filter {
            exclude("**/generated/**")
            include("**/kotlin/**")
        }
    }

    detekt {
        config = rootProject.files("config/detekt/detekt.yml")
        reports {
            html {
                enabled = true
                destination = file("build/reports/detekt.html")
            }
        }
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_1_8.toString()
            freeCompilerArgs = freeCompilerArgs + listOf(
                "-Xopt-in=kotlinx.serialization.ExperimentalSerializationApi"
            )
        }
    }
}

tasks.register("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}
buildscript {
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$KOTLIN")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$NAVIGATION_VERSION")
    }
}

tasks.withType<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask> {
    rejectVersionIf {
        isNonStable(candidate.version)
    }
}

fun isNonStable(version: String) = "^[0-9,.v-]+(-r)?$".toRegex().matches(version).not()
