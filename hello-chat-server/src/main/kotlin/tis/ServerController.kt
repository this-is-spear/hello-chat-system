package tis

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.ReactiveStringRedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

private const val ENTER_CHANNEL = "enter-message"
private const val SEND_MESSAGE = "send-message"

@Controller
class ServerController(
    private val redisTemplate: ReactiveStringRedisTemplate,
) {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @MessageMapping(ENTER_CHANNEL)
    fun channel(channel: String): Flux<String> {
        log.info("Received channel request...")
        log.info("connect $channel")
        return redisTemplate
            .listenTo(ChannelTopic.of(channel))
            .map { it.message }
    }

    @MessageMapping(SEND_MESSAGE)
    fun send(message: String): Mono<Void> {
        log.info("send message. message : $message")
        val channel = message.split(":")[0]
        val content = message.split(":")[1]

        return redisTemplate
            .convertAndSend(channel, content)
            .then()
    }
}
