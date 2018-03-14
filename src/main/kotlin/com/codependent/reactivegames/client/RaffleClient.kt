package com.codependent.reactivegames.client

import com.codependent.reactivegames.dto.Game
import com.codependent.reactivegames.dto.Player
import com.codependent.reactivegames.dto.RaffleResult
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import java.util.function.BiFunction

@Component
class RaffleClient(val webClient: WebClient) {

    fun raffleResults() = Flux.zip<Game, Player, RaffleResult>(
            webClient.get().uri("/games").retrieve().bodyToFlux(Game::class.java),
            webClient.get().uri("/players").retrieve().bodyToFlux(Player::class.java),
            BiFunction<Game, Player, RaffleResult> { g, p -> RaffleResult(g, p) })

}