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
    /*@Provides
    @Singleton
    fun provideMealApi(mealApi: MealApiImpl):MealApiInterface{
        return mealApi
    }*/

    @Provides
    @Singleton
    fun provideRepository(repository: RepositoryImpl): RepositoryInterface {
        return repository
    }

   /* @Provides
    @Singleton
    fun provideRealmConfig(): RealmConfiguration {
        val realmConfig = RealmConfiguration.Builder(
            schema = setOf(
                MealCategoryRealm::class,
            ),
        )
            .deleteRealmIfMigrationNeeded()
            .build()
        return realmConfig
    }*/

    /*@Provides
    @Singleton
    fun provideLocalDb(localDb:LocalDbImpl):LocalDbInterface{
        return localDb
    }*/
}