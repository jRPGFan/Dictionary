package com.example.dictionary.view.history

import com.example.dictionary.model.data.AppState
import com.example.dictionary.model.data.DataModel
import com.example.dictionary.model.repository.Repository
import com.example.dictionary.model.repository.RepositoryLocal
import com.example.dictionary.presenter.Interactor

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