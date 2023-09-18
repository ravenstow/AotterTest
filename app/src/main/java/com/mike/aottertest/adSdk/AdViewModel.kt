package com.mike.aottertest.adSdk

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mike.aottertest.adSdk.model.AdEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdViewModel @Inject constructor() : ViewModel() {

    fun addAdImpCount(ad: AdEntity) = viewModelScope.launch(Dispatchers.IO) {
        // 實作曝光數的計數作業 跟本地/雲端溝通實際紀錄該廣告的IMP
        // ...
    }
}
