package com.example.scrapproject

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.scrapproject.databinding.FragmentMainBinding


class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentMainBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(requireArguments().getString("uname").isNullOrEmpty()){
            binding.textView2.text="Hi"
        }else{
            binding.textView2.text=requireArguments().getString("uname")
        }

      //  val st=requireArguments().getString("uname")
        binding.btnScheduleMain.setOnClickListener{
           findNavController().navigate(R.id.action_mainFragment_to_scheduleFragment)


        }
        binding.btnNearestYardMain.setOnClickListener{
            findNavController().navigate(R.id.action_mainFragment_to_mapsFragment2)
        }
        //bottom bar navigation...
        val bottomNavigationView=binding.bottomNavigationView
        val navController=findNavController()
        bottomNavigationView.setupWithNavController(navController)
    }


}