package com.git.trendingrepositories.domain.usecase.search

import androidx.paging.map
import com.git.trendingrepositories.domain.model.enums.ResultsOrder
import com.git.trendingrepositories.domain.model.enums.SortOrder
import com.git.trendingrepositories.domain.model.enums.SortPeriod
import com.git.trendingrepositories.domain.repository.ISearchRepository
import kotlinx.coroutines.flow.filter
import javax.inject.Inject

class GetRepositoriesUseCase @Inject constructor(private val repository: ISearchRepository) {

    fun getRepositoriesLocalFlow(sort: SortPeriod) =
        repository.getLocalSearchResult(sort, SortOrder.STARS, ResultsOrder.DESC).flow
}