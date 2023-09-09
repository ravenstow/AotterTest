package com.mike.aottertest

import androidx.lifecycle.ViewModel
import com.mike.aottertest.model.Feed
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FeedListViewModel(
    private val feedRepository: FeedRepository = FeedRepository()
) : ViewModel() {
    private val _feeds = MutableStateFlow<List<Feed>>(listOf())
    val feeds: StateFlow<List<Feed>>
        get() = _feeds.asStateFlow()

}