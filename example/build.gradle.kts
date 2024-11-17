plugins {
    java
}

dependencies {
    implementation(platform(project(":our-platform"))) // bom that is extending original scio-bom
    implementation("com.spotify:scio-cassandra3_2.13") // original dependency, not changed by our constraints (0.14.9)
    implementation("com.spotify:scio-redis_2.13") // overridden version from scio-bom but changed to ours (0.14.9 -> 0.14.7)
    implementation("args4j:args4j") // overridden version from scio-bom but changed to ours (2.33 -> 2.32)
    implementation("com.github.bgalek.security.svg:safe-svg") // custom version from our bom, no need to specify (1.1.4)
}

// just for demo purposes
tasks.register("demo") {
    doLast {
        println("args4j: " + getVersion("args4j:args4j"))
        println("cassandra3: " + getVersion("com.spotify:scio-cassandra3_2.13"))
        println("scio-redis: " + getVersion("com.spotify:scio-redis_2.13"))
        println("safe-svg: " + getVersion("com.github.bgalek.security.svg:safe-svg"))
    }
}

// just for demo purposes
fun getVersion(coordinates: String): String {
    val split = coordinates.split(":", limit = 2)
    return configurations.runtimeClasspath.get()
        .resolvedConfiguration
        .firstLevelModuleDependencies
        .find { it.moduleGroup == split[0] && it.moduleName == split[1] }!!.moduleVersion
}