package com.example.scrapproject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scrapproject.models.ScheduleRequest
import com.example.scrapproject.repository.ScheduleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(private  val scheduleRepository: ScheduleRepository) :ViewModel(){
    val notesLiveData get() = scheduleRepository.scheduleLiveData
    val statusLiveData get() = scheduleRepository.statusLiveData

    fun createSchedule(scheduleRequest: ScheduleRequest){
        viewModelScope.launch {
            scheduleRepository.createSchedule(scheduleRequest)
        }
    }

    //getAllScheduleData function implementation remaining
}