package com.project.mailbot.service

import com.project.mailbot.domain.EmailRecord
import com.project.mailbot.repository.EmailRepository
import org.springframework.stereotype.Service

@Service
class EmailService(val emailRepository: EmailRepository) {
    fun importMails(emailRecords: List<EmailRecord>){
        emailRepository.saveAll(emailRecords)
    }
}