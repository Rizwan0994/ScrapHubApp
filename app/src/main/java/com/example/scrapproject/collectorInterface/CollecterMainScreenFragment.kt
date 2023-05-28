package com.example.scrapproject.collectorInterface

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.scrapproject.R




class CollecterMainScreenFragment : Fragment() {

    private lateinit var GernateQRButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_collecter_main_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GernateQRButton=view.findViewById(R.id.btn_GernateQR)
        GernateQRButton.setOnClickListener({
            findNavController().navigate(R.id.action_collecterMainScreenFragment_to_gernateQRFragment)
        })
    }


}