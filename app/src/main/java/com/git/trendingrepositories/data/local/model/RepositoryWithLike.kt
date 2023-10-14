package com.git.trendingrepositories.data.local.model

import androidx.room.Embedded
import androidx.room.Relation

data class RepositoryWithLike(
    @Embedded
    val repository: RepositoryEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "repositoryId"
    )
    val likedEntity: LikedEntity?
)