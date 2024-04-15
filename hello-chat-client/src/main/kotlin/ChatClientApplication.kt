package tis

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class ChatClientApplication

fun main(args: Array<String>) {
    runApplication<ChatClientApplication>(*args)
}
