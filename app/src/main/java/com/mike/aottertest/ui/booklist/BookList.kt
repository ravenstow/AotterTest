package com.mike.aottertest

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mike.aottertest.model.Book
import com.mike.sdk.SdkHelper

@Composable
fun BookList(
    modifier: Modifier = Modifier,
    bookListViewModel: BookListViewModel = BookListViewModel()
) {
    val context = LocalContext.current
    val sdkHelper = SdkHelper(context)
    val books by bookListViewModel.books.collectAsState()

    Box(modifier.fillMaxSize()) {
        if (books.isNotEmpty()) {
            LazyColumn {
                items(books.size) { i ->
                    BookItem(books[i])
                }
            }
        } else {
            Column(Modifier.align(Alignment.Center)) {
                CircularProgressIndicator(Modifier.height(100.dp))
                Loading(Modifier.height(50.dp))
            }
        }
    }
}

@Composable
private fun BookItem(
    book: Book
) {
    Box(Modifier.height(50.dp)) {
        Column {
            Row {
                Text(
                    book.title,
                    fontSize = 14.sp
                )
                AsyncImage(
                    book.imageSrc,
                    contentDescription = ""
                )
            }
        }
    }
}

@Composable
private fun Loading(modifier: Modifier = Modifier) {
    Text(
        text = "Loading...",
        modifier = modifier,
        fontSize = 20.sp
    )
}