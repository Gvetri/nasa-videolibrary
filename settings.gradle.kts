include(":model")
include(":testing")
include(":datasource")
pluginManagement {
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "com.android.library") {
                useModule("com.android.tools.build:gradle:${requested.version}")
            }
            if (requested.id.id == "com.android.application") {
                useModule("com.android.tools.build:gradle:${requested.version}")
            }
        }
    }
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        jcenter()
    }
}

rootProject.name = "com.gvetri.kotlin.videolibrary"

include(
    "app",
    "core",
    "network",
    "home",
    "download",
    "settings"
)
