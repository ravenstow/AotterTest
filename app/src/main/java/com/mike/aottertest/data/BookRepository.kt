package com.mike.aottertest.data

import com.mike.aottertest.model.Book

interface BookRepository {
    suspend fun fetchBooks(): List<Book>
}