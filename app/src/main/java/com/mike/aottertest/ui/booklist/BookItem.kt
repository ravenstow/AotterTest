package com.mike.aottertest.ui.booklist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mike.aottertest.model.Book

@Composable
fun BookItem(
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
