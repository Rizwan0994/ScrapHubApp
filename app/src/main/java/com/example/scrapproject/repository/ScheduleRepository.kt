package com.example.scrapproject.repository

import androidx.lifecycle.MutableLiveData
import com.example.scrapproject.api.ScheduleAPI
import com.example.scrapproject.models.ScheduleRequest
import com.example.scrapproject.models.ScheduleResponse
import com.example.scrapproject.utils.NetworkResult
import retrofit2.Response
import javax.inject.Inject

class ScheduleRepository @Inject constructor(private val scheduleAPI: ScheduleAPI) {
    private val _scheduleLiveData=MutableLiveData<NetworkResult<List<ScheduleResponse>>>()
    val scheduleLiveData get() = _scheduleLiveData
    private val _statusLiveData=MutableLiveData<NetworkResult<Pair<Boolean,String>>>()
    val statusLiveData get() = _statusLiveData

    suspend fun createSchedule(scheduleRequest: ScheduleRequest){
        _statusLiveData.postValue(NetworkResult.Loading())
        val response=scheduleAPI.createSchedulePickup(scheduleRequest)
        handleResponse(response,"Schedule Pickup Created")
    }

    //getSchedule data function implementation remaining

    private fun handleResponse(response: Response<ScheduleResponse>, message:String){
        if(response.isSuccessful&& response.body()!=null){
            _statusLiveData.postValue(NetworkResult.Success(Pair(true,message)))
        }else{
            _statusLiveData.postValue(NetworkResult.Success(Pair(false,"Something went wrong")))
        }
    }
}