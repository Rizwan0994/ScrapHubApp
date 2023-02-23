package com.example.scrapproject.blogScreen

// BlogAdapter.java


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class BlogAdapter(private val blogs: List<Blog>, private val context: Context) :
    RecyclerView.Adapter<BlogAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(com.example.scrapproject.R.layout.recycler_card_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val blog = blogs[position]
        holder.titleTextView.text = blog.title
        holder.descriptionTextView.text = blog.description
        Glide.with(context).load(blog.imageResource).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return blogs.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView
        var titleTextView: TextView
        var descriptionTextView: TextView

        init {
            imageView = itemView.findViewById(com.example.scrapproject.R.id.image_view)
            titleTextView = itemView.findViewById(com.example.scrapproject.R.id.title_text_view)
            descriptionTextView = itemView.findViewById(com.example.scrapproject.R.id.description_text_view)
        }
    }
}
