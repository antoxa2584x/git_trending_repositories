package com.git.trendingrepositories.data.remote.search

import com.git.trendingrepositories.data.remote.search.model.ApiRepositorySearchModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("search/repositories")
    suspend fun getSearchRepositories(
        @Query("q") search: String,
        @Query("page") page: Int = 0,
        @Query("sort") sort: String,
        @Query("order") order: String,
    ): Response<ApiRepositorySearchModel>
}