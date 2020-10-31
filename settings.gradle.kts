include(":network:fakenasaapi")
include(":network:fake")
include(":nasarepository:fake")
include(":nasarepository")
include(":di")
include(":network:nasaapi")
include(":network:apimodel")
include(":repository")
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
