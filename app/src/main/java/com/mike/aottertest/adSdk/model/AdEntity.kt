package com.mike.aottertest.adSdk.model

data class AdEntity(
    val id: Int = 0,
    val title: String,
    val imgSrc: String
) {
    object Demo {
        operator fun invoke(): List<AdEntity> =
            Array(100) { AdEntity(id = it, title = "Title ${it + 1}", imgSrc = "") }.toList()
    }
}
