package com.example.scrapproject

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scrapproject.models.UserRequest
import com.example.scrapproject.models.UserResponse
import com.example.scrapproject.repository.UserRepository
import com.example.scrapproject.utils.Helper
import com.example.scrapproject.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.function.Predicate.isEqual
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val userRepository: UserRepository):ViewModel() {
    val userResponseLiveData:LiveData<NetworkResult<UserResponse>>
    get()=userRepository.userResponseLiveData

    fun registerUser(userRequest: UserRequest){
        viewModelScope.launch {
            userRepository.registerUser(userRequest);
        }
    }
    fun loginUser(userRequest: UserRequest){
        viewModelScope.launch {
            userRepository.loginUser(userRequest);
        }
    }

    fun validateCredentials(userName: String,contact:String, emailAddress: String, address:String , password: String,
                            isLogin: Boolean) : Pair<Boolean, String> {

        var result = Pair(true, "")
        if((!isLogin&&TextUtils.isEmpty(emailAddress)) || (!isLogin && TextUtils.isEmpty(userName)) || TextUtils.isEmpty(password)||TextUtils.isEmpty(contact)||(!isLogin&&TextUtils.isEmpty(address))){
            result = Pair(false, "Please provide the credentials")
        }
        else if(!isLogin&& !Helper.isValidEmail(emailAddress)){
            result = Pair(false, "Email is invalid")
        }
        else if(contact.length<13||contact.length>13||contact[0].equals('+').not()||contact[1].equals('9').not()||contact[2].equals('2').not())
        {
            result = Pair(false, "Contact is invalid")

        }
        else if(!TextUtils.isEmpty(password) && password.length <= 8){
            result = Pair(false, "Password length should be greater than 8")
        }

        return result
    }
}