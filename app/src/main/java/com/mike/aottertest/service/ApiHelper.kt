package com.mike.aottertest.service

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * NewYorkTimes API: https://developer.nytimes.com/docs/books-product/1/overview
 */
private const val BASE_URL = "https://api.nytimes.com/svc/"

class ApiHelper {
    companion object NewYorkTimes {
        private val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        val retrofitService: NytApiService by lazy { retrofit.create(NytApiService::class.java) }
    }
}