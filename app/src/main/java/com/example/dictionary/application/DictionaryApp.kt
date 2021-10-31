package com.example.dictionary.application

import android.app.Application
import com.example.dictionary.di.koin.application
import com.example.dictionary.di.koin.mainScreen
import org.koin.core.context.startKoin

class DictionaryApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin { modules(listOf(application, mainScreen)) }
    }
}