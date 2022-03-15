package com.haneure.chucknorrisapi.category

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.haneure.chucknorrisapi.detailcategory.DetailCategory
import com.haneure.chucknorrisapi.R

class CategoryListAdapter(private val listCategoryImage: ArrayList<CategoryAndCategoryImage>) :
    RecyclerView.Adapter<CategoryListAdapter.ListViewHolder>() {
    var TAG: String = "<><>"

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvCategory: TextView = itemView.findViewById(R.id.tv_category)
        var imgCategory: ImageView = itemView.findViewById(R.id.img_category)

        init {
            itemView.setOnClickListener {
                Toast.makeText(itemView.context, tvCategory.text, Toast.LENGTH_SHORT).show()
                val intentDetailCat = Intent(itemView.context, DetailCategory::class.java)
                intentDetailCat.putExtra(JOKES_CATEGORY, tvCategory.text)
                itemView.context.startActivity(intentDetailCat)
            }

        }
    }

    companion object {
        const val JOKES_CATEGORY = "animal"
        const val WEB_DATA = "detail"
        //val WEB_DATA: ArrayList<Jokes> = arrayListOf(Jokes(listOf("a", "b"), "com", "a", "a", "a"))
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.category_row, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val category = listCategoryImage[position]

        holder.tvCategory.text = category.category
        GlideToVectorYou.init().with(holder.itemView.context)
            .load(Uri.parse(category.categoryImage.image), holder.imgCategory)
    }

    override fun getItemCount(): Int {
        return listCategoryImage.size
    }

}