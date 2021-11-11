package com.example.dictionary.koin

import androidx.room.Room
import com.example.dictionary.main.MainInteractor
import com.example.dictionary.main.MainViewModel
import com.example.history.interactor.HistoryInteractor
import com.example.history.ui.HistoryViewModel
import com.example.model.DataModel
import com.example.repository.datasource.RetrofitImplementation
import com.example.repository.repository.Repository
import com.example.repository.repository.RepositoryImplementation
import com.example.repository.repository.RepositoryImplementationLocal
import com.example.repository.repository.RepositoryLocal
import com.example.repository.room.HistoryDatabase
import com.example.repository.room.RoomDBImplementation
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