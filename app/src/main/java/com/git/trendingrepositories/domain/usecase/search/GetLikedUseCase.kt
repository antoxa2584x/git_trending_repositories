package com.git.trendingrepositories.domain.usecase.search

import com.git.trendingrepositories.data.local.RepositoriesDatabase
import com.git.trendingrepositories.data.local.model.LikedEntity
import com.git.trendingrepositories.domain.model.search.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLikedUseCase @Inject constructor(private val database: RepositoriesDatabase) {
    suspend fun getLiked(repository: Repository): LikedEntity? {
        return database.dao.isLiked(repository.id)
    }

    fun getLiked(): Flow<List<LikedEntity>> {
        return database.dao.getLiked()
    }
}