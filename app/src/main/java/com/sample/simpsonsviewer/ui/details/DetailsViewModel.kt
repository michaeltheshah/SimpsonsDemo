package com.sample.simpsonsviewer.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.network.SearchManager
import com.sample.simpsonsviewer.models.SearchResponse
import com.sample.simpsonsviewer.util.extensions.toLiveData
import com.sample.simpsonsviewer.util.state.AwaitResult
import com.sample.simpsonsviewer.util.state.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val manager: SearchManager): ViewModel() {
    private val _responseLiveData: MutableLiveData<State<SearchResponse>> = MutableLiveData()
    val responseLiveData = _responseLiveData.toLiveData()

    fun getDetails(url: String) {
        _responseLiveData.value = State.Loading

        viewModelScope.launch {
            when (val result = manager.getDetails(url)) {
                is AwaitResult.Ok -> {
                    _responseLiveData.value = State.Success(result.value)
                }
                is AwaitResult.Error -> {
                    _responseLiveData.value = State.Error(result.exception)
                }
            }
        }
    }
}