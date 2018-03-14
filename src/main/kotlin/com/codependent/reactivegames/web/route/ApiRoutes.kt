package com.codependent.reactivegames.web.route

import com.codependent.reactivegames.web.handler.ApiHandlers
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router

@Configuration
class ApiRoutes(private val apiHandlers: ApiHandlers) {

    @Bean
    fun apiRouter() = router {
        "/api/v1".nest {
            GET("/games", apiHandlers::getGames)
            GET("/players", apiHandlers::getPlayers)
            GET("/raffleResults", apiHandlers::getRaffleResults)
        }
    }
}
