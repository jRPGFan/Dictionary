package com.example.history.interactor

import com.example.base.Interactor
import com.example.model.AppState
import com.example.model.dto.SearchResultDTO
import com.example.repository.repository.Repository
import com.example.repository.repository.RepositoryLocal
import com.example.utils.mapSearchResultToResult

class HistoryInteractor(
    private val repositoryRemote: Repository<List<SearchResultDTO>>,
    private val repositoryLocal: RepositoryLocal<List<SearchResultDTO>>
) : Interactor<AppState> {
    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        return AppState.Success(
            mapSearchResultToResult(
            if (fromRemoteSource) { repositoryRemote }
            else { repositoryLocal }.getData(word)
            )
        )
    }
}