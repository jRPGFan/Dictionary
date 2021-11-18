package com.example.repository.repository

import com.example.model.AppState
import com.example.model.dto.SearchResultDTO
import com.example.model.userdata.DataModel
import com.example.repository.datasource.DataSourceLocal

class RepositoryImplementationLocal(private val dataSource: DataSourceLocal<List<SearchResultDTO>>) :
    RepositoryLocal<List<SearchResultDTO>> {
    override suspend fun getData(word: String): List<SearchResultDTO> = dataSource.getData(word)
    override suspend fun saveToDB(appState: AppState) = dataSource.saveToDB(appState)
    override suspend fun getWord(word: String): DataModel = dataSource.getWord(word)
}