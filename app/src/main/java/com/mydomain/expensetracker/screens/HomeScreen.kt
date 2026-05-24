package com.mydomain.expensetracker.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mydomain.expensetracker.Green10
import com.mydomain.expensetracker.Green5
import com.mydomain.expensetracker.Routes
import com.mydomain.expensetracker.enums.UiState
import com.mydomain.expensetracker.White10
import com.mydomain.expensetracker.White5
import com.mydomain.expensetracker.database.AppDatabase
import com.mydomain.expensetracker.entities.Expense
import com.mydomain.expensetracker.enums.TransactionType
import com.mydomain.expensetracker.ui.theme.ExpenseTrackerTheme
import com.mydomain.expensetracker.utils.monthArray
import com.mydomain.expensetracker.viewModelFactories.HomeViewModelFactory
import com.mydomain.expensetracker.viewmodel.HomeScreenViewModel
import java.text.SimpleDateFormat
import java.util.Date
import com.mydomain.expensetracker.utils.ErrorContent

@Composable
fun HomeScreen(navController: NavController) {
    var context = LocalContext.current
    val appDatabase = AppDatabase.getInstance(context);
    val dao = appDatabase.expenseDao()

    val factory = HomeViewModelFactory(dao)
    val viewModel: HomeScreenViewModel = viewModel(factory = factory)
    val uiState = viewModel.uiState.collectAsState()
    val expenseMap = viewModel.expenseMap.collectAsState();
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 60.dp, start = 20.dp, end = 20.dp),
    ) {
        Column(modifier = Modifier.align(Alignment.TopStart))
        {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center)
            {
                Text("All Expenses")
            }
            Row(modifier = Modifier.fillMaxWidth())
            {
                OutlinedButton(onClick = { /* sort */ }, shape = RoundedCornerShape(5.dp)) {
                    Icon(Icons.AutoMirrored.Filled.Sort, contentDescription = "Sort")
                }
            }
            HomeScreenContent(uiState.value, expenseMap.value)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomEnd)
                .padding(bottom = 60.dp),
            horizontalArrangement = Arrangement.End
        )
        {
            IconButton(
                onClick = {
                    navController.navigate(Routes.ExpenseScreen.route)
                }, colors = IconButtonColors(
                    containerColor = Green10, contentColor = White10,
                    disabledContainerColor = Green5,
                    disabledContentColor = White5
                ), modifier = Modifier.size(60.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "add expense")
            }
        }
    }
}

@Composable
fun HomeScreenContent(
    uiState: UiState,
    expenseMap: Map<HomeScreenViewModel.DateStampOfExpense, List<Expense>>
) {
    when (uiState) {
        UiState.LOADING -> {
            CircularProgressIndicator()
        }

        UiState.SUCCESS -> {
            Content(expenseMap)
        }

        UiState.FAILURE -> {
            ErrorContent()
        }
    }
}

@Composable
fun Content(expenseMap: Map<HomeScreenViewModel.DateStampOfExpense, List<Expense>>) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        expenseMap.forEach { (key, expenses) ->
            item {
                Header(month = key.month, year = key.year)
            }
            items(expenses)
            { it ->
                ExpenseRow(item = it, month = key.month)
            }
        }
    }
}

@Composable
fun Header(month: String, year: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Text(text = monthArray[Integer.parseInt(month) - 1], fontSize = 30.sp)
                Text(text = year, fontSize = 30.sp)

            }
        }
    }
    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
fun ExpenseRow(item: Expense, month: String) {
    val formatterDate = SimpleDateFormat("dd");
    val date = formatterDate.format(Date(item.dateOfExpense))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column()
        {
            Text(
                text = item.description,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "${date} ${monthArray[Integer.parseInt(month) - 1]}",
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 20.sp
        )
        }
        Text(
            text = if(item.transactionType== TransactionType.CREDIT) "+${item.amount}" else "-${item.amount}",
            style = MaterialTheme.typography.bodySmall,
            color = if(item.transactionType== TransactionType.CREDIT) Green5 else Color.Red,
            fontSize = 20.sp
        )
    }
    Spacer(modifier = Modifier.height(10.dp))

}




@Preview(showSystemUi = true)
@Composable
private fun Preview1() {
    val navController = rememberNavController()
    ExpenseTrackerTheme {
        HomeScreen(navController)
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview2() {
    ExpenseTrackerTheme {
        Header("Jan", "2026")
    }
}
