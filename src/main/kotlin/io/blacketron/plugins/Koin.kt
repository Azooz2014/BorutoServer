package io.blacketron.plugins

import io.blacketron.di.koinModule
import io.ktor.application.*
import org.koin.core.logger.Level
import org.koin.ktor.ext.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin(){
    install(Koin){
        slf4jLogger(level = Level.ERROR) /*setting level logger to Level.ERROR is workaround for
        "main" java.lang.NoSuchMethodError: kotlin.time.Duration.toDouble-impl(JLjava/util/concurrent/ TimeUnit;)D
        exception cause of something related to the koin library -_-.
        */
        modules(koinModule)
    }
}