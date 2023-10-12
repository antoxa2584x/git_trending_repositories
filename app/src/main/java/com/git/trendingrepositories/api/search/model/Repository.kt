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
) {
    fun getShortDescription(): String {
        val descriptionLength = description?.length ?: 0
        val trimmedText =
            description?.substring(0, descriptionLength.coerceAtMost(MAX_TEXT_LENGTH))?.trim() ?: ""
        return "$trimmedText${if (descriptionLength > MAX_TEXT_LENGTH) "…" else ""}"
    }

    companion object {
        const val MAX_TEXT_LENGTH = 100
    }
}