package com.git.trendingrepositories.data.remote.search.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class RepositoryDto(
    @SerializedName("id") var id: Int = 0,
    @SerializedName("name") var name: String = "",
    @SerializedName("full_name") var fullName: String = "",
    @SerializedName("private") var private: Boolean = false,
    @SerializedName("owner") var owner: OwnerDto = OwnerDto(),
    @SerializedName("description") var description: String? = "",
    @SerializedName("url") var url: String = "",
    @SerializedName("stargazers_count") var stargazersCount: Int = 0,
    @SerializedName("forks_count") var forksCount: Int = 0,
    @SerializedName("language") var language: String? = "",
    @SerializedName("created_at") var dateCreated: Date,
    @SerializedName("html_url") var htmlUrl: String = "",
    @SerializedName("score") var score: Int = 0
)