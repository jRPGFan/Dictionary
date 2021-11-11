package com.example.repository.repository

import com.example.model.AppState
import com.example.model.DataModel

interface RepositoryLocal<T> : Repository<T> {
    suspend fun saveToDB(appState: AppState)
    suspend fun getWord(word: String): DataModel
}