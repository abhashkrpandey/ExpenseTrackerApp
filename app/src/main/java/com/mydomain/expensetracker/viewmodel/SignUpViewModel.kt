package com.mydomain.expensetracker.viewmodel

import androidx.lifecycle.ViewModel
import com.mydomain.expensetracker.enums.ButtonActions
import com.mydomain.expensetracker.enums.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
open class SignUpViewModel @Inject constructor(): ViewModel() {

    private var _uiState = MutableStateFlow<ButtonActions>(ButtonActions.NOT_TOUCHED)
    val uiState = _uiState.asStateFlow()

    private val _username = MutableStateFlow<String>("")
    val username=_username.asStateFlow()

//    private val _email = MutableStateFlow<String?>(null);
//    val email=_email.asStateFlow();

    private val _password = MutableStateFlow<String>("")
    val password=_password.asStateFlow()

    private val _confirmPassword = MutableStateFlow<String>("")
    val confirmPassword=_confirmPassword.asStateFlow()

    fun setUserName(name:String)
    {
        _username.value=name
    }
//    fun setEmail(email:String)
//    {
//        _email.value=email
//    }
    fun setPassword(password:String)
    {
        _password.value=password
    }
    fun setConfirmPassword(confirmPassword:String)
    {
        _confirmPassword.value=confirmPassword
    }

}