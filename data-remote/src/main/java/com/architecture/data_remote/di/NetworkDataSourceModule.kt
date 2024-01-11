package com.architecture.data_remote.di

import com.architecture.data_remote.source.UserNetworkDataSource
import com.architecture.data_remote.source.UserNetworkDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkDataSourceModule {

    @Binds
    abstract fun bindUserNetworkDataSource(
        userNetworkDataSourceImpl: UserNetworkDataSourceImpl
    ): UserNetworkDataSource

}
