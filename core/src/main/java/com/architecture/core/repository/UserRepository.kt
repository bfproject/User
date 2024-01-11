package com.architecture.core.repository

import com.architecture.core.model.User
import com.architecture.core.state.DataState
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUserList(): Flow<DataState<List<User>>>

}
