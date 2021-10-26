package com.example.dictionary.view.base

import com.example.dictionary.model.data.AppState

interface View {
    fun renderData(appState: AppState)
}