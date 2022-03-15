package com.haneure.chucknorrisapi.category

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

//{
//    category: [
//    {
//        name: "animal",
//        image: "https://firebasestorage.googleapis.com/v0/b/catalog-1af7c.appspot.com/o/category%2Fanimal.svg?alt=media&token=735d144b-88d7-40c5-943a-cd09f406ba0c"
//        result:[
//             "abac",
//             "caca"
//        ]
//    },
//}


//data class CategoryImage(val name: String, val image: String)

data class Category(
    @SerializedName("category")
    @Expose
    val categoryImage: List<CategoryImage>
    )

data class CategoryImage(
//    @SerializedName("name")
//    @Expose
    val name: String,

//    @SerializedName("image")
//    @Expose
    val image: String

)




