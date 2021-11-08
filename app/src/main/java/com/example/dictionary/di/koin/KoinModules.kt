package com.example.dictionary.di.koin

import androidx.room.Room
import com.example.dictionary.model.data.DataModel
import com.example.dictionary.model.datasource.RetrofitImplementation
import com.example.dictionary.model.datasource.RoomDBImplementation
import com.example.dictionary.model.repository.Repository
import com.example.dictionary.model.repository.RepositoryImplementation
import com.example.dictionary.model.repository.RepositoryImplementationLocal
import com.example.dictionary.model.repository.RepositoryLocal
import com.example.dictionary.room.HistoryDatabase
import com.example.dictionary.view.history.HistoryInteractor
import com.example.dictionary.view.history.HistoryViewModel
import com.example.dictionary.view.main.MainInteractor
import com.example.dictionary.view.main.MainViewModel
import org.koin.dsl.module

val application = module {
    single {
        Room.databaseBuilder(get(), HistoryDatabase::class.java, "HistoryDB")
            .fallbackToDestructiveMigration().build()
    }
    single { get<HistoryDatabase>().historyDAO() }
    single<Repository<List<DataModel>>> { RepositoryImplementation(RetrofitImplementation()) }
    single<RepositoryLocal<List<DataModel>>> {
        RepositoryImplementationLocal(RoomDBImplementation(get()))
    }
}

val mainScreen = module {
    factory { MainViewModel(get()) }
    factory { MainInteractor(get(), get()) }
}

val historyScreen = module {
    factory { HistoryViewModel(get()) }
    factory { HistoryInteractor(get(), get()) }
}