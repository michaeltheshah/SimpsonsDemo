package com.sample.network

import com.sample.simpsonsviewer.models.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface SearchService {
    @GET
    suspend fun getSearchResults(
        @Url url: String = "https://api.duckduckgo.com/?q=the+wire+characters&format=json"
    ): Response<SearchResponse>

    @GET
    suspend fun getDetail(@Url url: String): Response<SearchResponse>
}