package com.samfonsec.fuzecs.di

import com.samfonsec.fuzecs.data.api.MatchApi
import com.samfonsec.fuzecs.data.dataSource.MatchDataSource
import com.samfonsec.fuzecs.data.repository.MatchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object MatchModule {

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): MatchApi = retrofit.create(MatchApi::class.java)

    @Provides
    @Singleton
    fun provideRepository(api: MatchApi): MatchRepository = MatchDataSource(api)
}