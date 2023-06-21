package com.sample.network

import com.sample.simpsonsviewer.models.SearchResponse
import com.sample.simpsonsviewer.util.extensions.awaitResult
import com.sample.simpsonsviewer.util.state.AwaitResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchManager @Inject constructor(private val service: SearchService) {
    suspend fun getSearchResults(): AwaitResult<SearchResponse> =
        withContext(Dispatchers.IO) { service.getSearchResults().awaitResult() }

    suspend fun getDetails(url: String): AwaitResult<SearchResponse> =
        withContext(Dispatchers.IO) { service.getDetail(url).awaitResult() }
}