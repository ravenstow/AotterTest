package com.mike.aottertest.data

import com.mike.aottertest.model.Book
import com.mike.aottertest.service.API_KEY
import com.mike.aottertest.service.ApiHelper
import com.mike.aottertest.service.KEY_SUFFIX
import com.mike.aottertest.service.response.toBookCategories
import com.mike.aottertest.service.response.toBooks
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor() : BookRepository {
    private var cacheBooks = mutableListOf<Book>()

    override suspend fun fetchBooks(): List<Book> {
        try {
            val bookCategoryResponse = ApiHelper.retrofitService.getBookCategories()
            val bookCategories =
                bookCategoryResponse.toBookCategories().also { println("Book Name Response: $it") }

            if (bookCategories.isEmpty()) throw EmptyResultException()

            val available = if (bookCategories.size > 3) 2 else 0

            for (i in 0..available) {
                val bookDataResponse = ApiHelper.retrofitService.getBooks(
                    url = "books/lists/current/${bookCategories[i]}.json?$KEY_SUFFIX=$API_KEY"
                )
                val fetchedBooks = bookDataResponse.toBooks().also { println("Book Data Response: $it") }

                cacheBooks.addAll(fetchedBooks)
            }


            return cacheBooks
        } catch (e: Exception) {
            println("Http Error: ${e.message}")
            return cacheBooks
        }
    }
}
class EmptyResultException: Exception("Books no found!!")