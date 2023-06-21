package com.sample.simpsonsviewer.hilt.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sample.network.SearchManager
import com.sample.network.SearchService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.*
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Provides
    @Singleton
    fun providesJson(): Json = Json {
        ignoreUnknownKeys = true
        allowStructuredMapKeys = true
        encodeDefaults = true
        explicitNulls = false
        isLenient = true
    }

    @Provides
    @Singleton
    fun providesRetrofit(json: Json): Retrofit {
        val client = OkHttpClient.Builder().build()
        val contentType = "application/json".toMediaType()

        return Retrofit.Builder()
            .baseUrl("https://api.duckduckgo.com/")
            .client(client)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun providesSimpsonsSearchService(retrofit: Retrofit): SearchService = retrofit.create(
        SearchService::class.java
    )

    @Provides
    @Singleton
    fun providesSimpsonsSearchManager(service: SearchService) = SearchManager(
        service
    )
}