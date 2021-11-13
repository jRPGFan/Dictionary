package com.example.repository.datasource

import com.example.model.dto.SearchResultDTO

class DataSourceRemote(private val remoteProvider: RetrofitImplementation = RetrofitImplementation()) :
    DataSource<List<SearchResultDTO>> {
    override suspend fun getData(word: String): List<SearchResultDTO> = remoteProvider.getData(word)
}