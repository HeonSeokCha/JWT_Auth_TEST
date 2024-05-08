package com.chs.jwt_auth_test.di

import com.chs.jwt_auth_test.data.FakeRepositoryImpl
import com.chs.jwt_auth_test.domain.FakeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindFakeRepository(
        fakeRepositoryImpl: FakeRepositoryImpl
    ): FakeRepository
}