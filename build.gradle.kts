import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.0"
    id("com.google.osdetector") version "1.7.1"
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.spring") version "1.9.23"
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply {
        plugin("io.spring.dependency-management")
        plugin("org.springframework.boot")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.jetbrains.kotlin.plugin.spring")
        plugin("org.jlleitschuh.gradle.ktlint")
        plugin("com.google.osdetector")
    }

    group = "tis"
    version = "1.0-SNAPSHOT"

    kotlin {
        jvmToolchain(17)
    }

    extra["springCloudVersion"] = "2023.0.1"

    dependencies {
        // https://mvnrepository.com/artifact/io.netty/netty-resolver-dns-native-macos
        if (osdetector.classifier == "osx-aarch_64") {
            runtimeOnly("io.netty:netty-resolver-dns-native-macos:4.1.77.Final:${osdetector.classifier}")
        }
        implementation("io.micrometer.prometheus:prometheus-rsocket-spring:1.5.3")
        implementation("org.springframework.boot:spring-boot-starter-rsocket")
        implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.springframework.boot:spring-boot-testcontainers")
        testImplementation("io.projectreactor:reactor-test")
        testImplementation("org.springframework.cloud:spring-cloud-starter-contract-stub-runner")
        testImplementation("org.testcontainers:junit-jupiter")
    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = "17"
        }
    }

    tasks.test {
        useJUnitPlatform()
    }

    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        debug.set(true)
    }
}
