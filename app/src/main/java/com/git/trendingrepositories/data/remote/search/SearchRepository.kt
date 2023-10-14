package com.git.trendingrepositories.data.remote.search

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.git.trendingrepositories.data.local.RepositoriesDatabase
import com.git.trendingrepositories.data.local.model.RepositoryWithLike
import com.git.trendingrepositories.domain.model.enums.ResultsOrder
import com.git.trendingrepositories.domain.model.enums.SortOrder
import com.git.trendingrepositories.domain.model.enums.SortPeriod
import com.git.trendingrepositories.domain.repository.ISearchRepository
import com.git.trendingrepositories.utils.ext.toLocalDateLong
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val service: SearchService,
    private val database: RepositoriesDatabase
) : ISearchRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getLocalSearchResult(
        searchCondition: SortPeriod,
        sortOrder: SortOrder,
        resultsOrder: ResultsOrder
    ): Pager<Int, RepositoryWithLike> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE),
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