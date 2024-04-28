
dependencies {
    developmentOnly("org.springframework.boot:spring-boot-docker-compose")
    implementation("io.micrometer.prometheus:prometheus-rsocket-spring:1.5.3")
    implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")
}
