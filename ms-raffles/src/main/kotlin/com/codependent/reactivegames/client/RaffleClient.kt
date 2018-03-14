package com.codependent.reactivegames.client

import com.codependent.reactivegames.dto.Game
import com.codependent.reactivegames.dto.Player
import com.codependent.reactivegames.dto.RaffleResult
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import java.util.function.BiFunction

@Component
class RaffleClient(val webClientBuilder: WebClient.Builder) {

    fun raffleResults() = Flux.zip<Game, Player, RaffleResult>(
            webClientBuilder.baseUrl("http://localhost:8081/api/v1").build()
                    .get().uri("/games").retrieve().bodyToFlux(Game::class.java).log(),
            webClientBuilder.baseUrl("http://localhost:8082/api/v1").build()
                    .get().uri("/players").retrieve().bodyToFlux(Player::class.java).log(),
            BiFunction<Game, Player, RaffleResult> { g, p -> RaffleResult(g, p) }).log()

}