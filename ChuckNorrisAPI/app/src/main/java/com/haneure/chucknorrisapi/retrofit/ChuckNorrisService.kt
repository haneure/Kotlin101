package com.haneure.chucknorrisapi.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ChuckNorrisService {

    val BASE_URL: String = "https://api.chucknorris.io/"
    val endpoint: ChuckNorrisAPI

    get() {
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create( ChuckNorrisAPI::class.java )
    }
}