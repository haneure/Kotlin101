package com.haneure.chucknorrisapi.detailcategory

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.haneure.chucknorrisapi.R
import com.haneure.chucknorrisapi.webview.WebView
import com.haneure.chucknorrisapi.category.CategoryListAdapter.Companion.WEB_DATA
import de.hdodenhof.circleimageview.CircleImageView

class DetailCategoryListAdapter(
    private val detailCategoryList: ArrayList<JokesDetail>
) :
    RecyclerView.Adapter<DetailCategoryListAdapter.ListViewHolder>() {
    var TAG: String = "<><>"


    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgIcon: CircleImageView = itemView.findViewById(R.id.img_icon)
        var imgJokes: ImageView = itemView.findViewById(R.id.img_jokes)
        var tvJokes: TextView = itemView.findViewById(R.id.tv_jokes)
        var tvCategory: TextView = itemView.findViewById(R.id.tv_category_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.jokes_card, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val detail = detailCategoryList[position]

        if(detail?.jokes.categories.size == 0){
            holder.tvCategory.visibility = View.GONE
        } else {
            holder.tvCategory.text = detail?.jokes.categories!![0]
        }

        if(detail.jokes.value.length > 75){
            holder.tvJokes.text = detail.jokes.value.take(75) + " . . ."
        }else {
            holder.tvJokes.text = detail.jokes.value.take(75)
        }

        Glide.with(holder.itemView.context)
            .load(detail.jokes.icon_url)
            .into(holder.imgIcon)

        if(detail.image[position] == null){
            Glide.with(holder.itemView.context)
                .load(Uri.parse(
                    (detailCategoryList[position%30].image)
                ))
                .into(holder.imgJokes)
        } else {
            Log.d(TAG, detail.image)
            Glide.with(holder.itemView.context)
                .load(Uri.parse(detail.image))
                .into(holder.imgJokes)
        }

        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, detail.jokes.url, Toast.LENGTH_SHORT).show()

            var json: String = Gson().toJson(detailCategoryList)
            Log.d(TAG, json)

            //kirim intent seluruh ArrayList<Jokes> konten biar bisa di previous sama next saat di buka di webview
            val intentWebView = Intent(holder.itemView.context, WebView::class.java)
            intentWebView.putExtra(WEB_DATA, json)
            intentWebView.putExtra("INDEX", position)
            holder.itemView.context.startActivity(intentWebView)

        }
    }

    override fun getItemCount(): Int {
        return detailCategoryList.size
    }
}