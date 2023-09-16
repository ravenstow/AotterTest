package com.mike.aottertest.data

import com.mike.aottertest.model.Book
import com.mike.aottertest.service.API_KEY
import com.mike.aottertest.service.ApiHelper
import com.mike.aottertest.service.KEY_SUFFIX
import com.mike.aottertest.service.response.toBookCategories
import com.mike.aottertest.service.response.toBooks
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor() : BookRepository {
    private var cacheBooks = listOf<Book>()

    override suspend fun fetchBooks(): List<Book> {
        try {
            val bookCategoryResponse = ApiHelper.retrofitService.getBookCategories()
            val bookCategories =
                bookCategoryResponse.toBookCategories().also { println("Book Name Response: $it") }
            val bookDataResponse = ApiHelper.retrofitService.getBooks(
                url = "books/lists/current/${bookCategories.first()}/.json?$KEY_SUFFIX=$API_KEY"
            )

            cacheBooks = bookDataResponse.toBooks().also { println("Book Data Response: $it") }
            return cacheBooks
        } catch (e: Exception) {
            println("Http Error: ${e.message}")
            return cacheBooks
        }
    }
}