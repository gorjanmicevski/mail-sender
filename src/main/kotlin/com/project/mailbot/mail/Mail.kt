package com.project.mailbot.mail

data class Mail(
    val mailFrom: String,
    val mailTo: String,
    val mailSubject: String,
    val mailContent: String,
) {
}