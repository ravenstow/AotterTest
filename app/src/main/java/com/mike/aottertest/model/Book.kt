package com.mike.aottertest.model

import com.squareup.moshi.Json

data class Book(
    val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "author") val author: String,
    @Json(name = "book_image") val imageSrc: String
)