package com.example.dictionary.model.repository

import com.example.dictionary.model.data.DataModel
import com.example.dictionary.model.datasource.DataSource
import io.reactivex.Observable

class RepositoryImplementation(
    private val dataSource: DataSource<List<DataModel>>
) : Repository<List<DataModel>> {
    override fun getData(word: String): Observable<List<DataModel>> = dataSource.getData(word)
}