package com.mike.aottertest.domain

import com.mike.aottertest.data.BookRepository
import javax.inject.Inject

class BookUseCase @Inject constructor(
    private val bookRepository: BookRepository
) {
    suspend fun fetchBooks() = bookRepository.fetchBooks()
}