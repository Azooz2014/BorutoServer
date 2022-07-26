package io.blacketron.plugins

import io.blacketron.routes.getAllHeroes
import io.blacketron.routes.root
import io.blacketron.routes.searchHeroes
import io.ktor.application.*
import io.ktor.http.content.*
import io.ktor.routing.*

fun Application.configureRouting() {

    routing {
        root()
        getAllHeroes()
        searchHeroes()

        static("/images") {
            resources("images")
        }
    }
}
