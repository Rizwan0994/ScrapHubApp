package com.example.scrapproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.scrapproject.databinding.FragmentMainBinding
import com.example.scrapproject.databinding.FragmentUserMainBinding


class UserMainFragment : Fragment() {
    private var _binding: FragmentUserMainBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentUserMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtlogout.setOnClickListener{
            findNavController().navigate(R.id.action_userMainFragment_to_loginFragment)
           // getActivity()?.finishAffinity();
        //  activity?.finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()


    }


}