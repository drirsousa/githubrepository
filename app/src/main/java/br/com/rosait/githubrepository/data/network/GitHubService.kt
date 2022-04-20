package br.com.rosait.githubrespositories.data.network

import br.com.rosait.githubrespositories.data.model.ResponseResult
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubService {
    @GET("search/repositories")
    suspend fun loadRepositories(
        @Query("q") queryStringLanguage: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int): ResponseResult
}