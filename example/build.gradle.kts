plugins {
    java
}

dependencies {
    implementation(platform(project(":our-platform"))) // bom that is extending original scio-bom
    implementation("com.spotify:scio-cassandra3_2.13") // original dependency, not changed by our constraints (0.14.9)
    implementation("com.spotify:scio-redis_2.13") // overridden version from scio-bom but changed to ours (0.14.9 -> 0.14.7)
    implementation("args4j:args4j") // version from scio-core, cannot be changed
    implementation("com.fasterxml.jackson.core:jackson-databind") // version from scio-core, cannot be changed, TODO: try to set it to 2.18.1 (latest)
    implementation("com.github.bgalek.security.svg:safe-svg") // custom version from our bom, no need to specify (1.1.4)
    implementation("com.spotify:scio-core_2.13") // version from bom, no need to specify (0.14.9), will be overridden by our constraints
    testImplementation("org.scalatestplus:scalacheck-1-18_3") // dynamic version based on scala-test dependency
}