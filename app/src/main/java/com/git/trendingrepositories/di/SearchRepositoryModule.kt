package com.git.trendingrepositories.di

import com.git.trendingrepositories.data.remote.search.SearchRepository
import com.git.trendingrepositories.data.remote.search.SearchService
import com.git.trendingrepositories.domain.repository.ISearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class SearchRepositoryModule {
    @Singleton
    @Provides
    fun provideSearchRepository(searchService: SearchService): ISearchRepository {
        return SearchRepository(searchService)
    }
}