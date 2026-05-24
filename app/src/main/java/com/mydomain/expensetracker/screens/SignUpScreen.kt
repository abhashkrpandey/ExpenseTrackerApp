package com.mydomain.expensetracker.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mydomain.expensetracker.Routes
import com.mydomain.expensetracker.enums.ButtonActions
import com.mydomain.expensetracker.ui.theme.ExpenseTrackerTheme
import com.mydomain.expensetracker.utils.ErrorContent
import com.mydomain.expensetracker.viewmodel.SignUpViewModel


@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val username = viewModel.username.collectAsState()
    val password = viewModel.password.collectAsState()
    val confirmPassword = viewModel.confirmPassword.collectAsState()
    val buttonStatus = viewModel.buttonStatus.collectAsState()
    val isPasswordsSame =viewModel.isPasswordsSame.collectAsState()
    SignUpScreenForm(
        { username ->
            viewModel.setUserName(username)
        },
        { password ->
            viewModel.setPassword(password)
        },
        { confirmPassword ->
            viewModel.setConfirmPassword(confirmPassword)
        },
        {
            viewModel.signUp()
        },
        username.value,
        password.value,
        confirmPassword.value,
        buttonStatus.value,
        navController,
        isPasswordsSame.value
    )
}


@Composable
fun SignUpScreenForm(
    userNameSetter: (userName:String) -> Unit,
    passwordSetter: (password:String) -> Unit,
    confirmPasswordSetter: (confirmPassword:String) -> Unit,
    signUp: () -> Unit,
    username: String,
    password: String,
    confirmPassword: String,
    buttonStatus: ButtonActions,
    navController: NavController,
    isPasswordSame : Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//        TextFieldWrapper({}, "Email")
            OutlinedTextField(
                value = username,
                label={
                    Text("UserName")
                },
                onValueChange =
                    { it ->
                        userNameSetter(it)
                    },
                keyboardOptions = KeyboardOptions(
                    keyboardType =  KeyboardType.Text
                ),
            )
            OutlinedTextField(
                value = password,
                label={
                    Text("Password")
                },
                onValueChange =
                    { it ->
                        passwordSetter(it)
                    },
                visualTransformation =  PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType =   KeyboardType.Password
                )
            )
            OutlinedTextField(
                    value = confirmPassword,
            label={
                Text("ConfirmPassword")
            },
            onValueChange =
                { it ->
                    confirmPasswordSetter(it)
                },
            visualTransformation =  PasswordVisualTransformation() ,
            keyboardOptions = KeyboardOptions(
                keyboardType =   KeyboardType.Password
            ),
            colors = if(isPasswordSame) OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Unspecified,
                unfocusedBorderColor = Color.Unspecified
            ) else OutlinedTextFieldDefaults.colors(focusedBorderColor = Color.Red,
                unfocusedBorderColor = Color.Red)
            )
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                OutlinedButton(
                    onClick =
                        {
                            signUp()
                        },
                    modifier = Modifier.height(50.dp),
                    shape = RoundedCornerShape(5.dp),
                    enabled = isPasswordSame
                ) {
                    when (buttonStatus) {
                        ButtonActions.LOADING -> CircularProgressIndicator()
                        ButtonActions.SUCCESS -> navController.navigate(Routes.HomeScreen.route)
                        ButtonActions.ERROR -> ErrorContent()
                        ButtonActions.NOT_TOUCHED -> Text("Signup")
                    }
                }
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun Preview1() {
    val navController: NavController = rememberNavController()
    ExpenseTrackerTheme() {
        SignUpScreenForm(
            {}, {}, {},
            {},
            "", "", "", ButtonActions.NOT_TOUCHED,
            navController,
            true
        )
    }
}
