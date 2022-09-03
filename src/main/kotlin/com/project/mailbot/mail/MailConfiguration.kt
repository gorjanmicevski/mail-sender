package com.project.mailbot.mail

import com.project.mailbot.domain.SenderMail
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.stereotype.Component
import java.util.*

@Component
//@EnableConfigurationProperties(MailSenderProperties::class)
//val mailSenderProperties: MailSenderProperties
abstract class MailConfiguration {
    val list = listOf(
        SenderMail("cryptoriver3@gmail.com", "dwdoaplpgptjtkna"),
        SenderMail("teslacoinapp@gmail.com", "annsxdrxyftnupit")
    )

    @Bean
    fun javaMailSenders(): List<JavaMailSender> =
        list.map { mail ->
            val mailSender = JavaMailSenderImpl()
            mailSender.host = "smtp.gmail.com"
            mailSender.port = 587
            mailSender.username = mail.mail
            mailSender.password = mail.password
            configureJavaMailProperties(mailSender.javaMailProperties)
            mailSender
        }


    private fun configureJavaMailProperties(properties: Properties) {
        properties["mail.transport.protocol"] = "smtp"
        properties["mail.smtp.auth"] = true
        properties["mail.smtp.starttls.enable"] = true
        properties["mail.debug"] = true
    }
}