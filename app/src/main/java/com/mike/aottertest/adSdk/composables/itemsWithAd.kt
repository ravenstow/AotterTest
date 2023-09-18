package com.mike.aottertest.adSdk.composables

import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import com.mike.aottertest.adSdk.model.AdEntity
import com.mike.aottertest.adSdk.model.AdFrequency

inline fun <T> LazyListScope.itemsWithAd(
    items: List<T>,
    adFrequency: AdFrequency = AdFrequency.HIGH,
    noinline key: ((item: T) -> Any)? = null,
    noinline contentType: (item: T) -> Any? = { null },
    crossinline itemContent: @Composable LazyItemScope.(item: T) -> Unit
) {
    var startIndex = 0
    val ads = AdEntity.Demo().toMutableList()

    items.forEachIndexed { i, _ ->
        val shouldInsertAd = (i + 1) % adFrequency.adInterval == 0

        if (shouldInsertAd) {
            val periodicItems = items.subList(startIndex, i + 1)

            items(
                count = periodicItems.size,
                key = if (key != null) { index: Int -> key(periodicItems[index]) } else null,
                contentType = { index: Int -> contentType(periodicItems[index]) }
            ) {
                itemContent(periodicItems[it])
            }

            item { AdItem(ad = ads.removeFirst() ) }

            startIndex = i + 1
        }
    }

}

