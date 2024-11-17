plugins {
    `java-platform`
}

javaPlatform {
    allowDependencies()
}

val scalaTestAware = configurations.create("scalaTestAware") {
    isCanBeResolved = true
    isCanBeConsumed = false
}

val versionAware = configurations.create("versionAware") {
    isCanBeResolved = true
    isCanBeConsumed = false
}

fun resolveVersion(group: String, name: String) = scalaTestAware
    .resolvedConfiguration
    .resolvedArtifacts
    .first { it.moduleVersion.id.group == group && it.name == name }
    .moduleVersion
    .id
    .version

dependencies {
    // dependencies that will go to the runtime classpath of users
    api(platform(libs.scio.bom))
    api(libs.scio.core)

    // dependencies that are only here to be scanned
    scalaTestAware(libs.scio.test)
    versionAware(libs.scio.core)

    constraints {
        // make sure that all dependencies from the scio-core are strictly matching sci-core version
        versionAware
            .resolvedConfiguration
            .resolvedArtifacts
            .forEach {
                api("${it.moduleVersion.id.group}:${it.moduleVersion.id.name}") {
                    version { strictly(it.moduleVersion.id.version) }
                }
            }

        // make sure that scalacheck from the scio-test is strictly matching sci-test version
        api("org.scalatestplus:scalacheck-1-18_3") {
            val scalaTestVersion = resolveVersion("org.scalatest", "scalatest_2.13")
            version { strictly("$scalaTestVersion.0") }
        }

        // add custom constraint, that is absent in the scio-bom
        api("com.github.bgalek.security.svg:safe-svg") {
            version {
                strictly("1.1.4")
            }
        }

        // override the version from the scio-bom or scio-core (0.14.9)
        api("com.spotify:scio-redis_2.13") {
            version {
                strictly("0.14.7")
            }
        }

        // overriding a transient library version from the scio-core will fail since it's already locked by scio-core
//        api("args4j:args4j") {
//            version {
//                strictly("2.33")
//            }
//        }
    }
}
