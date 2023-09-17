package com.mike.aottertest.adSdk.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AdItem(
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .background(Color.Red)
            .height(60.dp)
            .fillMaxWidth()
            .onGloballyPositioned {

            }) {
        Text(
            text = "This is Ad Content",
            modifier = Modifier.align(Alignment.Center),
            fontSize = 24.sp
        )
    }
}