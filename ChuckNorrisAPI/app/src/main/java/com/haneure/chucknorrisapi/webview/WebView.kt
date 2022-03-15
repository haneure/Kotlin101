package com.haneure.chucknorrisapi.webview

import android.graphics.Bitmap
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.haneure.chucknorrisapi.R
import com.haneure.chucknorrisapi.category.CategoryListAdapter
import com.haneure.chucknorrisapi.detailcategory.JokesDetail


class WebView : AppCompatActivity() {
    var TAG: String = "<><>"
    private lateinit var backButton: ImageView
    private lateinit var forwardButton: ImageView

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
        setContentView(R.layout.activity_web_view)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setCustomView(R.layout.browser_toolbar)
        supportActionBar?.setTitle("Detail")

        var title = findViewById<TextView>(R.id.toolbar_title)
        title.text = "Detail"

        backButton = findViewById<ImageView>(R.id.backButton)
        forwardButton = findViewById<ImageView>(R.id.forwardButton)

        val progressBar: ProgressBar = findViewById(R.id.progressBar)
        val webview: WebView = findViewById(R.id.webView)

        val json = intent.getStringExtra(CategoryListAdapter.WEB_DATA)
        val token: TypeToken<ArrayList<JokesDetail?>?> = object : TypeToken<ArrayList<JokesDetail?>?>() {}

        //data
        val detailCategoryList: ArrayList<JokesDetail> = Gson().fromJson(json, token.type)


        //index yg diklik
        var index = intent.getIntExtra("INDEX", 0)

        loadWebpage(webview, progressBar, detailCategoryList[index].jokes.url)

        setComponentVisibility(index, detailCategoryList.size)

        //onClick
        backButton.setOnClickListener {
            if (index > 0) {
                index -= 1
                setComponentVisibility(index, detailCategoryList.size)
            }
            loadWebpage(webview, progressBar, detailCategoryList[index].jokes.url)
        }

        forwardButton.setOnClickListener {
            if (index < detailCategoryList.size) {
                index += 1
                setComponentVisibility(index, detailCategoryList.size)
            }
            Toast.makeText(this, "Forward", Toast.LENGTH_SHORT).show()
            loadWebpage(webview, progressBar, detailCategoryList[index].jokes.url)
        }
    }

    private fun setComponentVisibility(index: Int, size: Int) {
        if (index == 0) {
            backButton.visibility = View.GONE
        } else if (index == size - 1) {
            forwardButton.visibility = View.GONE
        } else {
            backButton.visibility = View.VISIBLE
            forwardButton.visibility = View.VISIBLE
        }
    }

    private fun loadWebpage(webview: WebView, progressBar: ProgressBar, url: String) {
        webview.webViewClient = WebViewClient()
        loadProgressBar(webview, progressBar)
        updateProgress(webview, progressBar)
        try {
            webview.loadUrl(url)
        } catch (e: UnsupportedOperationException) {
            e.printStackTrace()
        }
    }

    private fun updateProgress(webview: WebView, progressBar: ProgressBar) {
        webview.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                progressBar.progress = newProgress
            }
        }
    }

    private fun loadProgressBar(webview: WebView, progressBar: ProgressBar) {
        webview.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)

                progressBar.visibility = View.VISIBLE
                progressBar.progress = 0
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                progressBar.visibility = View.GONE
            }
        }
    }
}