package io.blacketron.routes

import io.blacketron.repository.HeroRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.searchHeroes(){

    val heroRepository: HeroRepository by inject()

    get("/boruto/heroes/search") {
        val query = call.request.queryParameters["name"]

        val apiResponse = heroRepository.searchHeroes(name = query)

        call.respond(
            message = apiResponse,
            status = HttpStatusCode.OK
        )
    }
}