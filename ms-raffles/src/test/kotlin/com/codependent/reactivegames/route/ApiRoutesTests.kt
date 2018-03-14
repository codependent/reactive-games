package com.codependent.reactivegames.route

import com.codependent.reactivegames.client.RaffleClient
import com.codependent.reactivegames.dto.Game
import com.codependent.reactivegames.dto.Player
import com.codependent.reactivegames.dto.RaffleResult
import com.codependent.reactivegames.web.handler.ApiHandlers
import com.codependent.reactivegames.web.route.ApiRoutes
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.ApplicationContext
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux


@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [ApiRoutes::class, ApiHandlers::class])
@WebFluxTest
class ApiRoutesTests(@Autowired val context: ApplicationContext) {

    @MockBean
    private lateinit var raffleClient: RaffleClient

    private lateinit var testWebClient: WebTestClient

    @BeforeEach
    fun setUp() {
        testWebClient = WebTestClient.bindToApplicationContext(context).build()
    }

    @Test
    fun shouldGetRaffleResults() {
        val raffleResults = Flux.just(RaffleResult(Game("Maniac Mansion"), Player("Jose")))
        `when`(raffleClient.raffleResults()).thenReturn(raffleResults)
        val result = testWebClient.get().uri("/api/v1/raffleResults", 2).accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBody(object : ParameterizedTypeReference<List<RaffleResult>>() {})
                .returnResult()
        //.isEqualTo(raffleResults.collectList())
        val responseBody: List<RaffleResult>? = result.responseBody
        Assertions.assertEquals(raffleResults.collectList().block(), responseBody)

    }

}