package com.example.dictionary.presenter

import com.example.dictionary.model.data.AppState
import com.example.dictionary.view.base.View

interface Presenter<T : AppState, V : View> {
    fun attachView(view: V)
    fun detachView(view: V)
    fun getData(word: String, isOnline: Boolean)
}