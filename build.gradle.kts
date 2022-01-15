import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.0"
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "ua.tox1cozz.vkmusicdisplay"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin", "kotlin-stdlib-jdk8", "1.6.0")
    implementation("org.jetbrains.kotlin", "kotlin-reflect", "1.6.0")

    implementation("io.ktor", "ktor-server-core", "1.6.7")
    implementation("io.ktor", "ktor-server-netty", "1.6.7")

    implementation("org.jsoup", "jsoup", "1.14.3")

    implementation("org.apache.logging.log4j", "log4j-core", "2.17.1")
    implementation("org.apache.logging.log4j", "log4j-slf4j-impl", "2.17.1")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

tasks.withType<Jar> {
    manifest {
        attributes(
            mapOf(
                "Main-Class" to "ua.tox1cozz.vkmusicdisplay.AppKt",
                "Created-By" to "tox1cozZ (vk.com/agravaine)"
            )
        )
    }
}

val build by tasks.getting
val shadowJar by tasks.getting
build.dependsOn(shadowJar)