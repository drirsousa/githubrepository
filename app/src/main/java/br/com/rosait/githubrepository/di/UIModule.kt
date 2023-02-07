package br.com.rosait.githubrepository.di

import br.com.rosait.githubrepository.viewmodel.RepositoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel {
        RepositoryViewModel(get())
    }
}