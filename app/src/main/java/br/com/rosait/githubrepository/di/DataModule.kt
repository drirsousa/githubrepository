package br.com.rosait.githubrepository.di

import br.com.rosait.githubrepository.data.repository.GithubRepository
import org.koin.dsl.module

val dataModule = module {
    factory { GithubRepository() }
}