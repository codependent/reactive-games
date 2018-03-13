package com.codependent.reactivegames.repository

import com.codependent.reactivegames.dto.Player
import reactor.core.publisher.Flux

interface PlayersRepository {

    fun get(): Flux<Player>

}