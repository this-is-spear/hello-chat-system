package tis

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.rsocket.core.RSocketConnector
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.messaging.rsocket.RSocketStrategies
import org.springframework.util.MimeTypeUtils
import reactor.util.retry.Retry
import java.time.Duration

@Configuration
class RsocketConfiguration {
    @Bean
    fun rSocketRequester(): RSocketRequester {
        val mapper = ObjectMapper().registerKotlinModule()

        return RSocketRequester.builder()
            .rsocketConnector { rSocketConnector: RSocketConnector ->
                rSocketConnector.reconnect(
                    Retry.fixedDelay(2, Duration.ofSeconds(2)),
                )
            }
            .rsocketStrategies(
                RSocketStrategies.builder()
                    .encoder(Jackson2JsonEncoder(mapper, MimeTypeUtils.APPLICATION_JSON))
                    .build(),
            )
            .dataMimeType(MimeTypeUtils.APPLICATION_JSON)
            .tcp("127.0.0.1", 7000)
//            .websocket(URI.create("http://localhost:7000/ws"))
    }
}
