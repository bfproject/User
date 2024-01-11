package com.architecture.data_remote.source

import com.architecture.data_remote.api.UserListApiModel
import com.architecture.data_remote.api.UserService
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserNetworkDataSourceImpl @Inject constructor(private val userService: UserService) : UserNetworkDataSource {

    override suspend fun getAssetList(): UserListApiModel = withContext(Dispatchers.IO) {
        userService.getUserList()
    }


}
