rootProject.name = "gradle-platform-constraints"

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

include("example")
include("our-platform")
