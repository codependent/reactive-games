package com.codependent.reactivegames.web.handler

import com.codependent.reactivegames.dto.Game
import com.codependent.reactivegames.repository.GamesRepository
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok

@Component
class ApiHandlers(private val gamesRepository: GamesRepository) {

    fun getGames(serverRequest: ServerRequest) = ok().body(gamesRepository.findAll(), Game::class.java)

}