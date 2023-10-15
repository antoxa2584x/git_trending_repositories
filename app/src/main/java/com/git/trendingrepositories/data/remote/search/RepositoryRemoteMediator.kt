package com.git.trendingrepositories.data.remote.search

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.git.trendingrepositories.data.local.RepositoriesDatabase
import com.git.trendingrepositories.data.local.model.RepositoryWithLike
import com.git.trendingrepositories.data.mappers.toRepositoryEntity
import com.git.trendingrepositories.domain.model.enums.ResultsOrder
import com.git.trendingrepositories.domain.model.enums.SortOrder
import com.git.trendingrepositories.domain.model.enums.SortPeriod
import com.git.trendingrepositories.utils.ext.toLocalDateString
import okio.IOException
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class RepositoryRemoteMediator(
    private val repositoriesDatabase: RepositoriesDatabase,
    private val searchService: SearchService,
    private val searchCondition: SortPeriod,
    private val sortOrder: SortOrder,
    private val resultsOrder: ResultsOrder,
    private val searchQuery: String
) : RemoteMediator<Int, RepositoryWithLike>() {

    private var page = 1

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RepositoryWithLike>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                LoadType.APPEND -> {
                    page++
                }
            }

            val request = searchService.getSearchRepositories(
                search = "${if (searchQuery.isNotEmpty()) "$searchQuery+" else ""}created:>${searchCondition.toLocalDateString()}",
                page = loadKey,
                sort = resultsOrder.value,
                order = sortOrder.value
            )

            val code = request.code()
            val searchResponse = request.body()

            if (code == 403) {
                return MediatorResult.Error(Exception())
            }

            repositoriesDatabase.withTransaction {
                val entities = searchResponse?.items?.map { it.toRepositoryEntity() }
                entities?.let { repositoriesDatabase.dao.upsertAll(it) }
            }

            MediatorResult.Success(
                endOfPaginationReached = code == 422,
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}