package com.haneure.chucknorrisapi.retrofit

import com.haneure.chucknorrisapi.detailcategory.Jokes
import com.haneure.chucknorrisapi.detailcategory.Search
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ChuckNorrisAPI {
    @GET("jokes/categories")
    fun getCategory(): Call<ArrayList<String>>

    @GET("jokes/random")
    fun getJokesByCategory(@Query("category")query: String): Call<Jokes>

    @GET("jokes/search")
    fun searchJokes(@Query("query")query: String): Call<Search>

}