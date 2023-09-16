package com.mike.aottertest.service

import com.mike.aottertest.service.response.BookCategoryResponse
import com.mike.aottertest.service.response.BookDataResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

const val API_KEY = "Wzw3LuW9qwgEX8BaMZJG1GqpGHEPPm9H"
const val KEY_SUFFIX = "api-key"

interface NytApiService {
    @GET("books/lists/names.json")
    suspend fun getBookCategories(@Query(KEY_SUFFIX) apiKey: String = API_KEY): BookCategoryResponse
    @GET
    suspend fun getBooks(@Url url: String): BookDataResponse
}