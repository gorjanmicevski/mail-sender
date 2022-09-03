package com.project.mailbot.mail

import org.springframework.stereotype.Service
import java.util.function.Predicate
import java.util.regex.Pattern

@Service
class EmailValidator : Predicate<String> {
    private val regexPattern = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"
    override fun test(s: String): Boolean = Pattern.compile(regexPattern).matcher(s).matches()

}