package com.example.scrapproject.feedBackScreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.scrapproject.R
//working.........................................................
//class FeedbackAdapter(private val feedbacks: List<Feedback>) :
//    RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder>() {
//
//    inner class FeedbackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val ratingTextView: TextView = itemView.findViewById(R.id.ratingTextView)
//        val commentTextView: TextView = itemView.findViewById(R.id.commentTextView)
//        val timestampTextView: TextView = itemView.findViewById(R.id.timestampTextView)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedbackViewHolder {
//        val itemView = LayoutInflater.from(parent.context)
//            .inflate(R.layout.feedback_item, parent, false)
//        return FeedbackViewHolder(itemView)
//    }
//
//    override fun onBindViewHolder(holder: FeedbackViewHolder, position: Int) {
//        val feedback = feedbacks[position]
//        holder.ratingTextView.text = feedback.rating.toString()
//        holder.commentTextView.text = feedback.comment
//        holder.timestampTextView.text = feedback.timestamp.toString()
//    }
//
//    override fun getItemCount() = feedbacks.size
//}
