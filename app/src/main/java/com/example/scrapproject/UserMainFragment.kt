package com.example.scrapproject

import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import com.example.scrapproject.databinding.FragmentUserMainBinding
import com.example.scrapproject.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class UserMainFragment : Fragment() {
    private var _binding: FragmentUserMainBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var tokenManager: TokenManager

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
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        binding.txtlogout.setOnClickListener{
            sharedPrefs.edit().remove("USER_TOKEN").apply()
            // Get the nav controller
            val navController = findNavController()
            navController.navigate(R.id.loginFragment)
            navController.popBackStack(R.id.loginFragment, false)

             // findNavController().navigate(R.id.action_userMainFragment_to_loginFragment)


        }
        binding.txtfeedback.setOnClickListener{
            findNavController().navigate(R.id.action_userMainFragment_to_userFeedbackFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()


    }


}