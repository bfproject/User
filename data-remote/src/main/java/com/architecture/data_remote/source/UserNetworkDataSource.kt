package com.architecture.data_remote.source

import com.architecture.data_remote.api.UserListApiModel

interface UserNetworkDataSource {
    suspend fun getAssetList(page: Int, results: Int): UserListApiModel
}
