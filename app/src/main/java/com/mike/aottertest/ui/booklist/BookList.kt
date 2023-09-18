package com.mike.aottertest.ui.booklist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mike.aottertest.adSdk.composables.itemsWithAd
import com.mike.aottertest.model.Book
import com.mike.aottertest.ui.BookListViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BookList(
    modifier: Modifier = Modifier,
    bookListVM: BookListViewModel = hiltViewModel()
) {
    val bookListState by bookListVM.bookListState.collectAsState()
    // 沒有網路時的Demo books
//    val books = Book.Demo()
    val books = bookListState.books
    val isRefreshing = bookListState.isRefreshing
    val pullRefreshState =
        rememberPullRefreshState(isRefreshing, bookListVM::refreshBookList)

    Box(
        modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        if (isRefreshing) {
            Text(text = "Loading...", modifier = Modifier.align(Alignment.Center), fontSize = 20.sp)
        } else {
            if (books.isEmpty()) {
                Text(
                    text = "Fetching books failed\n please try again later",
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 20.sp
                )
            } else {
                LazyColumn {
                    itemsWithAd(books) { BookItem(it) }
                }
            }
        }

        PullRefreshIndicator(
            isRefreshing,
            pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}


