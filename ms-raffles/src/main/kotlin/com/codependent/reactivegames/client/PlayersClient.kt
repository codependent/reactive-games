package com.codependent.reactivegames.client

import com.codependent.reactivegames.dto.Player
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux

@Component
class PlayersClient(private val webClientBuilder: WebClient.Builder,
                    circuitBreakerRegistry: CircuitBreakerRegistry) {

    private val logger = LoggerFactory.getLogger(javaClass)
    private var playersCircuitBreaker: CircuitBreaker = circuitBreakerRegistry.circuitBreaker("playersCircuitBreaker")

    fun getPlayers(): Flux<Player> {
        logger.info("Circuit breaker ${playersCircuitBreaker.name} state ${playersCircuitBreaker.state}")
        return webClientBuilder.baseUrl("http://localhost:8082/api/v1").build()
                .get().uri("/players").retrieve().bodyToFlux(Player::class.java)
                .transform(CircuitBreakerOperator.of(playersCircuitBreaker))
                .onErrorResume(Exception::class.java, { _ -> Flux.empty() })
    }
}