package com.codependent.reactivegames.client

import com.codependent.reactivegames.dto.Game
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux

@Component
class GamesClient(private val webClientBuilder: WebClient.Builder,
                  circuitBreakerRegistry: CircuitBreakerRegistry) {

    private val logger = LoggerFactory.getLogger(javaClass)
    private var gamesCircuitBreaker: CircuitBreaker = circuitBreakerRegistry.circuitBreaker("gamesCircuitBreaker")

    fun getGames(): Flux<Game> {
        logger.info("Circuit breaker ${gamesCircuitBreaker.name} state ${gamesCircuitBreaker.state}")
        return webClientBuilder.baseUrl("http://localhost:8081/api/v1").build()
                .get().uri("/games").retrieve().bodyToFlux(Game::class.java)
                .transform(CircuitBreakerOperator.of(gamesCircuitBreaker))
                .onErrorResume(Exception::class.java, { _ -> Flux.empty() })
    }

}