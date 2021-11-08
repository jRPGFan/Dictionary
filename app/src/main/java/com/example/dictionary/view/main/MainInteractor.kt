package com.example.dictionary.view.main

import com.example.dictionary.model.data.AppState
import com.example.dictionary.model.data.DataModel
import com.example.dictionary.model.repository.Repository
import com.example.dictionary.model.repository.RepositoryLocal
import com.example.dictionary.presenter.Interactor

class MainInteractor(
    private val remoteRepository: Repository<List<DataModel>>,
    private val localRepository: RepositoryLocal<List<DataModel>>
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        val appState: AppState
            if (fromRemoteSource) {
                appState = AppState.Success(remoteRepository.getData(word))
                localRepository.saveToDB(appState)
            }
            else appState = AppState.Success(localRepository.getData(word))
            return appState
    }

    suspend fun getWord(word: String) : DataModel {
        return localRepository.getWord(word)
    }
}
