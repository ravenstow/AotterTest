package com.mike.aottertest

import com.mike.aottertest.model.Book
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

// https://developer.nytimes.com/docs/books-product/1/overview
private const val BASE_URL = "https://api.nytimes.com/svc/"

/**
 * https://api.nytimes.com/svc/books/lists/names.json?api-key=Wzw3LuW9qwgEX8BaMZJG1GqpGHEPPm9H
 */

class BookRepository {
    object NytApi {
        private val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        val retrofitService: NytApiService by lazy { retrofit.create(NytApiService::class.java) }

//        val bookNameAdapter: JsonAdapter<BookNameResponse> = moshi.adapter(BookNameResponse::class.java)
//        val bookDataAdapter: JsonAdapter<BookDataResponse> = moshi.adapter(BookDataResponse::class.java)
    }


    suspend fun fetchBooks(): List<Book> {
        try {
            val bookNameResponse = NytApi.retrofitService.getBookList()
            println("Book Name Response: $bookNameResponse")
//            val decodedNameResponse = NytApi.bookNameAdapter.fromJson(bookNameResponse)
            val bookList = bookNameResponse.toBookList()

            val bookDataResponse = NytApi.retrofitService
                .getBooks(url = "books/lists/current/${bookList.first()}/.json?$KEY_SUFFIX=$API_KEY")
//            val decodedBookData = NytApi.bookDataAdapter.fromJson(bookDataResponse)
            println("Book Data Response: $bookDataResponse")
            return bookDataResponse.toBooks()
        } catch (e: Exception) {
            println("Http Error: ${e.message}")
            return listOf()
        }
    }
}