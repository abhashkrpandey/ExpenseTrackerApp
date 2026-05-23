package com.mydomain.expensetracker.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mydomain.expensetracker.Routes
import com.mydomain.expensetracker.ui.theme.ExpenseTrackerTheme
import com.mydomain.expensetracker.viewmodel.SignUpViewModel


@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val username = viewModel.username.collectAsState()
    val password = viewModel.password.collectAsState()
    val confirmPassword = viewModel.confirmPassword.collectAsState()
    SignUpScreenForm(
        {
            viewModel.setUserName(username.value)
        },
        {
            viewModel.setPassword(password.value)
        },
        {
            viewModel.setConfirmPassword(confirmPassword.value)
        },
        {
            navController.navigate(Routes.HomeScreen)
        },
        username.value,
        password.value,
        confirmPassword.value,
        )
}

@Composable
fun TextFieldWrapper(functionality: (text: String) -> Unit, text: String, value: String) {
    OutlinedTextField(
        value = value,
        placeholder = {
            Text(text)
        },
        onValueChange =
            { it ->
                functionality(it)
            },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text
        )
    )
}

@Composable
fun SignUpScreenForm(
    userNameSetter: () -> Unit,
    passwordSetter: () -> Unit,
    confirmPasswordSetter: () -> Unit,
    signUp: () -> Unit,
    username: String,
    password: String,
    confirmPassword: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            TextFieldWrapper({ userNameSetter() }, "UserName", username)
//        TextFieldWrapper({}, "Email")
            TextFieldWrapper({ passwordSetter() }, "Password", password)
            TextFieldWrapper({ confirmPasswordSetter() }, "Confirm Password", confirmPassword)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                OutlinedButton(
                    onClick =
                        {
                            signUp()
                        },
                    modifier = Modifier.height(50.dp),
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Text("Signup")
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview1() {
    ExpenseTrackerTheme() {
        SignUpScreenForm(
            {}, {}, {},
             {},
            "","",""
        )
    }
}
