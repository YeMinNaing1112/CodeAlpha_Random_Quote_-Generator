package com.yeminnaing.randomquotegenerator.di

import com.yeminnaing.randomquotegenerator.ApiService
import com.yeminnaing.randomquotegenerator.repositories.GetQuoteRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }


    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(
                GsonConverterFactory.create()
            ).build()
    }

    @Provides
    @Singleton
    fun provideApiService(
        retrofit:Retrofit
    ):ApiService{
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideGetQuoteRepo(
        apiService: ApiService
    ):GetQuoteRepo{
        return GetQuoteRepo(apiService)
    }

}