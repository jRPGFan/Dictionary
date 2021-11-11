package com.example.history.interactor

import com.example.base.Interactor
import com.example.model.AppState
import com.example.model.DataModel
import com.example.repository.repository.Repository
import com.example.repository.repository.RepositoryLocal

class HistoryInteractor(
    private val repositoryRemote: Repository<List<DataModel>>,
    private val repositoryLocal: RepositoryLocal<List<DataModel>>
) : Interactor<AppState> {
    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        return AppState.Success(
            if (fromRemoteSource) { repositoryRemote }
            else { repositoryLocal }.getData(word)
        )
    }
}