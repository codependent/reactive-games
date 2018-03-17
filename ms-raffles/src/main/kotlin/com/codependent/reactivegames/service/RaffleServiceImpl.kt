package com.codependent.reactivegames.service

import com.codependent.reactivegames.client.GamesClient
import com.codependent.reactivegames.client.PlayersClient
import com.codependent.reactivegames.dto.Game
import com.codependent.reactivegames.dto.Player
import com.codependent.reactivegames.dto.RaffleResult
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.util.function.BiFunction

@Component
class RaffleServiceImpl(private val gamesClient: GamesClient, private val playersService: PlayersClient) : RaffleService {

    override fun raffle() = Flux.zip<Game, Player, RaffleResult>(
            gamesClient.getGames(), playersService.getPlayers(),
            BiFunction<Game, Player, RaffleResult> { g, p -> RaffleResult(g, p) }).log()


}