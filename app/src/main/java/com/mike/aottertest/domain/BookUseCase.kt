package com.mike.aottertest.domain

import com.mike.aottertest.data.BookRepository
import com.mike.aottertest.model.Book
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class BookUseCase @Inject constructor(
    private val bookRepository: BookRepository
) {
    val books: Flow<List<Book>> = flowOf()

    suspend fun fetchBooks() = bookRepository.fetchBooks()
}