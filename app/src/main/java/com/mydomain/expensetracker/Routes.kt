package com.mydomain.expensetracker

sealed class Routes(val route:String)
{
    object HomeScreen: Routes("home_screen")
    object ExpenseScreen:Routes("expense_screen")
}