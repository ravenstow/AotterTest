package com.mike.aottertest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mike.aottertest.model.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class BookListState(
    val books: List<Book>,
    val message: String? = ""
)

class BookListViewModel(
    private val bookRepository: BookRepository = BookRepository()
) : ViewModel() {
    private val _books = MutableStateFlow<List<Book>>(listOf())
    val books: StateFlow<List<Book>>
        get() = _books.asStateFlow()

    init {
        fetchBooks()
    }

    private fun fetchBooks() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val fetchedBooks = bookRepository.fetchBooks()
            _books.update { fetchedBooks }
        } catch (e: Exception) {
            println("Fetching feeds failed! ${e.message}")
        }
    }
}