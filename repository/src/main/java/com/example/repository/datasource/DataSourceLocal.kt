package com.example.repository.datasource

import com.example.model.AppState
import com.example.model.userdata.DataModel

interface DataSourceLocal<T> : DataSource<T> {
    suspend fun saveToDB(appState: AppState)
    suspend fun getWord(word: String) : DataModel
}