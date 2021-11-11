package com.example.repository.repository

import com.example.model.AppState
import com.example.model.DataModel
import com.example.repository.datasource.DataSourceLocal

class RepositoryImplementationLocal(private val dataSource: DataSourceLocal<List<DataModel>>) :
    RepositoryLocal<List<DataModel>> {
    override suspend fun getData(word: String): List<DataModel> = dataSource.getData(word)
    override suspend fun saveToDB(appState: AppState) = dataSource.saveToDB(appState)
    override suspend fun getWord(word: String): DataModel = dataSource.getWord(word)
}