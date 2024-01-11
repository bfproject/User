package com.architecture.data_remote.api

import retrofit2.http.GET

interface UserService {
    @GET("?inc=name,email,picture&results=50")
    suspend fun getUserList(): UserListApiModel

}
