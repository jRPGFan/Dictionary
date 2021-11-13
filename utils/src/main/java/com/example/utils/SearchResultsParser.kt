package com.example.utils

import com.example.model.AppState
import com.example.model.dto.SearchResultDTO
import com.example.model.userdata.DataModel
import com.example.model.userdata.Meaning
import com.example.model.userdata.TranslatedMeaning

fun mapSearchResultToResult(searchResults: List<SearchResultDTO>): List<DataModel> {
    return searchResults.map { searchResult ->
        var meanings: List<Meaning> = listOf()
        searchResult.meanings?.let {
            meanings = it.map { meaningsDTO ->
                Meaning(
                    TranslatedMeaning(meaningsDTO?.translation?.translation ?: ""),
                    meaningsDTO?.imageUrl ?: ""
                )
            }
        }
        DataModel(searchResult.text ?: "", meanings)
    }
}

fun parseOnlineSearchResults(data: AppState): AppState {
    return AppState.Success(mapResult(data, true))
}

private fun mapResult(data: AppState, isOnline: Boolean): List<DataModel> {
    val newSearchResults = arrayListOf<DataModel>()
    when (data) {
        is AppState.Success -> {
            getSuccessResultData(data, isOnline, newSearchResults)
        }
    }
    return newSearchResults
}

private fun getSuccessResultData(
    data: AppState.Success,
    isOnline: Boolean,
    newSearchDataModels: ArrayList<DataModel>
) {
    val searchDataModels: List<DataModel> = data.data as List<DataModel>
    if (searchDataModels.isNotEmpty()) {
        if (isOnline) for (searchResult in searchDataModels)
                        parseOnlineResult(searchResult, newSearchDataModels)
        else for (searchResult in searchDataModels)
                    newSearchDataModels.add(DataModel(searchResult.text, arrayListOf()))
    }
}

private fun parseOnlineResult(searchDataModel: DataModel, newSearchDataModels: ArrayList<DataModel>) {
    if (searchDataModel.text.isNotBlank() && searchDataModel.meanings.isNotEmpty()) {
        val newMeanings = arrayListOf<Meaning>()
        newMeanings.addAll(
            searchDataModel.meanings.filter { it.translatedMeaning.translatedMeaning.isNotBlank() })
        if (newMeanings.isNotEmpty()) {
            newSearchDataModels.add(DataModel(searchDataModel.text, newMeanings))
        }
    }
}

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
        val newMeanings = arrayListOf<Meaning>()
        for (meaning in dataModel.meanings!!) {
            if (meaning.translatedMeaning != null && !meaning.translatedMeaning!!.translatedMeaning.isNullOrBlank())
                newMeanings.add(Meaning(meaning.translatedMeaning, meaning.imageUrl))
        }
        if (newMeanings.isNotEmpty()) newDataModels.add(DataModel(dataModel.text, newMeanings))
    }
}

fun convertMeaningsToString(meanings: List<Meaning>): String {
    var meaningsWithSeparator = String()
    for ((index, meaning) in meanings.withIndex()) {
        meaningsWithSeparator +=
            if (index + 1 != meanings.size) String.format(
                "%s%s",
                meaning.translatedMeaning?.translatedMeaning,
                ", "
            )
            else meaning.translatedMeaning?.translatedMeaning
    }
    return meaningsWithSeparator
}
//
//
//fun parseOnlineSearchResults(appState: AppState): AppState =
//    AppState.Success(mapResult(appState, true))
//
fun parseLocalSearchResults(appState: AppState): AppState =
    AppState.Success(mapResult(appState, false))

//fun mapResult(appState: AppState, isOnline: Boolean): List<DataModel> {
//    val newSearchResults = arrayListOf<DataModel>()
//    when (appState) {
//        is AppState.Success -> getSuccessResultData(appState, isOnline, newSearchResults)
//    }
//    return newSearchResults
//}
//
//private fun getSuccessResultData(
//    appState: AppState.Success,
//    isOnline: Boolean,
//    newDataModels: ArrayList<DataModel>
//) {
//    val dataModels: List<DataModel> = appState.data as List<DataModel>
//    if (dataModels.isNotEmpty()) {
//        if (isOnline) for (searchResult in dataModels) parseOnlineResult(
//            searchResult,
//            newDataModels
//        )
//        else for (searchResult in dataModels) newDataModels.add(
//            DataModel(
//                searchResult.text,
//                arrayListOf()
//            )
//        )
//    }
//}
//
//private fun parseOnlineResult(dataModel: DataModel, newDataModels: ArrayList<DataModel>) {
//    if (!dataModel.text.isNullOrBlank() && !dataModel.meanings.isNullOrEmpty()) {
//        val newMeanings = arrayListOf<Meanings>()
//        for (meaning in dataModel.meanings!!)
//            if (meaning.translation != null && !meaning.translation!!.translation.isNullOrBlank())
//                newMeanings.add(Meanings(meaning.translation, meaning.imageUrl))
//
//        if (newMeanings.isNotEmpty()) newDataModels.add(DataModel(dataModel.text, newMeanings))
//    }
//}
