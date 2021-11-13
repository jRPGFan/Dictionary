package com.example.repository.room

import com.example.model.AppState
import com.example.model.dto.SearchResultDTO
import com.example.model.userdata.DataModel
import com.example.repository.convertDataModelSuccessToEntity
import com.example.repository.datasource.DataSourceLocal
import com.example.repository.mapHistoryEntityToDataModel
import com.example.repository.mapHistoryEntityToSearchResult

class RoomDBImplementation(private val historyDAO: HistoryDAO) : DataSourceLocal<List<SearchResultDTO>> {
    override suspend fun getData(word: String): List<SearchResultDTO> =
        mapHistoryEntityToSearchResult(historyDAO.all())

    override suspend fun saveToDB(appState: AppState) {
        convertDataModelSuccessToEntity(appState)?.let { historyDAO.insert(it) }
    }

    override suspend fun getWord(word: String): DataModel {
        return mapHistoryEntityToDataModel(historyDAO.getDataByWord(word))
    }
}