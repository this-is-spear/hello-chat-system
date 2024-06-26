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
private const val KEEP_MESSAGE = "keep-message"

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
    fun send(message: Message): Mono<Void> {
        log.info("send message. message : $message")
        val channel = message.channel
        val content = message.content

        return redisTemplate
            .convertAndSend(channel, content)
            .then()
    }

    @MessageMapping(KEEP_MESSAGE)
    fun sendKeepGoing(messages: Flux<Message>): Flux<Long> {
        return messages.log()
            .flatMap {
                log.info("send message. message : $it")
                redisTemplate.convertAndSend(it.channel, it.content)
            }
    }
}
