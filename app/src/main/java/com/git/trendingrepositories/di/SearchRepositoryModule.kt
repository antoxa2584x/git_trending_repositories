package com.git.trendingrepositories.di

import android.content.Context
import androidx.room.Room
import com.git.trendingrepositories.data.local.RepositoriesDatabase
import com.git.trendingrepositories.data.remote.search.SearchRepository
import com.git.trendingrepositories.data.remote.search.SearchService
import com.git.trendingrepositories.domain.repository.ISearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class SearchRepositoryModule {
    @Provides
    @Singleton
    fun provideRepositoriesDatabase(@ApplicationContext context: Context): RepositoriesDatabase {
        return Room.databaseBuilder(
            context,
            RepositoriesDatabase::class.java,
            "repositories.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideSearchRepository(
        searchService: SearchService,
        repositoriesDatabase: RepositoriesDatabase
    ): ISearchRepository {
        return SearchRepository(searchService,repositoriesDatabase)
    }
}