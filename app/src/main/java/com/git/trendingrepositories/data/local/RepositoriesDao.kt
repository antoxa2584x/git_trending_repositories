package com.git.trendingrepositories.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.git.trendingrepositories.data.local.model.LikedEntity
import com.git.trendingrepositories.data.local.model.RepositoryEntity
import com.git.trendingrepositories.data.local.model.RepositoryWithLike
import kotlinx.coroutines.flow.Flow

@Dao
interface RepositoriesDao {

    @Upsert
    suspend fun upsertAll(entities: List<RepositoryEntity>)

    @Update
    suspend fun upsert(entities: RepositoryEntity)

    @Upsert
    suspend fun upsert(likedEntity: LikedEntity)

    @Query("SELECT * FROM likedentity WHERE repositoryId = :id ")
    suspend fun isLiked(id: Int): LikedEntity?

    @Transaction
    @Query("SELECT * FROM likedentity JOIN repositoryentity ON id = repositoryId WHERE isLiked = 1 ORDER BY stargazersCount DESC")
    fun getSortedLiked(): PagingSource<Int, RepositoryWithLike>

    @Transaction
    @Query("SELECT * FROM repositoryentity WHERE dateCreated > :date ORDER BY stargazersCount DESC")
    fun pagingSource(date: Long): PagingSource<Int, RepositoryWithLike>

//    @Query("SELECT * FROM repositoryentity WHERE dateCreated > :date ORDER BY stargazersCount DESC")
//    fun pagingSource(date: Long): PagingSource<Int, RepositoryEntity>

    @Insert
    fun insert(repositoryEntity: RepositoryEntity)

    @Delete
    fun delete(repositoryEntity: RepositoryEntity)

    @Query("DELETE FROM repositoryentity")
    suspend fun clearAll()
}