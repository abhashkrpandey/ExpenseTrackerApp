package com.mydomain.expensetracker.viewmodel

import androidx.lifecycle.ViewModel
import com.mydomain.expensetracker.utils.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(private val tokenManager: TokenManager) :
    ViewModel() {


    fun isAccessTokenAvailable(): Boolean {
        return !tokenManager.getRefreshToken().isNullOrEmpty()
    }

    fun putAccessToken(accessToken: String) {
        tokenManager.saveRefreshToken(accessToken)
    }
}