package com.example.dictionary.main

import com.example.base.Interactor
import com.example.model.AppState
import com.example.model.dto.SearchResultDTO
import com.example.model.userdata.DataModel
import com.example.repository.repository.Repository
import com.example.repository.repository.RepositoryLocal
import com.example.utils.mapSearchResultToResult

class MainInteractor(
    private val remoteRepository: Repository<List<SearchResultDTO>>,
    private val localRepository: RepositoryLocal<List<SearchResultDTO>>
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        val appState: AppState
            if (fromRemoteSource) {
                appState = AppState.Success(mapSearchResultToResult(remoteRepository.getData(word)))
                localRepository.saveToDB(appState)
            }
            else appState = AppState.Success(mapSearchResultToResult(localRepository.getData(word)))
            return appState
    }

    suspend fun getWord(word: String) : DataModel {
        return localRepository.getWord(word)
    }
}
