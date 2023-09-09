package com.mike.aottertest

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mike.aottertest.model.Feed
import com.mike.sdk.SdkHelper

@Composable
fun FeedList(
    modifier: Modifier = Modifier,
    feedListViewModel: FeedListViewModel = FeedListViewModel()
) {
    val context = LocalContext.current
    val sdkHelper = SdkHelper(context)
    val feeds by feedListViewModel.feeds.collectAsState()

    Surface(modifier) {
        if (feeds.isNotEmpty()) {
            LazyColumn {
                items(feeds.size) { i ->
                    FeedItem(feeds[i])
                }
            }
        } else {
            Column {
                CircularProgressIndicator(Modifier.height(100.dp))
                Loading(Modifier.height(50.dp))
            }
        }
    }
}

@Composable
private fun FeedItem(
    feed: Feed
) {
    Box(Modifier.height(50.dp)) {
        Column {
            Row {
                Text(
                    feed.title,
                    fontSize = 14.sp
                )
                Image(
                    feed.image,
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