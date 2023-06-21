package com.sample.simpsonsviewer.ui.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.sample.network.SearchManager
import com.sample.simpsonsviewer.models.SearchResponse
import com.sample.simpsonsviewer.util.state.AwaitResult
import com.sample.simpsonsviewer.util.state.State
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import okhttp3.Response
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class MainViewModelTest : TestCase() {
    private val testDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var observer: Observer<in State<SearchResponse>>

    @Mock
    private lateinit var response: Response

    @get:Rule
    val testInstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var searchManager: SearchManager

    @Mock
    private lateinit var viewModel: MainViewModel

    private var successfulResponse: SearchResponse? = null
    private val json = Json {
        ignoreUnknownKeys = true
        allowStructuredMapKeys = true
        encodeDefaults = true
        explicitNulls = false
        isLenient = true
    }

    @Before
    public override fun setUp() {
        super.setUp()
        Dispatchers.setMain(testDispatcher)
        viewModel = MainViewModel(searchManager)
        viewModel.simpsonsSearchLiveData.observeForever(observer)

        successfulResponse = try {
            val stream = javaClass.getResourceAsStream("/search_response.json") ?: return
            json.decodeFromStream<SearchResponse>(stream)
        } catch (e: Exception) {
            null
        }
    }

    @Test
    fun shouldParseJsonAsResponse() {
        val successfulResponse = successfulResponse
        if (successfulResponse == null) {
            fail("successResponse is null")
            return
        }

        assert(successfulResponse.relatedTopics.isNotEmpty())
    }

    @Test
    fun shouldEmitSuccessState() = runBlocking {
        val successfulResponse = successfulResponse
        if (successfulResponse == null) {
            fail("successResponse is null")
            return@runBlocking
        }

        Mockito.`when`(searchManager.getSearchResults())
            .thenReturn(AwaitResult.Ok(successfulResponse, response))

        viewModel.search()
        Mockito.verify(observer).onChanged(State.Success(successfulResponse))
        viewModel.simpsonsSearchLiveData.removeObserver(observer)
    }

    @Test
    fun shouldEmitErrorState() = runBlocking {
        val exception = Exception()
        Mockito.`when`(searchManager.getSearchResults()).thenReturn(AwaitResult.Error(exception))

        viewModel.search()
        Mockito.verify(observer).onChanged(State.Error(exception))
        viewModel.simpsonsSearchLiveData.removeObserver(observer)
    }

    @After
    public override fun tearDown() {
        super.tearDown()
        Dispatchers.resetMain()
    }
}