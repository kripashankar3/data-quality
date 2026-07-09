import org.gradle.model.internal.typeregistration.InstanceFactory

plugins {
    id("java")
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
}