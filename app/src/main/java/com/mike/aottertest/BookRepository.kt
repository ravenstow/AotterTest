package com.mike.aottertest

import com.mike.aottertest.model.Book
import com.squareup.moshi.Json
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

// https://developer.nytimes.com/docs/books-product/1/overview
private const val BASE_URL = "https://api.nytimes.com/svc/"
private const val API_KEY = "Wzw3LuW9qwgEX8BaMZJG1GqpGHEPPm9H"
private const val KEY_SUFFIX = "?api-key="

data class BookNameResponse (
    @Json(name = "body") val body: Results
) {
    class Results (
        @Json(name = "results") val results: List<BookCategoryResult>
    )
}

data class BookCategoryResult (
    @Json(name = "list_name") val category: String
)

data class BookDataResponse (
    @Json(name = "header") val header: String,
    @Json(name = "body") val body: Results
) {
    class Results (
        @Json(name = "book_details") val results: List<BookDataResult>
    )
}

data class BookDataResult (
    @Json(name = "title") val title: String,
    @Json(name = "author") val author: String,
    @Json(name = "book_image") val book_image: String
)


class BookRepository {

    object NytApi {
        private val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        private val retrofit = Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
        val bookNameAdapter = moshi.adapter(BookNameResponse::class.java)
        val bookDataAdapter = moshi.adapter(BookDataResponse::class.java)
        val retrofitService: NytApiService by lazy {
            retrofit.create(NytApiService::class.java)
        }
    }


    suspend fun fetchBooks(): List<Book> {
        try {
            val bookNameResponse = NytApi.bookNameAdapter.fromJson(NytApi.retrofitService.getBookList())
            bookNameResponse?.let { _bookNameResponse ->
                println("Book Name Response: $_bookNameResponse")
                val bookDataResponse = NytApi.bookDataAdapter.fromJson(NytApi.retrofitService.getBooks(url = "books/lists/current/${_bookNameResponse.body.results.first().category}/.json$KEY_SUFFIX$API_KEY"))

                if (bookDataResponse == null) throw NullPointerException("BookData response is NULL!!")

                return bookDataResponse.body.results.map { Book(title = it.title, author = it.author, imageSrc = it.book_image) }
            } ?: throw NullPointerException("BookName response is NULL!!")
        } catch (e: Exception) {
            println("Http Error: ${e.message}")
        }



        return
    }
}

interface NytApiService {
    @GET("books/lists/names.json$KEY_SUFFIX$API_KEY")
    suspend fun getBookList(): String

    @GET
    suspend fun getBooks(@Url url: String): String
}

data class BookList (
    @Json(name = "list_name_encoded") val listName: String
)

data class BookListAdapter(
    @Json(name = "results") val bookList: BookList
)

data class BookAdapter(
    @Json(name = "book_details") val book: Book
)