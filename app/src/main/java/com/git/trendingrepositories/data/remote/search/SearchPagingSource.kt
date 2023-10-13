package com.git.trendingrepositories.data.remote.search

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.git.trendingrepositories.data.remote.search.model.Repository
import com.git.trendingrepositories.domain.model.enums.ResultsOrder
import com.git.trendingrepositories.domain.model.enums.SortOrder
import com.git.trendingrepositories.domain.model.enums.SortPeriod
import com.git.trendingrepositories.utils.ext.toLocalDateString
import okio.IOException
import retrofit2.HttpException

class SearchPagingSource(
    private val service: SearchService,
    private val searchCondition: SortPeriod,
    private val sortOrder: SortOrder,
    private val resultsOrder: ResultsOrder
) : PagingSource<Int, Repository>() {

    companion object {
        private const val PAGE_INDEX = 0
    }

    override fun getRefreshKey(state: PagingState<Int, Repository>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repository> {
        val page = params.key ?: PAGE_INDEX
        return try {
            val response =
                service.getSearchRepositories(
                    search = searchCondition.toLocalDateString(),
                    page = page,
                    sort = sortOrder.value,
                    order = resultsOrder.value
                )
            val body = response.body()
            val repositories = body?.items ?: listOf()

//            LoadResult.Page(
//                data = repositories,
//                prevKey = null,
//                nextKey = null
//            )
//
            LoadResult.Page(
                data = repositories,
                prevKey = null,
                nextKey = if (body?.incompleteResults == false) page + 1 else null
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

}