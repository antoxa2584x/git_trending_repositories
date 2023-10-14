package com.git.trendingrepositories.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.git.trendingrepositories.data.local.model.LikedEntity
import com.git.trendingrepositories.data.local.model.RepositoryEntity

@Database(
    entities = [RepositoryEntity::class, LikedEntity::class],
    version = 2,
    exportSchema = false
)
abstract class RepositoriesDatabase: RoomDatabase() {

    abstract val dao: RepositoriesDao
}