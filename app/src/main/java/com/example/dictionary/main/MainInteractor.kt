package com.example.dictionary.main

import com.example.base.Interactor
import com.example.model.AppState
import com.example.model.DataModel
import com.example.repository.repository.Repository
import com.example.repository.repository.RepositoryLocal

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
