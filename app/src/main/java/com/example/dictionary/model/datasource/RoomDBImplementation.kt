package com.example.dictionary.model.datasource

import com.example.dictionary.model.data.DataModel

class RoomDBImplementation : DataSource<List<DataModel>> {
    override suspend fun getData(word: String): List<DataModel> {
        TODO("Not yet implemented")
    }
}