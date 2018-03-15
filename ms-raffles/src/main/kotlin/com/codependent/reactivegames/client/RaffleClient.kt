package com.codependent.reactivegames.client

import com.codependent.reactivegames.dto.Game
import com.codependent.reactivegames.dto.Player
import com.codependent.reactivegames.dto.RaffleResult
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.circuitbreaker.CircuitBreakerOpenException
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Flux
import java.time.Duration
import java.util.function.BiFunction

@Component
class RaffleClient(val webClientBuilder: WebClient.Builder) {

    private val logger = LoggerFactory.getLogger(javaClass)
    private val circuitBreakerConfig: CircuitBreakerConfig = CircuitBreakerConfig.custom()
            .failureRateThreshold(50f)
            .waitDurationInOpenState(Duration.ofMillis(10000))
            .ringBufferSizeInHalfOpenState(5)
            .ringBufferSizeInClosedState(5)
            .build()
    private var gamesCircuitBreaker: CircuitBreaker = CircuitBreaker.of("gamesCircuitBreaker", circuitBreakerConfig)
    private var playersCircuitBreaker: CircuitBreaker = CircuitBreaker.of("playersCircuitBreaker", circuitBreakerConfig)

    fun raffleResults() = Flux.zip<Game, Player, RaffleResult>(
            getGames(), getPlayers(),
            BiFunction<Game, Player, RaffleResult> { g, p -> RaffleResult(g, p) }).log()

    private fun getGames() : Flux<Game> {
        logger.info("Circuit breaker ${gamesCircuitBreaker.name} state ${gamesCircuitBreaker.state}")
        return webClientBuilder.baseUrl("http://localhost:8081/api/v1").build()
                .get().uri("/games").retrieve().bodyToFlux(Game::class.java)
                .transform(CircuitBreakerOperator.of(gamesCircuitBreaker))
                .onErrorResume(CircuitBreakerOpenException::class.java, { _ -> Flux.empty() })
    }

    private fun getPlayers() : Flux<Player> {
        logger.info("Circuit breaker ${playersCircuitBreaker.name} state ${playersCircuitBreaker.state}")
        return webClientBuilder.baseUrl("http://localhost:8082/api/v1").build()
                .get().uri("/players").retrieve().bodyToFlux(Player::class.java)
                .transform(CircuitBreakerOperator.of(playersCircuitBreaker))
                .onErrorResume(CircuitBreakerOpenException::class.java, { _ -> Flux.empty() })
    }
}