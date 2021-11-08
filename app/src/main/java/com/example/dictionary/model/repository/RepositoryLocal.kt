package com.example.dictionary.model.repository

import com.example.dictionary.model.data.AppState
import com.example.dictionary.model.data.DataModel

interface RepositoryLocal<T> : Repository<T> {
    suspend fun saveToDB(appState: AppState)
    suspend fun getWord(word: String): DataModel
}