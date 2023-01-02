package com.example.scrapproject.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.scrapproject.api.UserAPI
import com.example.scrapproject.models.UserRequest
import com.example.scrapproject.models.UserResponse
import com.example.scrapproject.utils.Constants.TAG
import com.example.scrapproject.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val userAPI: UserAPI) {
    private val _userResponceLiveData=MutableLiveData<NetworkResult<UserResponse>>()
    val userResponseLiveData:LiveData<NetworkResult<UserResponse>>
    get()=_userResponceLiveData

    suspend fun registerUser(userRequest: UserRequest){
        _userResponceLiveData.postValue(NetworkResult.Loading())
        val response=userAPI.signup(userRequest)
        handleResponse(response)

    }
    suspend fun loginUser(userRequest: UserRequest){
        _userResponceLiveData.postValue(NetworkResult.Loading())
        val response=userAPI.signin(userRequest)
        handleResponse(response)
    }

    private fun handleResponse(response: Response<UserResponse>) {
        if (response.isSuccessful && response.body() != null) {
            _userResponceLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _userResponceLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))

        } else {
            _userResponceLiveData.postValue(NetworkResult.Error("Something Went Wrong"))

        }
    }


}