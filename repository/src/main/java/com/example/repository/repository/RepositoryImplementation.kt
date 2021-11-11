package com.example.repository.repository

import com.example.model.DataModel
import com.example.repository.datasource.DataSource

class RepositoryImplementation(
    private val dataSource: DataSource<List<DataModel>>
) : Repository<List<DataModel>> {
    override suspend fun getData(word: String): List<DataModel> = dataSource.getData(word)
}