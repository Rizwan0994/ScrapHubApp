package com.example.scrapproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.scrapproject.databinding.FragmentRegisterBinding
import com.example.scrapproject.models.UserRequest
import com.example.scrapproject.utils.Helper.Companion.hideKeyboard
import com.example.scrapproject.utils.NetworkResult
import com.example.scrapproject.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val authViewModel by activityViewModels<AuthViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        if (tokenManager.getToken() != null) {
            findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            it.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        binding.btnSignUp.setOnClickListener {
            hideKeyboard(it)
            val validationResult = validateUserInput()
            if (validationResult.first) {
                val userRequest = getUserRequest()
                authViewModel.registerUser(userRequest)
            } else {
                showValidationErrors(validationResult.second)
            }
        }
        bindObservers()
    }

    private fun validateUserInput(): Pair<Boolean, String> {

        val userName = binding.txtUsername.text.toString()
        val contact=binding.Contact.text.toString()
        val emailAddress = binding.txtEmail.text.toString()
        val address=binding.address.text.toString()
        val password = binding.txtPassword.text.toString()


        return authViewModel.validateCredentials( userName, contact,emailAddress,address,password, false)
    }

    private fun showValidationErrors(error: String) {
        binding.txtError.text =
            String.format(resources.getString(R.string.txt_error_message, error))
    }


    private fun getUserRequest(): UserRequest {
        return binding.run {
            UserRequest(
                txtUsername.text.toString(),
                Contact.text.toString(),
                txtEmail.text.toString(),
                address.text.toString(),
                txtPassword.text.toString()
            )
        }
    }

    private fun bindObservers() {
        authViewModel.userResponseLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    tokenManager.saveToken(it.data!!.token)
                findNavController().navigate(R.id.action_registerFragment_to_mainFragment,Bundle().apply {
                    putString("uname",binding.txtUsername.text.toString())
                })
                 //  findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
                }
                is NetworkResult.Error -> {
                    binding.txtError.text = it.message
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }

            }

        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


/*private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!  //null safe binding object
  private val authViewModel by viewModels<AuthViewModel> ()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentRegisterBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener{
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        binding.btnSignUp.setOnClickListener{

            authViewModel.registerUser(UserRequest ("test123","92345566768","test00@gmail.com","sargodha","12345678"))
        }
        bindObservers()

    }

    private fun validateUserInput(): Pair<Boolean, String> {
        val emailAddress = binding.txtEmail.text.toString()
        val userName = binding.txtUsername.text.toString()
        val password = binding.txtPassword.text.toString()
        return authViewModel.validateCredentials(emailAddress, userName, password, false)
    }
    private fun showValidationErrors(error: String) {
        binding.txtError.text = String.format(resources.getString(R.string.txt_error_message, error))
    }

    private fun bindObservers() {
        authViewModel.userResponseLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible=false
            when(it){
                is NetworkResult.Success->{
                    findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
                }
                is NetworkResult.Error->{
                    binding.txtError.text=it.message
                }
                is NetworkResult.Loading->{
                    binding.progressBar.isVisible=true
                }

            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
*/
