package com.architecture.data_remote.api

import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {
    @GET("?inc=name,email,picture&seed=abc")
    suspend fun getUserList(
        @Query("page") page: Int,
        @Query("results") results: Int,
    ): UserListApiModel

}
