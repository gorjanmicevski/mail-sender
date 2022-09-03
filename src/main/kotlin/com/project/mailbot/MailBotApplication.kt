package com.project.mailbot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MailBotApplication(){

}

fun main(args: Array<String>) {
    runApplication<MailBotApplication>(*args)
}
