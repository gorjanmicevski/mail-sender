package com.project.mailbot.domain

import lombok.NoArgsConstructor
import javax.persistence.*

@NoArgsConstructor
@Entity
@Table(name = "email_records")
data class EmailRecord(
    @Id
    val mail: String="",

    var isSent: Boolean= false
)