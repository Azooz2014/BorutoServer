package io.blacketron.plugins

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*

fun Application.configureStatusPages(){
    install(StatusPages){
        status(HttpStatusCode.NotFound){
            status ->
            call.respond(status = status, "Page not found!")
        }
    }
}