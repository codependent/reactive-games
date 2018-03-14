package com.codependent.reactivegames.web.handler

import com.codependent.reactivegames.dto.Game
import com.codependent.reactivegames.repository.GamesRepository
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono
import java.time.Duration

@Component
class ApiHandlers(private val gamesRepository: GamesRepository) {

    val circuitBreakerConfig: CircuitBreakerConfig = CircuitBreakerConfig.custom()
            .failureRateThreshold(50f)
            .waitDurationInOpenState(Duration.ofMillis(10000))
            .ringBufferSizeInHalfOpenState(5)
            .ringBufferSizeInClosedState(5)
            .build()
    var circuitBreaker: CircuitBreaker = CircuitBreaker.of("gamesCircuitBreaker", circuitBreakerConfig)

    fun getGames(serverRequest: ServerRequest): Mono<ServerResponse> {
        println("*********${circuitBreaker.state}")
        return ok().body(gamesRepository.findAll().transform(CircuitBreakerOperator.of(circuitBreaker)), Game::class.java)
    }
}