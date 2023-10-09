package com.git.trendingrepositories.api.search

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.git.trendingrepositories.api.search.model.Repository
import com.git.trendingrepositories.domain.repository.ISearchRepository
import com.git.trendingrepositories.domain.model.enums.ResultsOrder
import com.git.trendingrepositories.domain.model.enums.SortOrder
import com.git.trendingrepositories.domain.model.enums.SortPeriod
import javax.inject.Inject

class SearchRepository @Inject constructor(private val service: SearchService) : ISearchRepository {
    override fun getSearchResult(
        searchCondition: SortPeriod,
        sortOrder: SortOrder,
        resultsOrder: ResultsOrder
    ): Pager<Int, Repository> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = { SearchPagingSource(service, searchCondition, sortOrder, resultsOrder) }
        )
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 30 //Based on default git api response count
    }
}