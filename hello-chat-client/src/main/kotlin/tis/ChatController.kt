package tis

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.shell.command.annotation.Command
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.time.Duration

private const val ENTER = "enter"
private const val SEND = "send"
private const val KEEP = "keep"
private const val ENTER_CHANNEL = "enter-message"
private const val SEND_MESSAGE = "send-message"
private const val KEEP_MESSAGE = "keep-message"

@Command
@Component
class ChatController(
    private val rSocketRequester: RSocketRequester,
) {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Command(command = [ENTER])
    fun enterRoom(channel: String) {
        rSocketRequester
            .route(ENTER_CHANNEL)
            .data(channel)
            .retrieveFlux(String::class.java)
            .subscribe { message -> log.info("Received: $message") }
    }

    @Command(command = [SEND])
    fun send(
        channel: String,
        message: String,
    ) {
        rSocketRequester
            .route(SEND_MESSAGE)
            .data(Message(channel, message))
            .send()
            .subscribe()
    }

    @Command(command = [KEEP])
    fun keep(
        channel: String,
        message: String,
    ) {
        val subscribe =
            Flux.interval(Duration.ofSeconds(1))
                .map { Message(channel, message) }
                .doOnNext { log.info("Sending: $it") }
                .doOnNext { send(it.channel, it.content) }

        rSocketRequester
            .route(KEEP_MESSAGE)
            .data(subscribe)
            .retrieveFlux(String::class.java)
            .subscribe { log.info("Received: $it") }
    }
}
