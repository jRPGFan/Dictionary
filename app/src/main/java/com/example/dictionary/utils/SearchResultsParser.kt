package com.example.dictionary.utils

import com.example.dictionary.model.data.AppState
import com.example.dictionary.model.data.DataModel
import com.example.dictionary.model.data.Meanings
import com.example.dictionary.model.data.Translation
import com.example.dictionary.room.HistoryEntity

fun parseSearchResults(state: AppState): AppState {
    val newSearchResults = arrayListOf<DataModel>()
    when (state) {
        is AppState.Success -> {
            val searchResults = state.data
            if (!searchResults.isNullOrEmpty())
                for (searchResult in searchResults) parseResult(searchResult, newSearchResults)
        }
    }
    return AppState.Success(newSearchResults)
}

private fun parseResult(dataModel: DataModel, newDataModels: ArrayList<DataModel>) {
    if (!dataModel.text.isNullOrBlank() && !dataModel.meanings.isNullOrEmpty()) {
        val newMeanings = arrayListOf<Meanings>()
        for (meaning in dataModel.meanings) {
            if (meaning.translation != null && !meaning.translation.translation.isNullOrBlank())
                newMeanings.add(Meanings(meaning.translation, meaning.imageUrl))
        }
        if (newMeanings.isNotEmpty()) newDataModels.add(DataModel(dataModel.text, newMeanings))
    }
}

fun convertMeaningsToString(meanings: List<Meanings>): String {
    var meaningsWithSeparator = String()
    for ((index, meaning) in meanings.withIndex()) {
        meaningsWithSeparator +=
            if (index + 1 != meanings.size) String.format(
                "%s%s",
                meaning.translation?.translation,
                ", "
            )
            else meaning.translation?.translation
    }
    return meaningsWithSeparator
}

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

fun mapHistoryEntityToDataModel(historyEntity: HistoryEntity) : DataModel {
    var searchResult = DataModel("", null)
    if (historyEntity.word.isNotBlank())
        searchResult = DataModel(historyEntity.word,
        arrayListOf(Meanings(
            Translation(historyEntity.description ?: ""),
            historyEntity.imageUrl ?: ""
        )))
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

fun parseOnlineSearchResults(appState: AppState): AppState =
    AppState.Success(mapResult(appState, true))

fun parseLocalSearchResults(appState: AppState): AppState =
    AppState.Success(mapResult(appState, false))

fun mapResult(appState: AppState, isOnline: Boolean): List<DataModel> {
    val newSearchResults = arrayListOf<DataModel>()
    when (appState) {
        is AppState.Success -> getSuccessResultData(appState, isOnline, newSearchResults)
    }
    return newSearchResults
}

private fun getSuccessResultData(
    appState: AppState.Success,
    isOnline: Boolean,
    newDataModels: ArrayList<DataModel>
) {
    val dataModels: List<DataModel> = appState.data as List<DataModel>
    if (dataModels.isNotEmpty()) {
        if (isOnline) for (searchResult in dataModels) parseOnlineResult(
            searchResult,
            newDataModels
        )
        else for (searchResult in dataModels) newDataModels.add(
            DataModel(
                searchResult.text,
                arrayListOf()
            )
        )
    }
}

private fun parseOnlineResult(dataModel: DataModel, newDataModels: ArrayList<DataModel>) {
    if (!dataModel.text.isNullOrBlank() && !dataModel.meanings.isNullOrEmpty()) {
        val newMeanings = arrayListOf<Meanings>()
        for (meaning in dataModel.meanings)
            if (meaning.translation != null && !meaning.translation.translation.isNullOrBlank())
                newMeanings.add(Meanings(meaning.translation, meaning.imageUrl))

        if (newMeanings.isNotEmpty()) newDataModels.add(DataModel(dataModel.text, newMeanings))
    }
}

