package com.example.dictionary.view.main

import androidx.lifecycle.LiveData
import com.example.dictionary.model.data.AppState
import com.example.dictionary.utils.parseSearchResults
import com.example.dictionary.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel(private val interactor: MainInteractor) : BaseViewModel<AppState>() {
    fun subscribe(): LiveData<AppState> = liveData

    override fun getData(word: String, isOnline: Boolean) {
        liveData.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch { startInteractor(word, isOnline) }
    }

    private suspend fun startInteractor(word: String, isOnline: Boolean) = liveData.postValue(
        parseSearchResults(interactor.getData(word, isOnline))
    )

    override fun handleError(error: Throwable) = liveData.postValue(AppState.Error(error))

    override fun onCleared() {
        liveData.value = AppState.Success(null)
        super.onCleared()
    }
}