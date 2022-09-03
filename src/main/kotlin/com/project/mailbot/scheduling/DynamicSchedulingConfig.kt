package com.project.mailbot.scheduling

import com.project.mailbot.domain.EmailRecord
import com.project.mailbot.domain.SenderMail
import com.project.mailbot.mail.MailSenderWorker
import com.project.mailbot.repository.EmailRepository
import com.project.mailbot.service.MailSenderService
import org.hibernate.annotations.common.util.impl.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.PageRequest
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import java.util.*
import javax.mail.internet.InternetAddress

@Configuration
@EnableScheduling
class DynamicSchedulingConfig(
    val repository: EmailRepository,
    val mailSenderService: MailSenderService,
//    val javaMailSenders: List<JavaMailSender>
) {
    private var page = 0
    val logger = LoggerFactory.logger(DynamicSchedulingConfig::class.java)
    private var enabled = false
    fun enableScheduling() {
        enabled = true
    }

    @Scheduled(fixedDelay = 10 * 1000L)
    fun test() {
        logger.info("test scheduler")
        logger.info(javaMailSenders())
        try{
            javaMailSenders().forEach {
                val worker = MailSenderWorker(
                    it, arrayOf(
//                        InternetAddress("gtsleep18@gmail.com"),
                        InternetAddress("kosta.krapovski@sorsix.com")
                    )
                )
                worker.start()
            }
        }catch (e: Exception)
        {
            logger.warn(e.message)
            logger.warn(e.stackTrace)
        }

    }

    //    @Scheduled(fixedDelay = 10 * 1000L)
    fun sendMail() {
//        if (enabled) {
//            try {
//                var emails = repository.findAll(PageRequest.of(page, 50)).content
//                println("emails $emails")
//
//                if (emails.isEmpty())
//                    page = 0
//                else {
//                    page++
//                    emails = emails.filter { record -> !record.isSent }
//                    if (emails.size != 0) {
//                        mailSenderService.sendEmail(emails.map { InternetAddress(it.mail) }.toTypedArray())
//                        repository.saveAllAndFlush(emails.map { EmailRecord(it.mail, true) }.toList())
//                        logger.info("Sent to: $emails")
//                    }
//                }
//            } catch (e: Exception) {
//                logger.error(e)
//                logger.error("Stack trace: ${e.stackTrace}")
//                println(e)
//                Thread.sleep(1000 * 1000L)
//                println("after sleep")
//            }
//        }

    }
    val list = listOf(
        SenderMail("cryptoriver3@gmail.com", "dwdoaplpgptjtkna"),
        SenderMail("teslacoinapp@gmail.com", "annsxdrxyftnupit")
//        SenderMail("teslacoinapp@gmail.com", "annsxdrxyftnupit")

    )

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