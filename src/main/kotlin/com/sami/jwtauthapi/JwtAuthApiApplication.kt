package com.sami.jwtauthapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JwtAuthApiApplication

fun main(args: Array<String>) {
    runApplication<JwtAuthApiApplication>(*args)
}
