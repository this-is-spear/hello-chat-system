package tis

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.shell.command.annotation.CommandScan

@CommandScan
@SpringBootApplication
class ChatClientApplication

fun main(args: Array<String>) {
    runApplication<ChatClientApplication>(*args)
}
