package com.mike.aottertest

import com.mike.aottertest.model.Book
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class BookNameResponse(
    val results: List<BookCategoryResult>
)

//data class BookNameResponse (
//    @Json(name = "header") val header: String,
//    @Json(name = "body") val body: Results
//) {
//
//}
@JsonClass(generateAdapter = true)
data class BookCategoryResult(
    val list_name: String,
    val display_name: String,
    val list_name_encoded: String,
    val oldest_published_date: String,
    val newest_published_date: String,
    val updated: String
)

fun BookNameResponse.toBookList(): List<String> = results.map { it.list_name }

@JsonClass(generateAdapter = true)
class BookDataResponse(
    val body: List<BookDetailResults>
)

@JsonClass(generateAdapter = true)
class BookDetailResults(
    val book_details: List<BookDetail>
)
//data class BookDataResponse (
//    @Json(name = "header") val header: String,
//    @Json(name = "body") val body: Results
//) {
//    class BookDataResponse (
//        @Json(name = "book_details") val results: List<BookDataResult>
//    )
//}
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
