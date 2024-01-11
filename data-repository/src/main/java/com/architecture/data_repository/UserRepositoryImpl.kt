package com.architecture.data_repository

import com.architecture.core.model.User
import com.architecture.core.repository.UserRepository
import com.architecture.core.state.DataState
import com.architecture.data_remote.api.UserApiModel
import com.architecture.data_remote.source.UserNetworkDataSource
import com.architecture.data_repository.model.asUiModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl @Inject constructor(private val userNetworkDataSource: UserNetworkDataSource) :
    UserRepository {

    override fun getUserList(): Flow<DataState<List<User>>> = flow {
        try {
            val userList = userNetworkDataSource.getAssetList().results.map(UserApiModel::asUiModel)
            emit(DataState.Success(userList))
        } catch (e: Exception) {
            println(e.message)
            // TODO: handle errors
        }
    }

}
