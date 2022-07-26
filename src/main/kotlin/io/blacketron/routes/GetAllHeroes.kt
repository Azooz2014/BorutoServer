package io.blacketron.routes

import io.blacketron.model.ApiResponse
import io.blacketron.repository.HeroRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.getAllHeroes(){
    val heroRepository: HeroRepository by inject()

    get("/boruto/heroes"){
        try {
            val page = call.request.queryParameters["page"]?.toInt() ?: 1
            require(page in 1..5)

            val apiResponse = heroRepository.getHeroes(page = page)

            call.respond(
                message = apiResponse,
                status = HttpStatusCode.OK,
            )

        } catch (e: NumberFormatException){

            call.respond(
                message = ApiResponse(
                    success = false,
                    message = "Invalid page number",
                ),
                status = HttpStatusCode.BadRequest
            )
        } catch (e: IllegalArgumentException){
            call.respond(
                message = ApiResponse(
                    success = false,
                    message = "Page not found"
                ),
                status = HttpStatusCode.NotFound
            )
        }
    }
}