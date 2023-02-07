package br.com.rosait.githubrepository

import android.app.Application
import br.com.rosait.githubrepository.di.githubRepositoryAppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class GithubRepositoryApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initilizeKoin()
    }

    private fun initilizeKoin() {
        startKoin {
            androidLogger()
            androidContext(this@GithubRepositoryApplication)
            modules(githubRepositoryAppModule)
        }
    }
}