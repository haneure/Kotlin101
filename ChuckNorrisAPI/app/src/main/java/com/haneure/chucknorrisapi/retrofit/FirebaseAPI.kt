package com.haneure.chucknorrisapi.retrofit

import com.haneure.chucknorrisapi.category.Category
import retrofit2.Call
import retrofit2.http.GET

//{
//    category: [
//    {
//        name: "animal",
//        image: "https://firebasestorage.googleapis.com/v0/b/catalog-1af7c.appspot.com/o/category%2Fanimal.svg?alt=media&token=735d144b-88d7-40c5-943a-cd09f406ba0c"
//    },
//    {
//        name: "career",
//        image: "https://firebasestorage.googleapis.com/v0/b/catalog-1af7c.appspot.com/o/category%2Fcareer.svg?alt=media&token=5face837-938e-41f5-b745-74ff360c3982"
//    }
//    ]
//}

interface FirebaseAPI {
    @GET("v0/b/catalog-1af7c.appspot.com/o/categories.json?alt=media&token=04b89ba8-f69e-4d39-a6d6-5c6d33681235")
    fun getCategoryImage(): Call<Category>

    @GET("v0/b/catalog-1af7c.appspot.com/o/images.json?alt=media&token=8a3076d3-15be-4600-ad69-9d1e4fbc9ba6")
    fun getDetailImage(): Call<ArrayList<String>>
}