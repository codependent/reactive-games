package com.codependent.reactivegames.web.handler

import com.codependent.reactivegames.dto.RaffleResult
import com.codependent.reactivegames.service.RaffleService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok

@Component
class ApiHandlers(private val raffleService: RaffleService) {

    fun getRaffleResults(serverRequest: ServerRequest) =
            ok().body(raffleService.raffle(), RaffleResult::class.java)
}