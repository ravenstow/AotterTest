package com.mike.aottertest

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AotterTestApp() {
    Scaffold { contentPadding ->
        FeedList(Modifier.padding(contentPadding))
    }
}