package com.example.dictionary.model.datasource

import com.example.dictionary.model.data.DataModel

class DataSourceLocal(private val localProvider: RoomDBImplementation = RoomDBImplementation()) :
    DataSource<List<DataModel>> {
    override suspend fun getData(word: String): List<DataModel> = localProvider.getData(word)
}