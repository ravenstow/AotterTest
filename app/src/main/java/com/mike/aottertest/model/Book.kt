package com.mike.aottertest.model

import com.squareup.moshi.Json

data class Book(
    val id: Int = 0,
    @Json(name = "title") val title: String,
    @Json(name = "author") val author: String,
    @Json(name = "book_image") val imageSrc: String
) {
    object Demo {
        operator fun invoke(): List<Book> =
            Array(50) { Book(title = "Title ${it + 1}", author = "", imageSrc = "") }.toList()
    }
}
