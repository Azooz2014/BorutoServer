package io.blacketron

import io.blacketron.plugins.*
import io.ktor.application.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    configureKoin()
    configureMonitoring()
    configureRouting()
    configureSerialization()
    configureDefaultHeader()
    configureStatusPages()
}
