package com.codependent.reactivegames.repository

import com.codependent.reactivegames.dto.Player
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class PlayersRepositoryImpl : PlayersRepository {

    override fun findAll(): Flux<Player> {
        return if (Math.random() < 0.5) {
            Flux.error(RuntimeException("PlayersRepository findAll failed"))
        } else {
            Flux.just(
                    Player("Jose"),
                    Player("John"),
                    Player("Anna"),
                    Player("Charles"),
                    Player("Annie"),
                    Player("Megan")).log()
        }
    }

}