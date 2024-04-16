package tis

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.shell.command.annotation.Command
import org.springframework.stereotype.Component
import reactor.core.Disposable

@Command
@Component
class ChatController(
    private val rSocketRequester: RSocketRequester,
) {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)
    lateinit var disposable: Disposable

    @Command(command = ["enter"])
    fun enterRoom(message: String) {
        disposable =
            rSocketRequester
                .route("channel")
                .data(message)
                .retrieveFlux(String::class.java)
                .subscribe { message -> log.info("Received: $message") }
    }
}
