package com.architecture.data_repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.architecture.core.model.User
import com.architecture.core.repository.UserRepository
import com.architecture.data_remote.source.UserNetworkDataSource
import com.architecture.data_repository.pagingsource.SearchPagingSource
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl @Inject constructor(private val userNetworkDataSource: UserNetworkDataSource) :
    UserRepository {

    override fun getUserList(): Flow<PagingData<User>> {
        val pageSize = 20

        return Pager(
            config = PagingConfig(pageSize = pageSize),
            pagingSourceFactory = { SearchPagingSource(userNetworkDataSource, pageSize) }
        ).flow
    }

}
