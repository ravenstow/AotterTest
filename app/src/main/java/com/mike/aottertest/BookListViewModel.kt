package com.mike.aottertest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mike.aottertest.model.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class BookListState(
    val books: List<Book> = listOf(),
    val isRefreshing: Boolean = false,
    val message: String? = ""
)

class BookListViewModel(
    private val bookRepository: BookRepository = BookRepository()
) : ViewModel() {
    private val books = MutableStateFlow<List<Book>>(listOf())
    private val isRefreshing = MutableStateFlow<Boolean>(false)
    val bookListState: StateFlow<BookListState> = combine(
        books,
        isRefreshing
    ) { currentBooks, refreshing ->
        BookListState(
            books = currentBooks,
            isRefreshing = refreshing
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = BookListState()
    )

    init {
        refreshBookList()
    }

    fun refreshBookList() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val fetchedBooks = bookRepository.fetchBooks()
            books.update { fetchedBooks }
        } catch (e: Exception) {
            when (e) {
                is TimeOutException -> { println("Fetch book later! ${e.message}") }
                else -> { println("Fetch book failed! ${e.message}") }
            }

        }
    }
}

class TimeOutException: Exception("Access Timeout!!")