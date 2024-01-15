package com.architecture.core.repository

import androidx.paging.PagingData
import com.architecture.core.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUserList(): Flow<PagingData<User>>

}
