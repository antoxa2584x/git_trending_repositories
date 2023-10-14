package com.git.trendingrepositories.data.remote.search

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.git.trendingrepositories.data.local.RepositoriesDatabase
import com.git.trendingrepositories.data.local.model.RepositoryEntity
import com.git.trendingrepositories.data.local.model.RepositoryWithLike
import com.git.trendingrepositories.data.remote.search.model.RepositoryDto
import com.git.trendingrepositories.domain.model.enums.ResultsOrder
import com.git.trendingrepositories.domain.model.enums.SortOrder
import com.git.trendingrepositories.domain.model.enums.SortPeriod
import com.git.trendingrepositories.domain.repository.ISearchRepository
import com.git.trendingrepositories.utils.ext.toLocalDateLong
import com.git.trendingrepositories.utils.ext.toLocalDateString
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val service: SearchService,
    private val database: RepositoriesDatabase
) : ISearchRepository {
    override fun getSearchResult(
        searchCondition: SortPeriod,
        sortOrder: SortOrder,
        resultsOrder: ResultsOrder
    ): Pager<Int, RepositoryDto> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = {
                SearchPagingSource(
                    service,
                    searchCondition,
                    sortOrder,
                    resultsOrder
                )
            }
        )
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getLocalSearchResult(
        searchCondition: SortPeriod,
        sortOrder: SortOrder,
        resultsOrder: ResultsOrder
    ): Pager<Int, RepositoryWithLike> {
        return Pager(
            config = PagingConfig(pageSize = 30),
            remoteMediator = RepositoryRemoteMediator(
                repositoriesDatabase = database,
                searchService = service,
                searchCondition = searchCondition,
                sortOrder = sortOrder,
                resultsOrder = resultsOrder
            ),
            pagingSourceFactory = {
                database.dao.pagingSource(searchCondition.toLocalDateLong())
            }
        )
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 30 //Based on default git api response count
    }
}