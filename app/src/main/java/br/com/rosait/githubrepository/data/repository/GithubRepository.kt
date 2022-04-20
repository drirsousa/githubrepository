package br.com.rosait.githubrepository.data.repository

import br.com.rosait.githubrespositories.data.model.ResponseResult
import br.com.rosait.githubrespositories.data.network.GitHubService
import br.com.rosait.githubrespositories.data.network.RestManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GithubRepository {
    val service: GitHubService by lazy {
        RestManager.retrofit.create(
            GitHubService::class.java
        )
    }

    suspend fun loadRepositories(language: String, sort: String, page: Int, perPage: Int) : ResponseResult {
        return withContext(Dispatchers.IO) {
            with(service.loadRepositories(language, sort, page, perPage)) {
                this
            }
        }
    }
}