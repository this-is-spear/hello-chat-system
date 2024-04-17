package tis

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.ReactiveStringRedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

private const val CHANNEL = "channel"
private const val ENTER_CHANNEL = "enter-message"
private const val SEND_MESSAGE = "send-message"

@Controller
class ServerController(
    private val redisTemplate: ReactiveStringRedisTemplate,
) {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @MessageMapping(ENTER_CHANNEL)
    fun channel(name: String): Flux<String> {
        log.info("Received channel request...")
        log.info("enter $name")
        return redisTemplate
            .listenTo(ChannelTopic.of(CHANNEL))
            .map { it.message }
    }

    @MessageMapping(SEND_MESSAGE)
    fun send(message: String): Mono<Void> {
        log.info("send message. message : $message")
        redisTemplate
            .convertAndSend(CHANNEL, message)
            .then().subscribe()
        return Mono.empty()
    }
}
