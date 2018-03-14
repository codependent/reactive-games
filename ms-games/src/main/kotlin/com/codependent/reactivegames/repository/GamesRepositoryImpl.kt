package com.codependent.reactivegames.repository

import com.codependent.reactivegames.dto.Game
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class GamesRepositoryImpl : GamesRepository {

    override fun findAll() = Flux.just(
            Game("The Secret of Monkey Island"),
            Game("The Secret of Monkey Island 2"),
            Game("The Secret of Monkey Island 3"),
            Game("Loom"),
            Game("Maniac Mansion"),
            Game("Day of the Tentacle")).log()

}