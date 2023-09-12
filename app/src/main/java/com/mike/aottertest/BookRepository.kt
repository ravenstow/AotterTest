package com.mike.aottertest

import com.mike.aottertest.model.Book
import com.squareup.moshi.Json
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

// https://developer.nytimes.com/docs/books-product/1/overview
private const val BASE_URL = "https://api.nytimes.com/svc/"
private const val API_KEY = "Wzw3LuW9qwgEX8BaMZJG1GqpGHEPPm9H"
private const val KEY_SUFFIX = "?api-key="
class BookRepository {


    object NytApi {
        private val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        private val retrofit = Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
        val adapter: JsonAdapter<Book> = moshi.adapter<Book>()
        val retrofitService: NytApiService by lazy {
            retrofit.create(NytApiService::class.java)
        }
    }


    suspend fun fetchBooks(): List<Book> {
        val bookList = NytApi.retrofitService.getBookList()
        println("Book List: $bookList")
        val bookListName = bookList.first().listName
        return NytApi.retrofitService.getBooks(url = "books/lists/current/$bookListName/.json$KEY_SUFFIX$API_KEY")
    }
}

interface NytApiService {
    @GET("books/lists/names.json$KEY_SUFFIX$API_KEY")
    suspend fun getBookList(): List<BookList>

    @GET
    suspend fun getBooks(@Url url: String): List<Book>
}

data class BookList (
    @Json(name = "list_name_encoded") val listName: String
)

data class BookListAdapter(
    @Json(name = "results") val bookList: BookList
)

data class BookAdapter(
    @Json(name = "book_details") val book: Book
)