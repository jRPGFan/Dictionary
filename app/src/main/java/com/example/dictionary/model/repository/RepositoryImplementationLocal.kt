package com.example.dictionary.model.repository

import com.example.dictionary.model.data.AppState
import com.example.dictionary.model.data.DataModel
import com.example.dictionary.model.datasource.DataSourceLocal

class RepositoryImplementationLocal(private val dataSource: DataSourceLocal<List<DataModel>>) :
    RepositoryLocal<List<DataModel>> {
    override suspend fun getData(word: String): List<DataModel> = dataSource.getData(word)
    override suspend fun saveToDB(appState: AppState) = dataSource.saveToDB(appState)
    override suspend fun getWord(word: String): DataModel = dataSource.getWord(word)
}