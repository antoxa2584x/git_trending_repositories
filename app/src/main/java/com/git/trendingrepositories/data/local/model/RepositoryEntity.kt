package com.git.trendingrepositories.data.local.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RepositoryEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val fullName: String,
    @Embedded
    val owner: OwnerEntity,
    val description: String,
    val url: String,
    val stargazersCount: Int,
    val forksCount: Int,
    val language: String,
    val dateCreated: Long,
    val htmlUrl: String
)