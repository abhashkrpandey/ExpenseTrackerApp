package com.mydomain.expensetracker.screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mydomain.expensetracker.Routes
import com.mydomain.expensetracker.database.AppDatabase
import com.mydomain.expensetracker.ui.theme.ExpenseTrackerTheme
import com.mydomain.expensetracker.viewModelFactories.ExpenseScreenViewModelFactory
import com.mydomain.expensetracker.viewmodel.ExpenseScreenViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseScreen(navController: NavController) {

    var context = LocalContext.current
    val appDatabase = AppDatabase.getInstance(context);
    val dao = appDatabase.expenseDao()

    val factory = ExpenseScreenViewModelFactory(dao)
    val expenseScreenViewModel: ExpenseScreenViewModel = viewModel(factory = factory)

    val expense = expenseScreenViewModel.expense.collectAsState()
    val description = expenseScreenViewModel.description.collectAsState();
    val list = expenseScreenViewModel.list
    val showDatePicker = expenseScreenViewModel.showDatePicker.collectAsState()

    LaunchedEffect(expenseScreenViewModel.toastDescription) {
        expenseScreenViewModel.toastDescription.collect { description ->
            if (description != null) {
                Toast.makeText(context, description, Toast.LENGTH_LONG).show()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = {
                navController.navigate(Routes.HomeScreen.route)
            }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Cancel"
                )
            }
            Button(onClick = {
                expenseScreenViewModel.saveExpense()
                navController.navigate(Routes.HomeScreen.route)
            }) {
                Text(text = "Save")
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "Add expense",
                fontSize = 40.sp,
                modifier = Modifier.padding(bottom = 50.dp)
            )
        }
        Column(
            modifier = Modifier.padding(start = 20.dp),
            verticalArrangement = Arrangement.spacedBy(40.dp),
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(5.dp))
            {
                OutlinedButton(
                    onClick =
                        {

                        },
                    modifier = Modifier.height(55.dp),
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Receipt,
                        contentDescription = "pick currency"
                    )
                }
                OutlinedTextField(
                    onValueChange = { it: String ->
                        expenseScreenViewModel.setDescription(it)
                    },
                    value = description.value,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    placeholder = {
                        Text(text = "Enter a description")
                    }
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(5.dp))
            {
                OutlinedButton(
                    onClick =
                        {

                        },
                    modifier = Modifier.height(55.dp),
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CurrencyRupee,
                        contentDescription = "pick currency"
                    )
                }
                OutlinedTextField(
                    onValueChange = { it: String ->
                        expenseScreenViewModel.setExpense(it)
                    },
                    value = expense.value,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    placeholder = {
                        Text(text = "0.00")
                    }
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Date:", fontSize = 30.sp)
                Button(
                    onClick = {
                        expenseScreenViewModel.toggleShowDatePicker()
                    },
                    shape = RoundedCornerShape(5.dp),
                ) {
                    Row(horizontalArrangement = Arrangement.SpaceBetween)
                    {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Choose a date"
                        )
                        Text(text = expenseScreenViewModel.getDate())
                    }
                }
            }
            if (showDatePicker.value) {
                val datePickerState = rememberDatePickerState(initialSelectedDate = LocalDate.now())
                DatePickerDialog(
                    onDismissRequest = {
                        expenseScreenViewModel.toggleShowDatePicker()
                    },
                    confirmButton = {
                        Button(onClick = {
                            datePickerState.selectedDateMillis?.let {
                                expenseScreenViewModel.setDate(it)
                            }
                            expenseScreenViewModel.toggleShowDatePicker()

                        }) { Text(text = "Ok") }
                    }, dismissButton = {
                        Button(onClick = { expenseScreenViewModel.toggleShowDatePicker() }) {
                            Text("Cancel")
                        }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
private fun Previewer() {
    val navController = rememberNavController()
    ExpenseTrackerTheme() {
        ExpenseScreen(navController)
    }
}
