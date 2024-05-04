package tis

import io.micrometer.prometheus.PrometheusConfig
import io.micrometer.prometheus.PrometheusMeterRegistry
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PrometheusConfiguration {
    @Bean
    fun prometheusMeterRegistry(): PrometheusMeterRegistry {
        return PrometheusMeterRegistry(PrometheusConfig.DEFAULT)
    }
}
