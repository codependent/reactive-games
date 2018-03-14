package com.codependent.reactivegames

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ReactiveGamesApplication

fun main(args: Array<String>) {
    runApplication<ReactiveGamesApplication>(*args)
}
