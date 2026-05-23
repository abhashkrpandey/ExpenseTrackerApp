package com.mydomain.expensetracker.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mydomain.expensetracker.Routes
import com.mydomain.expensetracker.ui.theme.ExpenseTrackerTheme


@Composable
fun SignInScreen(navController: NavController) {

}

@Preview
@Composable
fun Preview() {
    ExpenseTrackerTheme() {
        val navController = rememberNavController()
        SignInScreen(navController)
    }
}