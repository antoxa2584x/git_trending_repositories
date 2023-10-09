package com.git.trendingrepositories.domain.usecase.search

import com.git.trendingrepositories.domain.model.enums.ResultsOrder
import com.git.trendingrepositories.domain.model.enums.SortOrder
import com.git.trendingrepositories.domain.model.enums.SortPeriod
import com.git.trendingrepositories.domain.repository.ISearchRepository
import javax.inject.Inject

class GetRepositoriesUseCase @Inject constructor(private val repository: ISearchRepository) {
    fun getRepositoriesFlow(sort: SortPeriod) =
        repository.getSearchResult(sort, SortOrder.STARS, ResultsOrder.DESC).flow
}