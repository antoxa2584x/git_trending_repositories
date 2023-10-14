package com.git.trendingrepositories.data.remote.search.model

import com.google.gson.annotations.SerializedName


data class OwnerDto(
    @SerializedName("login") var login: String = "",
    @SerializedName("id") var id: Int = 0,
    @SerializedName("avatar_url") var avatarUrl: String = "",
    @SerializedName("url") var url: String= "",
)