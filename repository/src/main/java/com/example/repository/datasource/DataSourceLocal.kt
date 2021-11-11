package com.example.repository.datasource

import com.example.model.AppState
import com.example.model.DataModel

interface DataSourceLocal<T> : DataSource<T> {
    suspend fun saveToDB(appState: AppState)
    suspend fun getWord(word: String) : DataModel
}