package com.codependent.reactivegames.web.handler

import com.codependent.reactivegames.dto.Player
import com.codependent.reactivegames.repository.PlayersRepository
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok

@Component
class ApiHandlers(private val playersRepository: PlayersRepository) {

    fun getPlayers(serverRequest: ServerRequest) =
            ok().body(playersRepository.findAll(), Player::class.java)

}