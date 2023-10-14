package com.git.trendingrepositories.domain.usecase.search

import com.git.trendingrepositories.data.local.RepositoriesDatabase
import com.git.trendingrepositories.data.local.model.LikedEntity
import com.git.trendingrepositories.domain.model.search.Repository
import javax.inject.Inject

class UpdateLikedUseCase @Inject constructor(private val database: RepositoriesDatabase) {
    suspend fun updateLiked(isLiked: Boolean, repository: Repository) {
        val likedEntity = LikedEntity(
            isLiked = isLiked,
            repositoryId = repository.id
        )
        return database.dao.upsert(likedEntity)
    }
}