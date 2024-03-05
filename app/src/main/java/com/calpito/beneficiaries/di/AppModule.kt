package com.calpito.beneficiaries.di

import com.calpito.beneficiaries.interfaces.RepositoryInterface
import com.calpito.beneficiaries.repository.RepositoryImpl

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRepository(repository: RepositoryImpl): RepositoryInterface {
        return repository
    }

}