package com.project.mailbot.mail

import com.project.mailbot.scheduling.DynamicSchedulingConfig
import org.hibernate.annotations.common.util.impl.LoggerFactory
import org.springframework.mail.javamail.JavaMailSender
import javax.activation.DataHandler
import javax.activation.DataSource
import javax.activation.FileDataSource
import javax.mail.Address
import javax.mail.BodyPart
import javax.mail.Message
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMultipart

class MailSenderWorker(val javaMailSender: JavaMailSender,val emails: Array<Address>) : Thread() {
    val logger = LoggerFactory.logger(MailSenderWorker::class.java)

    override fun run(){
        logger.info("running")
        sendEmail(javaMailSender,emails)
    }
    private val fds: DataSource = FileDataSource(
        "C:\\Users\\gtsle\\OneDrive\\Desktop\\mail-bot\\src\\main\\resources\\static\\Tesla Coin Email.png"
    )

    fun sendEmail(mailSender: JavaMailSender,emails: Array<Address>){
        val message= mailSender.createMimeMessage()
        message.subject = "Start investing with Tesla Coin"
        message.setRecipients(Message.RecipientType.TO,emails)

        val mm= MimeMultipart()
        val htmlBodyPart: BodyPart = MimeBodyPart()
        val htmlText = "<div style=\"width: 50%;float:left;font-size:20px;font-family:Arial, sans-serif;padding-right:10px\">" +
                "<h5>Start trading now</h5>"+
                "<p>Tesla Coin App provides an automated trading software free of charge. The feature doesn't require any previous trading experience, knowledge or time, since it works fully automatically on trading platforms. The user-friendly trading bot can skyrocket you initial investment within a day! </p>" +
                "<p><a href=\"https://teslacoinapp.com/\">teslacoinapp.com</a></p> </div>"+
                "<div style=\"float:left;\"><a href=\"https://teslacoinapp.com/\"><img src=\"cid:image\"></a></div>"
        htmlBodyPart.setContent(htmlText, "text/html")
        mm.addBodyPart(htmlBodyPart);
        val imageBodyPart = MimeBodyPart()
        imageBodyPart.setDataHandler(DataHandler(fds))
        imageBodyPart.setHeader("Content-ID", "<image>")
        mm.addBodyPart(imageBodyPart)
        message.setContent(mm)
        mailSender.send(message)
    }
}