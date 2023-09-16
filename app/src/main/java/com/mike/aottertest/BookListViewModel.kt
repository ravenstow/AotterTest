package com.mike.aottertest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mike.aottertest.data.BookRepository
import com.mike.aottertest.domain.BookUseCase
import com.mike.aottertest.model.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class BookListState(
    val books: List<Book> = listOf(),
    val isRefreshing: Boolean = false,
    val message: String? = ""
)

@HiltViewModel
class BookListViewModel @Inject constructor(
    private val bookUseCase: BookUseCase
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
            val fetchedBooks = bookUseCase.fetchBooks()
//            books.update { fetchedBooks }
        } catch (e: Exception) {
            when (e) {
                is TimeOutException -> { println("Fetch book later! ${e.message}") }
                else -> { println("Fetch book failed! ${e.message}") }
            }

        }
    }
}

class TimeOutException: Exception("Access Timeout!!")