package com.codependent.reactivegames.client

import com.codependent.reactivegames.dto.Game
import com.codependent.reactivegames.dto.Player
import com.codependent.reactivegames.dto.RaffleResult
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import java.util.function.BiFunction

@Component
class RaffleClient(private val webClientBuilder: WebClient.Builder,
                   private val circuitBreakerRegistry: CircuitBreakerRegistry) {

    private val logger = LoggerFactory.getLogger(javaClass)
    private var gamesCircuitBreaker: CircuitBreaker = circuitBreakerRegistry.circuitBreaker("gamesCircuitBreaker")
    private var playersCircuitBreaker: CircuitBreaker = circuitBreakerRegistry.circuitBreaker("playersCircuitBreaker")

    fun raffleResults() = Flux.zip<Game, Player, RaffleResult>(
            getGames(), getPlayers(),
            BiFunction<Game, Player, RaffleResult> { g, p -> RaffleResult(g, p) }).log()

    private fun getGames(): Flux<Game> {
        logger.info("Circuit breaker ${gamesCircuitBreaker.name} state ${gamesCircuitBreaker.state}")
        return webClientBuilder.baseUrl("http://localhost:8081/api/v1").build()
                .get().uri("/games").retrieve().bodyToFlux(Game::class.java)
                .transform(CircuitBreakerOperator.of(gamesCircuitBreaker))
                .onErrorResume(Exception::class.java, { _ -> Flux.empty() })
    }

    private fun getPlayers(): Flux<Player> {
        logger.info("Circuit breaker ${playersCircuitBreaker.name} state ${playersCircuitBreaker.state}")
        return webClientBuilder.baseUrl("http://localhost:8082/api/v1").build()
                .get().uri("/players").retrieve().bodyToFlux(Player::class.java)
                .transform(CircuitBreakerOperator.of(playersCircuitBreaker))
                .onErrorResume(Exception::class.java, { _ -> Flux.empty() })
    }
}