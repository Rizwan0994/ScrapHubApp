package com.example.scrapproject.di


import com.example.scrapproject.api.UserAPI
import com.example.scrapproject.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit():Retrofit{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }
    @Singleton
    @Provides
    fun providesUserAPI(retrofit: Retrofit):UserAPI{
        return retrofit.create(UserAPI::class.java)
    }
}