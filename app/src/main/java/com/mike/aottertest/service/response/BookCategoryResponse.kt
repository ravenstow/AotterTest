package com.mike.aottertest.service.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class BookCategoryResponse(
    val headers: BookCategoryHeaders,
    val body: BookCategoryResult
)

@JsonClass(generateAdapter = true)
class BookCategoryHeaders(
    val status: String,
    val copyright: String,
    val num_results: String
)

@JsonClass(generateAdapter = true)
data class BookCategoryResult (val results: List<BookCategory>)

@JsonClass(generateAdapter = true)
data class BookCategory(
    val list_name: String,
    val display_name: String,
    val list_name_encoded: String,
    val oldest_published_date: String,
    val newest_published_date: String,
    val updated: String
)

fun BookCategoryResponse.toBookCategories(): List<String> = body.results.map { it.list_name }