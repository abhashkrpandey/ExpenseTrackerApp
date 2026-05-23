package com.mydomain.expensetracker.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mydomain.expensetracker.Routes
import com.mydomain.expensetracker.ui.theme.ExpenseTrackerTheme
import com.mydomain.expensetracker.viewmodel.SplashScreenViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@Composable
fun SplashScreen (
    viewModel: SplashScreenViewModel = hiltViewModel(),
    navController: NavController) {
    val scale = remember {
        Animatable(0.5f)
    }

    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 1.5f,
            animationSpec = tween(
                durationMillis = 2000,
                easing = FastOutSlowInEasing
            )
        )
        if(viewModel.isAccessTokenAvailable())
        {
        navController.navigate(Routes.HomeScreen.route)
        }
        else{
            navController.navigate(Routes.SignUpScreen.route)
        }
    }
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text("ExpenseTracker App",fontSize = 20.sp,
                modifier = Modifier.scale(scale.value))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview1() {
    ExpenseTrackerTheme() {
        val navController = rememberNavController()
        SplashScreen(viewModel = hiltViewModel(),navController)
    }
}