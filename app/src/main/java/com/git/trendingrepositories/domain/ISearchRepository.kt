package com.git.trendingrepositories.domain

import androidx.paging.Pager
import com.git.trendingrepositories.api.search.model.Repository

interface ISearchRepository {
    fun getSearchResult(): Pager<Int, Repository>
}