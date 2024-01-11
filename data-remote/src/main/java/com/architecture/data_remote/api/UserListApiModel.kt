package com.architecture.data_remote.api

/**
 * Data class of the Network response
 */
data class UserListApiModel(
    val info: Info,
    val results: List<UserApiModel>
)
