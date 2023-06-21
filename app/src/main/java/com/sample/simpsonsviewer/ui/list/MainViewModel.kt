package com.sample.simpsonsviewer.ui.list

import androidx.lifecycle.*
import com.sample.network.SearchManager
import com.sample.simpsonsviewer.models.SearchResponse
import com.sample.simpsonsviewer.util.extensions.toLiveData
import com.sample.simpsonsviewer.util.state.AwaitResult
import com.sample.simpsonsviewer.util.state.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val manager: SearchManager): ViewModel() {
    private val _simpsonsSearchLiveData: MutableLiveData<State<SearchResponse>> = MutableLiveData()
    val simpsonsSearchLiveData = _simpsonsSearchLiveData.toLiveData()

    fun search() {
        _simpsonsSearchLiveData.value = State.Loading

        viewModelScope.launch {
            when (val result = manager.getSearchResults()) {
                is AwaitResult.Ok -> {
                    _simpsonsSearchLiveData.value = State.Success(result.value)
                }
                is AwaitResult.Error -> {
                    _simpsonsSearchLiveData.value = State.Error(result.exception)
                }
            }
        }
    }
}