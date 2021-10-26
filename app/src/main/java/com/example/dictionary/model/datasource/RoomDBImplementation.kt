package com.example.dictionary.model.datasource

import com.example.dictionary.model.data.DataModel
import io.reactivex.Observable

class RoomDBImplementation : DataSource<List<DataModel>> {
    override fun getData(word: String): Observable<List<DataModel>> {
        TODO("Not yet implemented")
    }
}