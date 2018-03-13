package com.codependent.reactivegames.web.handler

import com.codependent.reactivegames.dto.Game
import com.codependent.reactivegames.dto.Player
import com.codependent.reactivegames.dto.RaffleResult
import com.codependent.reactivegames.repository.GamesRepository
import com.codependent.reactivegames.repository.PlayersRepository
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Flux
import java.util.function.BiFunction

@Configuration
class ApiHandlers(private val gamesRepository: GamesRepository,
                  private val playersRepository: PlayersRepository,
                  private val webClient: WebClient) {

    fun getGames(serverRequest: ServerRequest) =
            ok().body(gamesRepository.get(), Game::class.java)

    fun getPlayers(serverRequest: ServerRequest) =
            ok().body(playersRepository.get(), Player::class.java)

    fun getRaffleResults(serverRequest: ServerRequest) = ok().body(
            Flux.zip<Game, Player, RaffleResult>(
                    webClient.get().uri("/games").retrieve().bodyToFlux(Game::class.java),
                    webClient.get().uri("/players").retrieve().bodyToFlux(Player::class.java),
                    BiFunction<Game, Player, RaffleResult> { g, p -> RaffleResult(g, p) }),
            RaffleResult::class.java)
}