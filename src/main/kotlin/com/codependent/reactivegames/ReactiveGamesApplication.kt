package com.codependent.reactivegames

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.reactive.function.client.WebClient

@SpringBootApplication
class ReactiveGamesApplication {
    @Bean
    fun webClient() = WebClient.create("http://localhost:8080/api/v1")
}

fun main(args: Array<String>) {
    runApplication<ReactiveGamesApplication>(*args)
}
