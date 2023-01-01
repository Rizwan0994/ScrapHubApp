package com.example.scrapproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.scrapproject.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {

private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!  //null safe binding object
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentRegisterBinding.inflate(inflater,container,false)
        binding.btnLogin.setOnClickListener{
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}