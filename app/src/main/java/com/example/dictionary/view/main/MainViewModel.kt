package com.example.dictionary.view.main

import androidx.lifecycle.LiveData
import com.example.dictionary.model.data.AppState
import com.example.dictionary.model.datasource.DataSourceLocal
import com.example.dictionary.model.datasource.DataSourceRemote
import com.example.dictionary.model.repository.RepositoryImplementation
import com.example.dictionary.utils.parseSearchResults
import com.example.dictionary.viewmodel.BaseViewModel
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class MainViewModel @Inject constructor(private val interactor: MainInteractor) :
    BaseViewModel<AppState>() {
    private var appState: AppState? = null

    fun subscribe(): LiveData<AppState> {
        return liveData
    }

    override fun getData(word: String, isOnline: Boolean) {
        compositeDisposable.add(interactor.getData(word, isOnline)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .doOnSubscribe { liveData.value = AppState.Loading(null) }
            .subscribeWith(getObserver()))
    }

    private fun doOnSubscribe(): (Disposable) -> Unit = { liveData.value = AppState.Loading(null) }

    private fun getObserver(): DisposableObserver<AppState> {
        return object : DisposableObserver<AppState>() {
            override fun onNext(t: AppState) {
                appState = parseSearchResults(t)
                liveData.value = appState
            }

            override fun onError(e: Throwable) {
                liveData.value = AppState.Error(e)
            }

            override fun onComplete() {}
        }
    }
}