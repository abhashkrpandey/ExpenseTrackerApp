package com.mydomain.expensetracker.repository

import com.mydomain.expensetracker.api.SignUpUserApi
import com.mydomain.expensetracker.requestBody.SignUpRequestBody
import com.mydomain.expensetracker.responseBody.SignUpResponse
import javax.inject.Inject

class SignUpRepository @Inject constructor(
    val api: SignUpUserApi
) {
    suspend fun signUpUser(signUpRequestBody: SignUpRequestBody): SignUpResponse {
       return api.signUp(signUpRequestBody)
    }
}