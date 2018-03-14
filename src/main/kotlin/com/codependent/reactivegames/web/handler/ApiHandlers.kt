package com.codependent.reactivegames.web.handler

import com.codependent.reactivegames.client.RaffleClient
import com.codependent.reactivegames.dto.Game
import com.codependent.reactivegames.dto.Player
import com.codependent.reactivegames.dto.RaffleResult
import com.codependent.reactivegames.repository.GamesRepository
import com.codependent.reactivegames.repository.PlayersRepository
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok

@Component
class ApiHandlers(private val gamesRepository: GamesRepository,
                  private val playersRepository: PlayersRepository,
                  private val raffleClient: RaffleClient) {

    fun getGames(serverRequest: ServerRequest) =
            ok().body(gamesRepository.findAll(), Game::class.java)

    fun getPlayers(serverRequest: ServerRequest) =
            ok().body(playersRepository.findAll(), Player::class.java)

    fun getRaffleResults(serverRequest: ServerRequest) =
            ok().body(raffleClient.raffleResults(), RaffleResult::class.java)
}