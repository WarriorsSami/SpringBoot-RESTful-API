package com.sami.jwtauthapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude= [SecurityAutoConfiguration::class])
class JwtAuthApiApplication

fun main(args: Array<String>) {
    runApplication<JwtAuthApiApplication>(*args)
}
