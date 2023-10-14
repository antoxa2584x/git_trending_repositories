package com.git.trendingrepositories.domain.model.search

import org.joda.time.DateTime


data class Repository(
    val id: Int,
    val name: String,
    val fullName: String,
    val owner: Owner,
    val description: String,
    val url: String,
    val stargazersCount: Int,
    val forksCount: Int,
    val language: String,
    val dateCreated: Long,
    val htmlUrl: String,
    var isLiked: Boolean = false,
) {
    fun getShortDescription(): String {
        val descriptionLength = description.length
        val trimmedText =
            description.substring(0, descriptionLength.coerceAtMost(MAX_TEXT_LENGTH)).trim()
        return "$trimmedText${if (descriptionLength > MAX_TEXT_LENGTH) "…" else ""}"
    }

    fun getLongDescription(): String {
        val descriptionLength = description.length
        val trimmedText =
            description.substring(0, descriptionLength.coerceAtMost(MAX_LONG_TEXT_LENGTH)).trim()
        return "$trimmedText${if (descriptionLength > MAX_LONG_TEXT_LENGTH) "…" else ""}"
    }

    fun getSimpleDate() = DateTime(dateCreated).toString("dd-MM-yyyy")

    companion object {
        const val MAX_TEXT_LENGTH = 100
        const val MAX_LONG_TEXT_LENGTH = 10000
    }
}