package br.com.rosait.githubrepository.di

import org.koin.dsl.module

val githubRepositoryAppModule = module {
    includes(
        uiModule,
        dataModule
    )
}