package com.example.base

interface Interactor<T> {
    suspend fun getData(word: String, fromRemoteSource: Boolean): T
}