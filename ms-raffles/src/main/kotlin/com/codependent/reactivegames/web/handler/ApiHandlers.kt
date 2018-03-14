package com.codependent.reactivegames.web.handler

import com.codependent.reactivegames.client.RaffleClient
import com.codependent.reactivegames.dto.RaffleResult
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok

@Component
class ApiHandlers(private val raffleClient: RaffleClient) {

    fun getRaffleResults(serverRequest: ServerRequest) =
            ok().body(raffleClient.raffleResults(), RaffleResult::class.java)
}