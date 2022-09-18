package com.shivam.jokeapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shivam.jokeapp.data.models.Joke
import com.shivam.jokeapp.data.repository.JokeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JokeScreenViewModel @Inject constructor(private val repository: JokeRepository) :
    ViewModel() {

    private val _jokeListFlow = repository.getAllJokes()
    private val _errorMessageFlow = MutableStateFlow<String?>(null)
    val uiStateFlow: StateFlow<JokeScreenUiState> =
        combine(_jokeListFlow, _errorMessageFlow) { jokes, error ->
            if (error != null) {
                JokeScreenUiState(error = error)
            } else {
                JokeScreenUiState(list = jokes)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = JokeScreenUiState()
        )

    private var job: Job? = null

    fun initiateFetching() {
        println("JokeScreenViewModel.initiateFetching")
        stopFetching()
        job = viewModelScope.launch {
            while (true) {
                val count = (_jokeListFlow.firstOrNull() ?: emptyList()).size
                try {
                    repository.fetchNewJokes(count >= 10)
                } catch (e: Exception) {
                    _errorMessageFlow.emit(e.message)
                }
                delay(6 * 1000)
            }
        }
    }

    private fun stopFetching() {
        println("JokeScreenViewModel.stopFetching")
        job?.cancel()
        job = null
    }

    override fun onCleared() {
        super.onCleared()
        stopFetching()
    }
}

data class JokeScreenUiState(
    val list: List<Joke> = emptyList(),
    val error: String? = null,
)