package com.example.scrapproject.api

import com.example.scrapproject.models.ScheduleRequest
import com.example.scrapproject.models.ScheduleResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ScheduleAPI {
  //  @GET("/schedule")
    //suspend fun getSchedulePickup(): Response<List<ScheduleResponse>>

    @POST("/schedule")
    suspend fun createSchedulePickup(@Body scheduleRequest: ScheduleRequest):Response<ScheduleResponse>
}