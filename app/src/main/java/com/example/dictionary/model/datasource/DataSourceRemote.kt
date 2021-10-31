package com.example.dictionary.model.datasource

import com.example.dictionary.model.data.DataModel

class DataSourceRemote(private val remoteProvider: RetrofitImplementation = RetrofitImplementation()) :
    DataSource<List<DataModel>> {
    override suspend fun getData(word: String): List<DataModel> = remoteProvider.getData(word)
}