plugins {
    id("java")
    id("jacoco")
}

group = "dq.engine"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.apache.spark:spark-sql_2.13:4.1.0")
    compileOnly("org.apache.spark:spark-core_2.13:4.1.0")


    testImplementation("org.apache.spark:spark-sql_2.13:4.1.0")
    testImplementation("org.apache.spark:spark-core_2.13:4.1.0")

    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.named("jacocoTestReport"))
}

val coverageExcludes = listOf(
    "**/generated/**",
    "**/config/**",
    "**/model/**",
    "**/enums/**"
)

tasks.named<JacocoReport>("jacocoTestReport") {
    dependsOn(tasks.test)

    reports {
        xml.required.set(true)
        html.required.set(true)
        csv.required.set(false)
    }

    classDirectories.setFrom(
        sourceSets.main.get().output.asFileTree.matching {
            exclude(coverageExcludes)
        }
    )
}

tasks.named<JacocoCoverageVerification>("jacocoTestCoverageVerification") {
    dependsOn(tasks.named("jacocoTestReport"))

    classDirectories.setFrom(
        sourceSets.main.get().output.asFileTree.matching {
            exclude(coverageExcludes)
        }
    )

    violationRules {
        rule {
            limit {
                minimum = "0.70".toBigDecimal()
            }
        }
    }
}

tasks.check {
    dependsOn(tasks.named("jacocoTestCoverageVerification"))
}