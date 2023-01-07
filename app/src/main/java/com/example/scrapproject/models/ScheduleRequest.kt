package com.example.scrapproject.models

data class ScheduleRequest(
    val  nearestYard: String,
    val itemDetails: String,
    val sDate: String,
    val sTime: String
)