package com.mydomain.expensetracker

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mydomain.expensetracker.screens.ExpenseScreen
import com.mydomain.expensetracker.screens.HomeScreen
import com.mydomain.expensetracker.screens.SignInScreen
import com.mydomain.expensetracker.screens.SignUpScreen
import com.mydomain.expensetracker.screens.SplashScreen
import com.mydomain.expensetracker.ui.theme.ExpenseTrackerTheme
import com.mydomain.expensetracker.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController= rememberNavController()
            ExpenseTrackerTheme {
                NavHost(navController=navController, startDestination = Routes.SplashScreen.route)
                {
                    composable(Routes.HomeScreen.route)
                    {
                        HomeScreen(navController)
                    }
                    composable(Routes.ExpenseScreen.route)
                    {
                        ExpenseScreen(navController)
                    }
                    composable (Routes.SignUpScreen.route)
                    {
                        SignUpScreen(navController,hiltViewModel())
                    }
                    composable (Routes.SignInScreen.route)
                    {
                        SignInScreen(navController)
                    }
                    composable(Routes.SplashScreen.route) {
                       SplashScreen(hiltViewModel(), navController)
                    }
                }
            }
        }
    }
}
