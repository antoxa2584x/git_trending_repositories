package com.git.trendingrepositories.domain.usecase.search

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.git.trendingrepositories.data.local.RepositoriesDatabase
import com.git.trendingrepositories.data.local.model.LikedEntity
import com.git.trendingrepositories.data.local.model.RepositoryWithLike
import com.git.trendingrepositories.domain.model.search.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLikedUseCase @Inject constructor(private val database: RepositoriesDatabase) {
    suspend fun getLiked(repository: Repository): LikedEntity? {
        return database.dao.isLiked(repository.id)
    }

    fun getLiked(): Flow<PagingData<RepositoryWithLike>> {
       return Pager(PagingConfig(5), null) {
            database.dao.getSortedLiked()
        }.flow
    }
}