extra["springShellVersion"] = "3.2.3"

dependencies {
    implementation("org.springframework.shell:spring-shell-starter")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.3")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.shell:spring-shell-dependencies:${property("springShellVersion")}")
    }
}

tasks.withType<Jar> {
    enabled = true
}
