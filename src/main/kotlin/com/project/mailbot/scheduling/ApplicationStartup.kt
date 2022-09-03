package com.project.mailbot.scheduling

import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor
import org.springframework.stereotype.Component

@Component
class ApplicationStartup(val postProcessor: ScheduledAnnotationBeanPostProcessor,
                         val schedulerConfiguration: DynamicSchedulingConfig) : ApplicationListener<ApplicationReadyEvent>{
    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        postProcessor.postProcessBeforeDestruction(schedulerConfiguration, "scheduledTasks")
        schedulerConfiguration.enableScheduling()
    }
}