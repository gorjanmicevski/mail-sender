package com.project.mailbot.repository

import com.project.mailbot.domain.EmailRecord
import org.springframework.data.jpa.repository.JpaRepository

interface EmailRepository : JpaRepository<EmailRecord,Long> {
}