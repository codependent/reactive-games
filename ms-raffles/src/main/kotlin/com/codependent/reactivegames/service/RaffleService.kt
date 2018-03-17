package com.codependent.reactivegames.service

import com.codependent.reactivegames.dto.RaffleResult
import reactor.core.publisher.Flux

interface RaffleService {

    fun raffle(): Flux<RaffleResult>
}