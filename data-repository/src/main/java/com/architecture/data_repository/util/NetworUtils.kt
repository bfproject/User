package com.architecture.data_repository.util

import com.architecture.data_remote.api.ErrorResponse
import com.squareup.moshi.Moshi
import retrofit2.HttpException

fun convertErrorBody(throwable: HttpException): ErrorResponse? {
    return try {
        throwable.response()?.errorBody()?.source()?.let {
            val moshiAdapter = Moshi.Builder().build().adapter(ErrorResponse::class.java)
            moshiAdapter.fromJson(it)
        }
    } catch (exception: Exception) {
        null
    }
}
