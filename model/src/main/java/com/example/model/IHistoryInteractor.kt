package com.example.model

interface IHistoryInteractor {
    suspend fun getData(word: String): AppState
}