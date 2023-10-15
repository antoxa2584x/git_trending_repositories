package com.git.trendingrepositories.domain.repository

import androidx.paging.Pager
import com.git.trendingrepositories.data.local.model.RepositoryWithLike
import com.git.trendingrepositories.domain.model.enums.ResultsOrder
import com.git.trendingrepositories.domain.model.enums.SortOrder
import com.git.trendingrepositories.domain.model.enums.SortPeriod

interface ISearchRepository {
    fun getLocalSearchResult(
        searchCondition: SortPeriod,
        searchQuery: String,
        sortOrder: SortOrder,
        resultsOrder: ResultsOrder
    ): Pager<Int, RepositoryWithLike>
}