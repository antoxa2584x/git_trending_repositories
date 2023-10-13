package com.git.trendingrepositories.data.local.model

data class Repository(
    var id: Int = 0,
    var name: String = "",
    var fullName: String = "",
    var private: Boolean = false,
    var owner: Owner = Owner(),
    var description: String? = "",
    var url: String = "",
    var stargazersCount: Int = 0,
    var forksCount: Int = 0,
    var language: String? = "",
    var dateCreated: String? = "",
    var htmlUrl: String? = "",
    var score: Int = 0
) {
    fun getShortDescription(): String {
        val descriptionLength = description?.length ?: 0
        val trimmedText =
            description?.substring(0, descriptionLength.coerceAtMost(MAX_TEXT_LENGTH))?.trim() ?: ""
        return "$trimmedText${if (descriptionLength > MAX_TEXT_LENGTH) "…" else ""}"
    }

    fun getLongDescription(): String {
        val descriptionLength = description?.length ?: 0
        val trimmedText =
            description?.substring(0, descriptionLength.coerceAtMost(MAX_LONG_TEXT_LENGTH))?.trim()
                ?: ""
        return "$trimmedText${if (descriptionLength > MAX_LONG_TEXT_LENGTH) "…" else ""}"
    }

    fun getSimpleDate() = dateCreated?.substringBefore("T") ?: ""

    companion object {
        const val MAX_TEXT_LENGTH = 100
        const val MAX_LONG_TEXT_LENGTH = 10000
    }
}