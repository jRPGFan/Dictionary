package com.example.history.ui

import androidx.lifecycle.LiveData
import com.example.base.BaseViewModel
import com.example.history.interactor.HistoryInteractor
import com.example.model.AppState
import com.example.utils.parseLocalSearchResults
import kotlinx.coroutines.launch

class HistoryViewModel(private val interactor: HistoryInteractor) : BaseViewModel<AppState>() {
    private val liveData: LiveData<AppState> = _mutableLiveData

    fun subscribe(): LiveData<AppState> = liveData

    override fun getData(word: String, isOnline: Boolean) {
        _mutableLiveData.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch { startInteractor(word, isOnline) }
    }

    private suspend fun startInteractor(word: String, isOnline: Boolean) =
        _mutableLiveData.postValue(parseLocalSearchResults(interactor.getData(word, isOnline)))

    override fun handleError(error: Throwable) = _mutableLiveData.postValue(AppState.Error(error))

    override fun onCleared() {
        _mutableLiveData.value = AppState.Success(null)
        super.onCleared()
    }
}