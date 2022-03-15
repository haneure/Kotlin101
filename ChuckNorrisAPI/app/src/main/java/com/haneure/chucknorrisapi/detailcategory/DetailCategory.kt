package com.haneure.chucknorrisapi.detailcategory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.haneure.chucknorrisapi.R
import com.haneure.chucknorrisapi.category.CategoryListAdapter
import com.haneure.chucknorrisapi.category.CategoryViewModel


class DetailCategory : AppCompatActivity() {
    var TAG: String = "<><>"
    private lateinit var rvJokes: RecyclerView
    private lateinit var spinner: ProgressBar
    val model: CategoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_category)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Category")

        //get Intent
        val categoryName = intent.getStringExtra(CategoryListAdapter.JOKES_CATEGORY)

//        val detailJokesObserver = Observer<ArrayList<JokesDetail>> { detailJokes ->
//            Log.d(TAG, detailJokes.toString())
//        }
//        model.getDetailCategory(categoryName!!).observe(this, detailJokesObserver)

        model.getDetailCategory(categoryName!!).observe(this, Observer<ArrayList<JokesDetail>> { detailJokes ->
            Log.d(TAG, detailJokes.toString())
            val listDetailAdapter = DetailCategoryListAdapter(detailJokes)
            rvJokes.adapter = listDetailAdapter
            spinner.visibility = View.GONE
        })

        spinner = findViewById(R.id.spinner)
        spinner.visibility = View.VISIBLE
        rvJokes = findViewById(R.id.rv_detail_category)
        rvJokes.layoutManager = LinearLayoutManager(this)

//        for (i in 0..30) {
//            ChuckNorrisService.endpoint.getJokesByCategory(categoryName!!)
//                .enqueue(object : Callback<Jokes> {
//                    override fun onResponse(call: Call<Jokes>, response: Response<Jokes>) {
//                        val result = response.body()
//                        //Log.d(TAG, result.toString())
//                        if (result != null) {
//                            listJokes.add(result)
//                        }
//                    }
//
//                    override fun onFailure(call: Call<Jokes>, t: Throwable) {
//                        Log.d(TAG, t.toString())
//                    }
//
//                })
//        }
//
//        listJokes.size
//
//        FirebaseService.endpoint.getDetailImage().enqueue(object : Callback<ArrayList<String>> {
//            override fun onResponse(
//                call: Call<ArrayList<String>>,
//                response: Response<ArrayList<String>>
//            ) {
//                val result = response.body()
//                result?.forEach {
//                    listDetailURL.add(it)
//                }
//                val listDetailAdapter = DetailCategoryListAdapter(listJokes, listDetailURL)
//                rvJokes.adapter = listDetailAdapter
//            }
//
//            override fun onFailure(call: Call<ArrayList<String>>, t: Throwable) {
//                Log.d(TAG, t.toString())
//            }
//
//        })

    }

}