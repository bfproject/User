package com.architecture.data_repository.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.architecture.core.model.User
import com.architecture.data_remote.api.UserApiModel
import com.architecture.data_remote.source.UserNetworkDataSource
import com.architecture.data_repository.model.asUiModel

private const val STARTING_PAGE = 0
class SearchPagingSource(
    private val userNetworkDataSource: UserNetworkDataSource,
    private val pageSize: Int,
): PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        var page: Int = params.key ?: STARTING_PAGE

        return try {
            val response = userNetworkDataSource.getAssetList(page = page, results = pageSize)
            val userList = response.results.map(UserApiModel::asUiModel)
            page += 1

            LoadResult.Page(
                data = userList,
                prevKey = null, // Only paging forward
                nextKey = page
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return null
    }

}
