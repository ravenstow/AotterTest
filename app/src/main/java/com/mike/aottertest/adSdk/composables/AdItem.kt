package com.mike.aottertest.adSdk.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mike.aottertest.adSdk.AdViewModel
import com.mike.aottertest.adSdk.model.AdEntity
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// 廣告區塊的可見大小曝光標準 = 50%以上
private const val AD_ON_SCREEN_THRESHOLD = .5f

// 廣告區況的曝光時長標準 = 1秒以上
private const val AD_DURATION_THRESHOLD = 1000L

@Composable
fun AdItem(
    modifier: Modifier = Modifier,
    adVM: AdViewModel = hiltViewModel(),
    ad: AdEntity
) {
    val scope = rememberCoroutineScope()
    val adHeight = 60f

    var adImpJob by remember { mutableStateOf<Job?>(null) }
    var isAdImpValid by remember { mutableStateOf(false) }

    LaunchedEffect(isAdImpValid) {
        if (isAdImpValid) {
            if (adImpJob == null) { // 還未開始
                adImpJob = scope.launch {
                    println("Ad ${ad.id} Imp Job is STARTED")
                    delay(AD_DURATION_THRESHOLD)
                    adVM.addAdImpCount(ad)
                    println("Ad ${ad.id} Imp Job is DONE!!")
                }
            }
        } else {
            adImpJob?.cancel()
            adImpJob = null
            println("Ad  Imp Job is CANCELLED by invalid")
        }
    }

    DisposableEffect(isAdImpValid) {
        onDispose {
            // 當UI本身被Decompose了的話，也取消曝光計數
            adImpJob?.cancel()
            adImpJob = null
            println("Ad Imp Job is CANCELLED by Decomposition")
        }
    }

    val onGloballyPositioned: (LayoutCoordinates) -> Unit = { coordinates ->
        val adSize = coordinates.size // 因為只需與root比對、算出當下看得到的佔比。所以不需要通過density換算實際的dp
        val adCurrentRect = coordinates.boundsInRoot() // 物件外框在根UI(=全畫面)的上下左右位置
        // 當下看到得的UI比例
        val visibleRatio =
            (adCurrentRect.width * adCurrentRect.height) / (adSize.width * adSize.height)
        val shouldValidateImpAdd = visibleRatio >= AD_ON_SCREEN_THRESHOLD

        isAdImpValid = shouldValidateImpAdd
    }

    Box(
        modifier
            .background(Color.Red)
            .height(adHeight.dp)
            .fillMaxWidth()
            .onGloballyPositioned(onGloballyPositioned)
    ) {
        Text(
            text = "This is Ad Content ${ad.id}",
            modifier = Modifier.align(Alignment.Center),
            fontSize = 24.sp
        )
    }
}