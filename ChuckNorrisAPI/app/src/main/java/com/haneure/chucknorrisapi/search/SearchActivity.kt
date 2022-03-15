package com.haneure.chucknorrisapi.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.haneure.chucknorrisapi.R
import com.haneure.chucknorrisapi.category.CategoryViewModel
import com.haneure.chucknorrisapi.detailcategory.DetailCategoryListAdapter
import com.haneure.chucknorrisapi.detailcategory.Jokes
import com.haneure.chucknorrisapi.detailcategory.JokesDetail
import com.haneure.chucknorrisapi.detailcategory.Search
import com.haneure.chucknorrisapi.retrofit.ChuckNorrisService
import com.haneure.chucknorrisapi.retrofit.FirebaseService
import okhttp3.internal.notify
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {
    var TAG: String = "<><>"
    private var listJokes = arrayListOf<Jokes>()
    private var listDetailURL: ArrayList<String> = arrayListOf()
    private var listSearchResult = arrayListOf<JokesDetail>()
    private lateinit var noResult: TextView
    private lateinit var spinner: ProgressBar
    val model: SearchViewModel by viewModels()

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                super.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Search")

        noResult = findViewById(R.id.noResult)
        spinner = findViewById(R.id.spinner)

        var query: SearchView = findViewById(R.id.search_view)
        var rvJokes: RecyclerView = findViewById(R.id.rv_search)

        rvJokes.layoutManager = LinearLayoutManager(this)

        query.requestFocus()
        query.isIconified = false

        model.listJokesObservable.observe(this) {
            val listDetailAdapter = DetailCategoryListAdapter(it)
            listDetailAdapter.notifyDataSetChanged()
            rvJokes.adapter = listDetailAdapter
            spinner.visibility = View.GONE
        }

        var Observer: Observer<ArrayList<JokesDetail>> = Observer{ search ->
            val listDetailAdapter = DetailCategoryListAdapter(search)
            listDetailAdapter.notifyDataSetChanged()
            rvJokes.adapter = listDetailAdapter
            spinner.visibility = View.GONE

            if(search.size == 0){
                noResult.visibility = View.VISIBLE
                rvJokes.visibility = View.GONE
            } else {
                noResult.visibility = View.GONE
                searchData(query!!.toString(), rvJokes, search)
            }

        }

        query.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                spinner.visibility = View.VISIBLE
                rvJokes.visibility = View.VISIBLE

                Log.d(TAG,model.onKeyboardTapEnter(query!!).toString())
//                model.getSearchResult(query!!).observe(this@SearchActivity, Observer<ArrayList<JokesDetail>> { search ->
//                    val listDetailAdapter = DetailCategoryListAdapter(search)
//                    rvJokes.adapter = listDetailAdapter
//                    searchData(query!!.toString(), rvJokes, search)
//                    spinner.visibility = View.GONE
//                })
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                rvJokes.visibility = View.GONE
                Log.d(TAG, newText.toString())
                return false
            }
        })
    }

    private fun searchData(query: String, rvJokes: RecyclerView, search: ArrayList<JokesDetail>) {

        if(query.length < 3){
            noResult.visibility = View.VISIBLE
            rvJokes.visibility = View.GONE
            noResult.text = "Input minimal 3 huruf untuk melakukan pencarian"
            spinner.visibility = View.GONE
        } else if(search.size == 0) {
            noResult.visibility = View.VISIBLE
            noResult.text = "Tidak ada hasil yang ditemukan"
            rvJokes.visibility = View.GONE
        }
        else {
            listJokes.clear()
            rvJokes.visibility = View.VISIBLE
            /**
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

                    val search = listJokes?.mapIndexed { index, imageURL ->
                        if(index > 30 ){
                            JokesDetail(listJokes[index], result!![index%30])
                        } else {
                            JokesDetail(listJokes[index], result!![index])
                        }
                    }
                    listSearchResult.addAll(search)
                    val listDetailAdapter = DetailCategoryListAdapter(listSearchResult)
                    listDetailAdapter.notifyDataSetChanged()
                    rvJokes.adapter = listDetailAdapter
                    spinner.visibility = View.GONE
                }

                override fun onFailure(call: Call<ArrayList<String>>, t: Throwable) {
                    Log.d(TAG, t.toString())
                }
             })
            **/
        }

        /**

//        ChuckNorrisService.endpoint.searchJokes(query).enqueue(object : Callback<Search> {
//            override fun onResponse(
//                call: Call<Search>,
//                response: Response<Search>
//            ) {
//                val result = response.body()
//                Log.d(TAG, result.toString())
//                result?.result?.forEach {
//                    Log.d(TAG, it.value)
//                    listJokes.add(it)
//                }
//
//                if(listJokes.size == 0){
//                    noResult.visibility = View.VISIBLE
//                } else if (query.length < 3) {
//                    noResult.visibility = View.VISIBLE
//                    noResult.text = "Input minimal 3 huruf untuk melakukan pencarian"
//                } else {
//                    noResult.visibility = View.GONE
//                }
//                spinner.visibility = View.GONE
//            }
//
//            override fun onFailure(call: Call<Search>, t: Throwable) {
//                Log.d(TAG, t.toString())
//            }
//        })
//
//        model
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
//
//                //val listDetailAdapter = DetailCategoryListAdapter(listJokes, listDetailURL)
////                listDetailAdapter.notifyDataSetChanged()
////                listDetailAdapter.notifyItemInserted(listJokes.size-1)
//                rvJokes.adapter?.notifyDataSetChanged()
//                //rvJokes.adapter = listDetailAdapter
//            }
//
//            override fun onFailure(call: Call<ArrayList<String>>, t: Throwable) {
//                Log.d(TAG, t.toString())
//            }
//        })
**/
    }
}