package com.example.model.dto

import com.google.gson.annotations.SerializedName

class MeaningsDTO(
    @field: SerializedName("translation") val translation: TranslationDTO?,
    @field: SerializedName("imageUrl") val imageUrl: String?
)
