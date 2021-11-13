package com.example.dictionary.koin

import androidx.room.Room
import com.example.dictionary.main.MainActivity
import com.example.dictionary.main.MainInteractor
import com.example.dictionary.main.MainViewModel
import com.example.history.interactor.HistoryInteractor
import com.example.history.ui.HistoryActivity
import com.example.history.ui.HistoryViewModel
import com.example.model.dto.SearchResultDTO
import com.example.repository.datasource.RetrofitImplementation
import com.example.repository.repository.Repository
import com.example.repository.repository.RepositoryImplementation
import com.example.repository.repository.RepositoryImplementationLocal
import com.example.repository.repository.RepositoryLocal
import com.example.repository.room.HistoryDatabase
import com.example.repository.room.RoomDBImplementation
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val application = module {
    single {
        Room.databaseBuilder(get(), HistoryDatabase::class.java, "HistoryDB")
            .fallbackToDestructiveMigration().build()
    }
    single { get<HistoryDatabase>().historyDAO() }

    single<Repository<List<SearchResultDTO>>> { RepositoryImplementation(RetrofitImplementation()) }
    single<RepositoryLocal<List<SearchResultDTO>>> {
        RepositoryImplementationLocal(RoomDBImplementation(get()))
    }
}

val mainScreen = module {
    scope(named<MainActivity>()) {
        scoped { MainInteractor(get(), get()) }
        viewModel { MainViewModel(get()) }
    }
}

val historyScreen = module {
    scope(named<HistoryActivity>()) {
        scoped { HistoryInteractor(get(), get()) }
        viewModel { HistoryViewModel(get()) }
    }
}