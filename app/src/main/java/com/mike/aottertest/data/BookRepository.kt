package com.mike.aottertest.data

import com.mike.aottertest.API_KEY
import com.mike.aottertest.KEY_SUFFIX
import com.mike.aottertest.NytApiService
import com.mike.aottertest.model.Book
import com.mike.aottertest.toBookList
import com.mike.aottertest.toBooks
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * https://api.nytimes.com/svc/books/lists/names.json?api-key=Wzw3LuW9qwgEX8BaMZJG1GqpGHEPPm9H
 */

// https://developer.nytimes.com/docs/books-product/1/overview
private const val BASE_URL = "https://api.nytimes.com/svc/"

interface BookRepository {
    suspend fun fetchBooks()
}

class BookRepositoryImpl : BookRepository {
    object NytApi {
        private val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        val retrofitService: NytApiService by lazy { retrofit.create(NytApiService::class.java) }
    }


   override suspend fun fetchBooks() {
        try {
            val bookNameResponse = NytApi.retrofitService.getBookList()

//            val decodedNameResponse = NytApi.bookNameAdapter.fromJson(bookNameResponse)
            val bookList = bookNameResponse.toBookList().also { println("Book Name Response: $it") }

            val bookDataResponse = NytApi.retrofitService
                .getBooks(url = "books/lists/current/${bookList.first()}/.json?$KEY_SUFFIX=$API_KEY")
//            val decodedBookData = NytApi.bookDataAdapter.fromJson(bookDataResponse)

//            return bookDataResponse.toBooks().also { println("Book Data Response: $it") }
        } catch (e: Exception) {
            println("Http Error: ${e.message}")
        }
    }
}