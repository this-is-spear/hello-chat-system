package tis

import org.springframework.boot.fromApplication
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.boot.with
import org.springframework.context.annotation.Bean
import org.testcontainers.containers.GenericContainer
import org.testcontainers.utility.DockerImageName

@TestConfiguration(proxyBeanMethods = false)
class TestDemoApplication {
    @Bean
    @ServiceConnection(name = "redis")
    fun redisContainer(): GenericContainer<*> =
        GenericContainer(DockerImageName.parse("redis:latest"))
            .withExposedPorts(6379)
}

