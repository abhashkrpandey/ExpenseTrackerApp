package com.mydomain.expensetracker.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mydomain.expensetracker.enums.ButtonActions
import com.mydomain.expensetracker.repository.SignUpRepository
import com.mydomain.expensetracker.requestBody.SignUpRequestBody
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

@HiltViewModel
open class SignUpViewModel @Inject constructor(
    val signUpRepository: SignUpRepository
) : ViewModel() {

    private var _buttonStatus = MutableStateFlow(ButtonActions.NOT_TOUCHED)
    val buttonStatus = _buttonStatus.asStateFlow()

    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()

//    private val _email = MutableStateFlow<String?>(null);
//    val email=_email.asStateFlow();

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword = _confirmPassword.asStateFlow()

    private val _isPasswordsSame = MutableStateFlow(true)

    val isPasswordsSame = _isPasswordsSame.asStateFlow()

    fun setUserName(name: String) {
        _username.value = name
    }

    //    fun setEmail(email:String)
//    {
//        _email.value=email
//    }
    fun setPassword(password: String) {
        _password.value = password
        isPasswordsSame()
    }

    fun setConfirmPassword(confirmPassword: String) {
        _confirmPassword.value = confirmPassword
        isPasswordsSame()
    }

    fun isPasswordsSame()
    {
        if(_password.value.equals(_confirmPassword.value))
            _isPasswordsSame.value =true
        else
            _isPasswordsSame.value=false
    }

     fun signUp() {
        viewModelScope.launch {
            _buttonStatus.value= ButtonActions.LOADING
            try {
                signUpRepository.signUpUser(
                    SignUpRequestBody(username.value, password.value)
                )
                _buttonStatus.value= ButtonActions.SUCCESS
            } catch (e: IOException)
            {
                _buttonStatus.value= ButtonActions.ERROR
                Log.d("Abhash-debug","${e.message}")
            }
            catch (e: HttpException)
            {
                _buttonStatus.value= ButtonActions.ERROR
                Log.d("Abhash-debug","${e.message}")

            }

        }
    }


}