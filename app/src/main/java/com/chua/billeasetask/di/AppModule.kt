package com.chua.billeasetask.di

import com.chua.billeasetask.data.remote.AuthorizationApi
import com.chua.billeasetask.data.repository.AuthorizationRepositoryImpl
import com.chua.billeasetask.domain.repository.AuthorizationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApi(): AuthorizationApi {
        val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        return Retrofit.Builder()
            .baseUrl(AuthorizationApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(AuthorizationApi::class.java)
    }

    @Provides
    @Singleton
    fun authorizationRepository(api: AuthorizationApi): AuthorizationRepository {
        return AuthorizationRepositoryImpl(api)
    }

}