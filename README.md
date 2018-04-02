# reactive-games

[![Build Status](https://semaphoreci.com/api/v1/codependent/reactive-games/branches/master/badge.svg)](https://semaphoreci.com/codependent/reactive-games)

Spring Boot 2.0 Kotlin Project to showcase a Reactive API with functional endpoints.

It consists of 3 services:

- Games: `http://localhost:8081/api/v1/games`
- Players: `http://localhost:8082/api/v1/players`
- Raffles: `http://localhost:8081/api/v1/raffleResults` - This endpoint uses a `webClient` to make parallel calls to the other 
two services and aggregate their outputs with `Flux.zip()`.
