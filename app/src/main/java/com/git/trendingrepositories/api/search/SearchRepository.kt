package com.git.trendingrepositories.api.search

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.git.trendingrepositories.api.search.model.Repository
import com.git.trendingrepositories.domain.ISearchRepository
import javax.inject.Inject

class SearchRepository @Inject constructor(private val service: SearchService) : ISearchRepository {
    override fun getSearchResult(): Pager<Int, Repository> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = { SearchPagingSource(service) }
        )
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 30 //Based on default git api response count
    }
}