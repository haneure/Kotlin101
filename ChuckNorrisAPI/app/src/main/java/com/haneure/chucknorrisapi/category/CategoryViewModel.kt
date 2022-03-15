package com.haneure.chucknorrisapi.category

import android.util.Log
import androidx.lifecycle.*
import com.haneure.chucknorrisapi.detailcategory.Jokes
import com.haneure.chucknorrisapi.detailcategory.JokesDetail
import com.haneure.chucknorrisapi.retrofit.ChuckNorrisService
import com.haneure.chucknorrisapi.retrofit.FirebaseService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryViewModel : ViewModel() {
    var TAG: String = "<><>"
    var categoryName: String = ""
    private var listJokes = arrayListOf<Jokes>()

    //data class CategoryAndCategoryImage(val category: String, val categoryImage: CategoryImage)
    val listCategoryAndCategoryImage: MutableLiveData<ArrayList<CategoryAndCategoryImage>> by lazy {
        MutableLiveData<ArrayList<CategoryAndCategoryImage>>().also {
            loadCategoryAndCategoryImage()
        }
    }

    val listDetailCategory: MutableLiveData<ArrayList<JokesDetail>> by lazy {
        MutableLiveData<ArrayList<JokesDetail>>().also {
            loadDetailCategory()
        }
    }


    fun getCategoryAndCategoryImage(): LiveData<ArrayList<CategoryAndCategoryImage>> {
        return listCategoryAndCategoryImage
    }

    fun getDetailCategory(category: String): LiveData<ArrayList<JokesDetail>> {
        categoryName = category
        return listDetailCategory
    }

    private fun loadCategoryAndCategoryImage() {
        //Concurrent
        //Serial biar jadi 1 sequence
        ChuckNorrisService.endpoint.getCategory().enqueue(object : Callback<ArrayList<String>> {
            override fun onResponse(
                call: Call<ArrayList<String>>,
                response: Response<ArrayList<String>>
            ) {
                FirebaseService.endpoint.getCategoryImage().enqueue(object : Callback<Category> {
                    override fun onResponse(call: Call<Category>, response2: Response<Category>) {
                        val result = response2.body()?.categoryImage
                        if (result != null) {
                            val cat = response.body()!!.mapIndexed { index, category ->
                                result[index].name == category
                                CategoryAndCategoryImage(category, result[index])
                            }
                            listCategoryAndCategoryImage.postValue(cat as ArrayList<CategoryAndCategoryImage>?)
                        }
                    }

                    override fun onFailure(call: Call<Category>, t: Throwable) {
                        Log.d(TAG, t.toString())
                    }
                })
            }

            override fun onFailure(call: Call<ArrayList<String>>, t: Throwable) {
                Log.d(TAG, t.toString())
            }
        })
    }

    private fun loadDetailCategory() {
        for (i in 0..30) {
            ChuckNorrisService.endpoint.getJokesByCategory(categoryName!!)
                .enqueue(object : Callback<Jokes> {
                    override fun onResponse(call: Call<Jokes>, response: Response<Jokes>) {
                        val result = response.body()
                        //Log.d(TAG, result.toString())
                        if (result != null) {
                            listJokes.add(result)
                        }
                    }

                    override fun onFailure(call: Call<Jokes>, t: Throwable) {
                        Log.d(TAG, t.toString())
                    }

                })
        }

        FirebaseService.endpoint.getDetailImage().enqueue(object : Callback<ArrayList<String>> {
            override fun onResponse(
                call: Call<ArrayList<String>>,
                response: Response<ArrayList<String>>
            ) {
                val result = response.body()
//                                result?.forEach {
//                                    listDetailURL.add(it)
//                                }

                val det = listJokes?.mapIndexed { index, imageURL ->
                    JokesDetail(listJokes[index], result!![index])
                }
                listDetailCategory.postValue(det as ArrayList<JokesDetail>?)

//                                val listDetailAdapter = DetailCategoryListAdapter(listJokes, listDetailURL)
//                                rvJokes.adapter = listDetailAdapter
            }

            override fun onFailure(call: Call<ArrayList<String>>, t: Throwable) {
                Log.d(TAG, t.toString())
            }

        })
    }
}