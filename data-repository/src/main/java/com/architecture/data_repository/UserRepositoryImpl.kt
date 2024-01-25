package com.architecture.data_repository

import android.annotation.SuppressLint
import com.architecture.core.model.User
import com.architecture.core.repository.UserRepository
import com.architecture.core.state.DataState
import com.architecture.data_remote.api.UserApiModel
import com.architecture.data_remote.source.UserNetworkDataSource
import com.architecture.data_repository.model.asUiModel
import com.architecture.data_repository.util.convertErrorBody
import java.net.UnknownHostException
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class UserRepositoryImpl @Inject constructor(private val userNetworkDataSource: UserNetworkDataSource) :
    UserRepository {

    @SuppressLint("NewApi")
    override fun getUserList(): Flow<DataState<List<User>>> = flow {
        try {
            val userList = userNetworkDataSource.getAssetList().results
                .distinctBy { it.email }
                .map(UserApiModel::asUiModel)
            emit(DataState.Success(userList))
        } catch (e: Exception) {
            when (e) {
                is UnknownHostException -> {
                    emit(DataState.Error.NoConnection(""))
                }

                is HttpException -> {
                    val errorResponse = convertErrorBody(e)
                    emit(DataState.Error.Http(errorResponse?.error))
                }

                else -> {
                    emit(DataState.Error.Generic(e.message))
                }
            }
        }
    }

}
