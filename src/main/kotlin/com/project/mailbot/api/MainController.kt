package com.project.mailbot.api

import com.project.mailbot.scheduling.DynamicSchedulingConfig
import com.project.mailbot.service.EmailService
import com.project.mailbot.service.FileParserService
import com.project.mailbot.service.MailSenderService
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.multipart.MultipartFile
import javax.mail.internet.InternetAddress


@Controller
class MainController(
    val mailSenderService: MailSenderService,
    val fileParserService: FileParserService,
    val emailService: EmailService,
    val postProcessor: ScheduledAnnotationBeanPostProcessor,
    val schedulerConfiguration: DynamicSchedulingConfig) {

    private val SCHEDULED_TASKS = "scheduledTasks"

    @GetMapping("/stop")
    fun stopSchedule(model:Model): String? {
        postProcessor.postProcessBeforeDestruction(schedulerConfiguration, SCHEDULED_TASKS)
        model.addAttribute("status","Stopped")
        return "executing.html"
    }

    @GetMapping("/start")
    fun startSchedule(model:Model): String? {
        model.addAttribute("status","Running")
        postProcessor.postProcessAfterInitialization(schedulerConfiguration, SCHEDULED_TASKS)
        return "executing.html"
    }
    @GetMapping("/execute")
    fun execute(model:Model):String{
        postProcessor.postProcessBeforeDestruction(schedulerConfiguration, SCHEDULED_TASKS)
        model.addAttribute("status","Stopped")
        return "executing.html"
    }
    @GetMapping
    fun home(model:Model): String {
        return "home.html"
    }
    @GetMapping("/sendMail")
    fun send():String{
        println("send")
//        mailSenderService.sendEmail(arrayOf(InternetAddress("gtsleep18@gmail.com"),
//            InternetAddress("JNKIRK@iprimos.com.au")
//        ))
        return "redirect:/"
    }
    @PostMapping("/upload")
    fun upload(file:MultipartFile): String {
        try{
            emailService.importMails(fileParserService.parseFile(file))
        }catch (e : Exception){
            print(e)
        }
        return "home.html"
    }
}