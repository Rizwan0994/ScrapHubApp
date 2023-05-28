package com.example.scrapproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class UserFeedbackFragment : Fragment() {

    private val client = OkHttpClient()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_feedback, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonSubmit = view.findViewById<Button>(R.id.btnSubmit)
        val ratingBar = view.findViewById<RatingBar>(R.id.ratingBar)
        val feedbackName = view.findViewById<EditText>(R.id.etName)
        val feedbackEmail = view.findViewById<EditText>(R.id.etEmail)
        val feedbackFeedback = view.findViewById<EditText>(R.id.etFeedback)
        val feedbackMessage = view.findViewById<TextView>(R.id.tvMessage)

        buttonSubmit.setOnClickListener {
            val name = feedbackName.text.toString()
            val email = feedbackEmail.text.toString()
            val feedback = feedbackFeedback.text.toString()
            val rating = ratingBar.rating

            val json = "{\"name\":\"$name\",\"email\":\"$email\",\"feedback\":\"$feedback\",\"rating\":$rating}"
            val body = json.toRequestBody("application/json".toMediaTypeOrNull())

            val request = Request.Builder()
                .url("https://apitestregs.onrender.com/feedback")
                .post(body)
                .build()

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = client.newCall(request).execute()

                    if (response.isSuccessful) {
                        activity?.runOnUiThread {
                            // Show success message to user
                            Toast.makeText(activity, "Feedback submitted successfully", Toast.LENGTH_SHORT).show()
                            feedbackMessage.text = "Feedback submitted successfully :)"
                            // Clear form fields
                            feedbackName.text?.clear()
                            feedbackEmail.text?.clear()
                            feedbackFeedback.text?.clear()
                            ratingBar.rating = 0F;
                        }
                    } else {
                        activity?.runOnUiThread {
                            // Show error message to user
                            Toast.makeText(activity, "Error submitting feedback", Toast.LENGTH_SHORT).show()
                            feedbackMessage.text = "Error submitting feedback"
                        }
                    }
                } catch (e: IOException) {
                    activity?.runOnUiThread {
                        // Show error message to user
                        Toast.makeText(activity, "Error submitting feedback", Toast.LENGTH_SHORT).show()
                        feedbackMessage.text = "Error submitting feedback"
                    }
                }
            }
        }
    }
}
