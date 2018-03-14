package com.codependent.reactivegames.route

import com.codependent.reactivegames.client.RaffleClient
import com.codependent.reactivegames.dto.Game
import com.codependent.reactivegames.dto.Player
import com.codependent.reactivegames.dto.RaffleResult
import com.codependent.reactivegames.repository.GamesRepository
import com.codependent.reactivegames.repository.PlayersRepository
import com.codependent.reactivegames.web.handler.ApiHandlers
import com.codependent.reactivegames.web.route.ApiRoutes
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
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
    private lateinit var gamesRepository: GamesRepository
    @MockBean
    private lateinit var playersRepository: PlayersRepository
    @MockBean
    private lateinit var raffleClient: RaffleClient

    private lateinit var testWebClient: WebTestClient

    @BeforeEach
    fun setUp() {
        testWebClient = WebTestClient.bindToApplicationContext(context).build()
    }

    @Test
    fun shouldGetGames() {
        val games = Flux.just(Game("Maniac Mansion"), Game("The Secret of Monkey Island"))
        Mockito.`when`(gamesRepository.findAll()).thenReturn(games)
        val body = testWebClient.get().uri("/api/v1/games", 2).accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBody(object : ParameterizedTypeReference<List<Game>>() {})
        //.isEqualTo(customerMap.findAll("Peter"))
        println(body)
    }

    @Test
    fun shouldGetPlayers() {
        val players = Flux.just(Player("Jose"), Player("John"))
        Mockito.`when`(playersRepository.findAll()).thenReturn(players)
        val body = testWebClient.get().uri("/api/v1/players", 2).accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBody(object : ParameterizedTypeReference<List<Player>>() {})
        //.isEqualTo(customerMap.findAll("Peter"))
        println(body)
    }

    @Test
    fun shouldGetRaffleResults() {
        val raffleResults = Flux.just(RaffleResult(Game("Maniac Mansion"), Player("Jose")))
        Mockito.`when`(raffleClient.raffleResults()).thenReturn(raffleResults)
        val body = testWebClient.get().uri("/api/v1/raffleResults", 2).accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBody(object : ParameterizedTypeReference<List<RaffleResult>>() {})
        //.isEqualTo(customerMap.findAll("Peter"))
        println(body)
    }

}