package com.haneure.chucknorrisapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.haneure.chucknorrisapi.category.CategoryAndCategoryImage
import com.haneure.chucknorrisapi.category.CategoryListAdapter
import com.haneure.chucknorrisapi.category.CategoryViewModel
import com.haneure.chucknorrisapi.search.SearchActivity


class MainActivity : AppCompatActivity() {
    var TAG: String = "<><>"
    private lateinit var rvCategory: RecyclerView
    private lateinit var spinner: ProgressBar
    val model: CategoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setCustomView(R.layout.main_toolbar)

        model.getCategoryAndCategoryImage().observe(
            this,
            Observer<ArrayList<CategoryAndCategoryImage>> { categoryAndCategoryImage ->
                Log.d(TAG, categoryAndCategoryImage.toString())
                val listJokesAdapter = CategoryListAdapter(categoryAndCategoryImage)
                rvCategory.adapter = listJokesAdapter
                spinner.visibility = View.GONE
            })

        //Toolbar
        var title = findViewById<TextView>(R.id.toolbar_title)
        title.text = "Chuck Norris V2"

        //Ui
        spinner = findViewById(R.id.spinner)
        var searchBtn: ImageView = findViewById(R.id.searchButton)
        spinner.visibility = View.VISIBLE

        //Recycler View
        rvCategory = findViewById(R.id.rv_list_category)
        rvCategory.layoutManager = LinearLayoutManager(this)

        //Search button
        searchBtn.setOnClickListener {
            var searchIntent = Intent(this, SearchActivity::class.java)
            startActivity(searchIntent)
        }
    }

}

