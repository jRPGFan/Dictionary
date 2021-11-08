package com.example.dictionary.model.datasource

import com.example.dictionary.model.data.AppState
import com.example.dictionary.model.data.DataModel

interface DataSourceLocal<T> : DataSource<T> {
    suspend fun saveToDB(appState: AppState)
    suspend fun getWord(word: String) : DataModel
}