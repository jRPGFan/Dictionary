package com.example.repository.repository

import com.example.model.dto.SearchResultDTO
import com.example.repository.datasource.DataSource

class RepositoryImplementation(
    private val dataSource: DataSource<List<SearchResultDTO>>
) : Repository<List<SearchResultDTO>> {
    override suspend fun getData(word: String): List<SearchResultDTO> = dataSource.getData(word)
}