package io.blacketron.di

import io.blacketron.repository.HeroRepository
import io.blacketron.repository.HeroRepositoryImpl
import org.koin.dsl.module

val koinModule = module {
    single<HeroRepository> {
        HeroRepositoryImpl()
    }
}