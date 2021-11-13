package com.example.dictionary.main

import androidx.lifecycle.LiveData
import com.example.base.BaseViewModel
import com.example.model.AppState
import com.example.model.userdata.DataModel
import kotlinx.coroutines.launch

class MainViewModel(private val interactor: MainInteractor) : BaseViewModel<AppState>() {
    private val liveData: LiveData<AppState> = _mutableLiveData

    fun subscribe(): LiveData<AppState> {
        return liveData
    }

    override fun getData(word: String, isOnline: Boolean) {
        _mutableLiveData.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch { startInteractor(word, isOnline) }
    }

    private suspend fun startInteractor(word: String, isOnline: Boolean) = _mutableLiveData.postValue(
        com.example.utils.parseSearchResults(interactor.getData(word, isOnline))
    )

    suspend fun getWord(word: String) : DataModel {
        return interactor.getWord(word)
    }

    override fun handleError(error: Throwable) = _mutableLiveData.postValue(AppState.Error(error))

    override fun onCleared() {
        _mutableLiveData.value = AppState.Success(null)
        super.onCleared()
    }
}