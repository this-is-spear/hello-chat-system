package tis

import org.reactivestreams.Publisher
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.shell.command.annotation.Command
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

private const val ENTER = "enter"
private const val SEND = "send"
private const val ENTER_CHANNEL = "enter-message"
private const val SEND_MESSAGE = "send-message"

@Command
@Component
class ChatController(
    private val rSocketRequester: RSocketRequester,
) {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Command(command = [ENTER])
    fun enterRoom(username: String) {
        rSocketRequester
            .route(ENTER_CHANNEL)
            .data(username)
            .retrieveFlux(String::class.java)
            .subscribe { message -> log.info("Received: $message") }
    }

    @Command(command = [SEND])
    fun send(message: String): Publisher<Void> {
        rSocketRequester
            .route(SEND_MESSAGE)
            .data(message)
            .send()
            .subscribe()

        return Mono.empty()
    }
}
