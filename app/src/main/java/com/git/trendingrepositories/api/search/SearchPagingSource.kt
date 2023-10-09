package com.git.trendingrepositories.api.search

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.git.trendingrepositories.api.search.model.Repository
import okio.IOException
import retrofit2.HttpException

class SearchPagingSource(private val service: SearchService) : PagingSource<Int, Repository>() {

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
                    search = "",
                    page = page,
                    order = "stars",
                    sort = "desc"
                )
            val body = response.body()
            val repositories = body?.items ?: listOf()

            LoadResult.Page(
                data = repositories,
                prevKey = null,
                nextKey = null
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

}