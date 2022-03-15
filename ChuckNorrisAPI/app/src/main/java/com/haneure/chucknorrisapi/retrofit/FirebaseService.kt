package com.haneure.chucknorrisapi.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FirebaseService {
    val BASE_URL: String = "https://firebasestorage.googleapis.com/"
    val endpoint: FirebaseAPI

        get() {
            val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create( FirebaseAPI::class.java )
        }
}

