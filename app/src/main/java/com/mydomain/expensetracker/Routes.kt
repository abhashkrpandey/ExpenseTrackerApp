package com.mydomain.expensetracker

sealed class Routes(val route:String)
{
    object HomeScreen: Routes("home_screen")
    object ExpenseScreen:Routes("expense_screen")
    object SignUpScreen:Routes("signup_screen")
    object SignInScreen:Routes("signin_screen")
    object SplashScreen:Routes(route = "splash_screen")
}