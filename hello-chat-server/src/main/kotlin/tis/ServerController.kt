package tis

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toFlux
import java.time.Duration

@Controller
class ServerController {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @MessageMapping("channel")
    fun channel(message: String): Flux<String> {
        log.info("Received channel request...")

        return LongRange(1, 10).toFlux()
            .doOnNext { setting: Long ->
                log.info("Channel frequency setting is {} second(s).", setting)
            }
            .doOnCancel { log.warn("The client cancelled the channel.") }
            .switchMap { setting -> Flux.interval(Duration.ofSeconds(setting)).map { "server sent message $it" } }
    }
}
