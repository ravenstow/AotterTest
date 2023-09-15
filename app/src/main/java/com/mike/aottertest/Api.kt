package com.mike.aottertest

import retrofit2.http.GET
import retrofit2.http.Url

const val API_KEY = "Wzw3LuW9qwgEX8BaMZJG1GqpGHEPPm9H"
const val KEY_SUFFIX = "api-key"

interface NytApiService {
    @GET("books/lists/names.json?$KEY_SUFFIX=$API_KEY")
    suspend fun getBookList(): BookNameResponse

    @GET
    suspend fun getBooks(@Url url: String): BookDataResponse
}