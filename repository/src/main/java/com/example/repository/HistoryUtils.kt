package com.example.repository

import com.example.model.AppState
import com.example.model.dto.MeaningsDTO
import com.example.model.dto.SearchResultDTO
import com.example.model.dto.TranslationDTO
import com.example.model.userdata.DataModel
import com.example.model.userdata.Meaning
import com.example.model.userdata.TranslatedMeaning
import com.example.repository.room.HistoryEntity
import com.example.utils.convertMeaningsToString

fun mapHistoryEntityToSearchResult(list: List<HistoryEntity>): List<SearchResultDTO> {
    val searchResult = ArrayList<SearchResultDTO>()
    if (!list.isNullOrEmpty())
        for (entity in list)
            searchResult.add(
                SearchResultDTO(
                    entity.word,
                    arrayListOf(
                        MeaningsDTO(
                            TranslationDTO(entity.description ?: ""),
                            entity.imageUrl ?: ""
                        )
                    )
                )
            )

    return searchResult
}

fun mapHistoryEntityToDataModel(historyEntity: HistoryEntity): DataModel {
    var searchResult = DataModel("", arrayListOf())
    if (historyEntity.word.isNotBlank())
        searchResult = DataModel(
            historyEntity.word,
            arrayListOf(
                Meaning(
                    TranslatedMeaning(historyEntity.description ?: ""),
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
