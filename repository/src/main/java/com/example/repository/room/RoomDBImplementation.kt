package com.example.repository.room

import com.example.model.AppState
import com.example.model.DataModel
import com.example.repository.convertDataModelSuccessToEntity
import com.example.repository.datasource.DataSourceLocal
import com.example.repository.mapHistoryEntityToDataModel
import com.example.repository.mapHistoryEntityToSearchResult

class RoomDBImplementation(private val historyDAO: HistoryDAO) : DataSourceLocal<List<DataModel>> {
    override suspend fun getData(word: String): List<DataModel> =
        mapHistoryEntityToSearchResult(historyDAO.all())

    override suspend fun saveToDB(appState: AppState) {
        convertDataModelSuccessToEntity(appState)?.let { historyDAO.insert(it) }
    }

    override suspend fun getWord(word: String): DataModel {
        return mapHistoryEntityToDataModel(historyDAO.getDataByWord(word))
    }
}