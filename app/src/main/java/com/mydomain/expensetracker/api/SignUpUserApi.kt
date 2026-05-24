package com.mydomain.expensetracker.api

import com.mydomain.expensetracker.requestBody.SignUpRequestBody
import com.mydomain.expensetracker.responseBody.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpUserApi {

    @POST("/signup")
    suspend fun signUp(@Body signUpRequestBody: SignUpRequestBody): SignUpResponse
}