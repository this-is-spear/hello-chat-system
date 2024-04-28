package tis

import io.micrometer.prometheus.rsocket.autoconfigure.EnablePrometheusRSocketProxyServer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@EnablePrometheusRSocketProxyServer
@SpringBootApplication
class ChatServerApplication

fun main(args: Array<String>) {
    runApplication<ChatServerApplication>(*args)
}
