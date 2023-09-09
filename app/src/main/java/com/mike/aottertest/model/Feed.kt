package com.mike.aottertest.model

import androidx.compose.ui.graphics.ImageBitmap

data class Feed(
    val title: String,
    val content: String,
    val image: ImageBitmap
)