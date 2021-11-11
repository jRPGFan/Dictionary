package com.example.repository

import com.example.model.AppState
import com.example.model.DataModel
import com.example.model.Meanings
import com.example.model.Translation
import com.example.repository.room.HistoryEntity
import com.example.utils.convertMeaningsToString

fun mapHistoryEntityToSearchResult(list: List<HistoryEntity>): List<DataModel> {
    val searchResult = ArrayList<DataModel>()
    if (!list.isNullOrEmpty())
        for (entity in list)
            searchResult.add(
                DataModel(
                    entity.word,
                    arrayListOf(
                        Meanings(
                            Translation(entity.description ?: ""),
                            entity.imageUrl ?: ""
                        )
                    )
                )
            )

    return searchResult
}

fun mapHistoryEntityToDataModel(historyEntity: HistoryEntity): DataModel {
    var searchResult = DataModel("", null)
    if (historyEntity.word.isNotBlank())
        searchResult = DataModel(
            historyEntity.word,
            arrayListOf(
                Meanings(
                    Translation(historyEntity.description ?: ""),
                    historyEntity.imageUrl ?: ""
                )
            )
        )
    return searchResult
}

fun convertDataModelSuccessToEntity(appState: AppState): HistoryEntity? {
    return when (appState) {
        is AppState.Success -> {
            val searchResult = appState.data
            if (searchResult.isNullOrEmpty() || searchResult[0].text.isNullOrEmpty()) null
            else HistoryEntity(
                searchResult[0].text!!, convertMeaningsToString(searchResult[0].meanings!!),
                searchResult[0].meanings?.get(0)?.imageUrl
            )
        }
        else -> null
    }
}
