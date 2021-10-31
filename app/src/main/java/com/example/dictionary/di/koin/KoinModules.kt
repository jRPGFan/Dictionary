package com.example.dictionary.di.koin

import com.example.dictionary.di.NAME_LOCAL
import com.example.dictionary.di.NAME_REMOTE
import com.example.dictionary.model.data.DataModel
import com.example.dictionary.model.datasource.RetrofitImplementation
import com.example.dictionary.model.datasource.RoomDBImplementation
import com.example.dictionary.model.repository.Repository
import com.example.dictionary.model.repository.RepositoryImplementation
import com.example.dictionary.view.main.MainInteractor
import com.example.dictionary.view.main.MainViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val application = module {
    single<Repository<List<DataModel>>>(named(NAME_REMOTE))
    { RepositoryImplementation(RetrofitImplementation()) }

    single<Repository<List<DataModel>>>(named(NAME_LOCAL))
    { RepositoryImplementation(RoomDBImplementation()) }
}

val mainScreen = module {
    factory { MainInteractor(get(named(NAME_REMOTE)), get(named(NAME_LOCAL))) }
    factory { MainViewModel(get()) }
}