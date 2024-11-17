plugins {
    `java-platform`
}

javaPlatform {
    allowDependencies()
}

dependencies {
    api(platform("com.spotify:scio-bom:0.14.9"))
    constraints {
        api("com.github.bgalek.security.svg:safe-svg") {
            version {
                // next line sets our own constraint, that is absent in scio-bom
                strictly("1.1.4")
            }
        }
        api("com.spotify:scio-redis_2.13") {
            version {
                // next line it will effectively override the version from the scio BOM (0.14.9)
                strictly("0.14.7")
            }
        }
        api("args4j:args4j") {
            version {
                // next line it will effectively override the version from the scio BOM (2.33)
                strictly("2.32")
            }
        }
    }
}