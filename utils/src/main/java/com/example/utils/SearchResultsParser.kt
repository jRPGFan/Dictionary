package com.example.utils

import com.example.model.AppState
import com.example.model.DataModel
import com.example.model.Meanings

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
        for (meaning in dataModel.meanings!!) {
            if (meaning.translation != null && !meaning.translation!!.translation.isNullOrBlank())
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
        for (meaning in dataModel.meanings!!)
            if (meaning.translation != null && !meaning.translation!!.translation.isNullOrBlank())
                newMeanings.add(Meanings(meaning.translation, meaning.imageUrl))

        if (newMeanings.isNotEmpty()) newDataModels.add(DataModel(dataModel.text, newMeanings))
    }
}
