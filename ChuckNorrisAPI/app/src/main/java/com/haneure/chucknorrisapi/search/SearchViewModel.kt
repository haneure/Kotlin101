package com.haneure.chucknorrisapi.search

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haneure.chucknorrisapi.detailcategory.Jokes
import com.haneure.chucknorrisapi.detailcategory.JokesDetail
import com.haneure.chucknorrisapi.detailcategory.Search
import com.haneure.chucknorrisapi.retrofit.ChuckNorrisService
import com.haneure.chucknorrisapi.retrofit.FirebaseService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel: ViewModel() {
    var TAG: String = "<><>"
    var query: String = ""
    var listSearchResult: MutableLiveData<ArrayList<JokesDetail>> = MutableLiveData<ArrayList<JokesDetail>>()

    private val _listJokes = MutableLiveData<ArrayList<JokesDetail>>()
    val listJokesObservable = _listJokes

    //val listSearch: MutableLiveData<>

//    var listSearchResult: MutableLiveData<ArrayList<JokesDetail>> by lazy {
//        MutableLiveData<ArrayList<JokesDetail>>().also {
//            loadSearchResult()
//        }
//    }

    fun onKeyboardTapEnter(query: String) {
        var listJokes:ArrayList<Jokes> = arrayListOf()

        ChuckNorrisService.endpoint.searchJokes(query).enqueue(object : Callback<Search> {
            override fun onResponse(
                call: Call<Search>,
                response: Response<Search>
            ) {
                val result = response.body()
                Log.d(TAG, result.toString())
                result?.result?.forEach {
                    Log.d(TAG, it.value)
                    listJokes.add(it)
                }
            }

            override fun onFailure(call: Call<Search>, t: Throwable) {
                Log.d(TAG, t.toString())
            }
        })

        FirebaseService.endpoint.getDetailImage().enqueue(object : Callback<ArrayList<String>> {
            override fun onResponse(
                call: Call<ArrayList<String>>,
                response: Response<ArrayList<String>>
            ) {
                val result = response.body()
                Log.d(TAG, listJokes.toString())

                val search = listJokes?.mapIndexed { index, imageURL ->
                    if(index > 30 ){
                        JokesDetail(listJokes[index], result!![index%30])
                    } else {
                        JokesDetail(listJokes[index], result!![index])
                    }
                }
                listJokesObservable.value = search as ArrayList<JokesDetail>?
            }

            override fun onFailure(call: Call<ArrayList<String>>, t: Throwable) {
                Log.d(TAG, t.toString())
            }

        })
    }

    fun getSearchResult(q: String): LiveData<ArrayList<JokesDetail>> {
        query = q
        listSearchResult.value = arrayListOf()
        loadSearchResult()
        return listSearchResult
    }

    private fun loadSearchResult() {
        var listJokes:ArrayList<Jokes> = arrayListOf()
        ChuckNorrisService.endpoint.searchJokes(query).enqueue(object : Callback<Search> {
            override fun onResponse(
                call: Call<Search>,
                response: Response<Search>
            ) {
                val result = response.body()
                Log.d(TAG, result.toString())
                result?.result?.forEach {
                    Log.d(TAG, it.value)
                    listJokes.add(it)
                }
            }

            override fun onFailure(call: Call<Search>, t: Throwable) {
                Log.d(TAG, t.toString())
            }
        })

        FirebaseService.endpoint.getDetailImage().enqueue(object : Callback<ArrayList<String>> {
            override fun onResponse(
                call: Call<ArrayList<String>>,
                response: Response<ArrayList<String>>
            ) {
                val result = response.body()
                Log.d(TAG, listJokes.toString())

                val search = listJokes?.mapIndexed { index, imageURL ->
                    if(index > 30 ){
                        JokesDetail(listJokes[index], result!![index%30])
                    } else {
                        JokesDetail(listJokes[index], result!![index])
                    }
                }
                listSearchResult.value = search as ArrayList<JokesDetail>?
            }

            override fun onFailure(call: Call<ArrayList<String>>, t: Throwable) {
                Log.d(TAG, t.toString())
            }

        })
    }
}