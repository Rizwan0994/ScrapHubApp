package com.example.scrapproject.blogScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scrapproject.R


class blogFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Set up the RecyclerView
        val recyclerView = view.findViewById<RecyclerView>(R.id.blog_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = BlogAdapter(getSampleBlogList(),requireContext())
    }


    private fun getSampleBlogList(): List<Blog> {
        val blogList = mutableListOf<Blog>()
        blogList.add(Blog("Scrap Buyers – Nature’s Best Friends", "Scrap buyers are an essential part of the waste" +
                " management sector in India. We, humans, are growing in a digital age in which every artifact has its limit to be" +
                " used in a particular field. Each piece of equipment gets outdated or worn, let it be a needle or a car. But if a thing " +
                "gets completely worn or outdated doesn’t mean that it’s of no use. ", R.drawable.image_blog_1))
        blogList.add(Blog("Sell on An Online Platform", "Scrap can now be sold on a platform. Interesting right? With advancements in technology around us, it is crucial to even concentrate on the environment and think of its improvement too. " +
                "Because nature gives you all and to keep it clean is our duty. ", R.drawable.image_blog_2))
        blogList.add(Blog("Scrap Collector Near Me – A Click Away", "A click away. Now, with the availability of online raddiwala, you can sell your scrap handily. The online raddiwala near me is Scrap Uncle. Scrap Uncle is an online scrap dealer " +
                "that provides effective doorstep services to its users.", R.drawable.image_blog_3))
        blogList.add(Blog("Get Doorstep Services", "Now get the best doorstep services in exchange for your scrap items.", R.drawable.image_blog_2))
        blogList.add(Blog("ONLINE KABAD – NOW IN Pakistan", "An online platform is another surprise for us. Disposal of Scrap online is a perfect platform where we can start to contribute to society by disposing of kabadi online.", R.drawable.image_blog_1))
        return blogList
    }
}