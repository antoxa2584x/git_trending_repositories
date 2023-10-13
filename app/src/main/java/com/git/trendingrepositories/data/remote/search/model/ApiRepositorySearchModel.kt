package com.git.trendingrepositories.data.remote.search.model

import com.google.gson.annotations.SerializedName

data class ApiRepositorySearchModel(
    @SerializedName("total_count") var totalCount: Int? = null,
    @SerializedName("incomplete_results") var incompleteResults: Boolean = true,
    @SerializedName("items") var items: List<Repository> = listOf(),
)