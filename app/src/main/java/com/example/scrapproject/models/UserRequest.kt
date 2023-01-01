package com.example.scrapproject.models

data class UserRequest(
    val address: String,
    val contact: String,
    val email: String,
    val password: String,
    val username: String
)