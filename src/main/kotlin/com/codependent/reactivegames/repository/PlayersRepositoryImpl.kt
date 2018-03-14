package com.codependent.reactivegames.repository

import com.codependent.reactivegames.dto.Player
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class PlayersRepositoryImpl : PlayersRepository {

    override fun findAll() = Flux.just(
            Player("Jose"),
            Player("John"),
            Player("Anna"),
            Player("Charles"),
            Player("Annie"),
            Player("Megan")).log()


}