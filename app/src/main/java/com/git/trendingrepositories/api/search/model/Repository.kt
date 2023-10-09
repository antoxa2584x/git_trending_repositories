package com.git.trendingrepositories.api.search.model

import com.google.gson.annotations.SerializedName

data class Repository(
    @SerializedName("id") var id: Int = 0,
    @SerializedName("name") var name: String = "",
    @SerializedName("full_name") var fullName: String = "",
    @SerializedName("private") var private: Boolean = false,
    @SerializedName("owner") var owner: Owner = Owner(),
    @SerializedName("description") var description: String? = "",
    @SerializedName("url") var url: String = "",
    @SerializedName("stargazers_count") var stargazersCount: Int = 0,
    @SerializedName("language") var language: String = "",
    @SerializedName("score") var score: Int = 0

)