package com.example.testflowwow

import kotlinx.serialization.Serializable

@Serializable
data class DataNumberItem(
    val cc: List<String>,
    val desc_en: String,
    val desc_ru: String,
    val mask: String,
    val name_en: String,
    val name_ru: String
)