package com.mike.aottertest.service.response

import com.mike.aottertest.model.Book
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class BookDataResponse(
    val headers: BookDataHeaders,
    val body: List<BookDetailResults>
)

@JsonClass(generateAdapter = true)
class BookDataHeaders(
    val status: String,
    val copyright: String,
    val num_results: String,
    val last_modified: String
)

@JsonClass(generateAdapter = true)
class BookDetailResults(val book_details: List<BookDetail>)

@JsonClass(generateAdapter = true)
data class BookDetail(
    val title: String,
    val description: String,
    val contributor: String,
    val author: String,
    val contributor_note: String,
    val price: String,
    val age_group: String,
    val publisher: String,
    val primary_isbn13: String,
    val primary_isbn10: String,
    val book_uri: String,
    val book_image: String,
    val amazon_product_url: String,
)

fun BookDataResponse.toBooks(): List<Book> = body.map { it.book_details.first().let { detail ->
    Book(title = detail.title, author = detail.author, imageSrc =  detail.book_image)
}}
