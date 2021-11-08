package com.example.dictionary.model.datasource

import com.example.dictionary.model.data.AppState
import com.example.dictionary.model.data.DataModel
import com.example.dictionary.room.HistoryDAO
import com.example.dictionary.utils.convertDataModelSuccessToEntity
import com.example.dictionary.utils.mapHistoryEntityToDataModel
import com.example.dictionary.utils.mapHistoryEntityToSearchResult

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