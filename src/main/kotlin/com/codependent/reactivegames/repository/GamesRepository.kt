package com.codependent.reactivegames.repository

import com.codependent.reactivegames.dto.Game
import reactor.core.publisher.Flux

interface GamesRepository {

    fun get(): Flux<Game>

}