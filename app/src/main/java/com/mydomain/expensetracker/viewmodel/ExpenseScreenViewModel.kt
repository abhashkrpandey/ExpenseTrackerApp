package com.mydomain.expensetracker.viewmodel


import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mydomain.expensetracker.dao.ExpenseDao
import com.mydomain.expensetracker.entities.Expense
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date


class ExpenseScreenViewModel(val dao: ExpenseDao) : ViewModel() {

    private val _toastDescription= MutableStateFlow<String?>(null);
    val toastDescription=_toastDescription.asStateFlow()
//    private val dao: ExpenseDao = dao
    val list = listOf("Food", "Transport", "Rent", "Recharge", "Other")
    private var _expense = MutableStateFlow<String>("")
    val expense = _expense.asStateFlow()

    private var _description = MutableStateFlow<String>("")
    val description = _description.asStateFlow()

    private val _transactionType = MutableStateFlow("Debit")

    val transactionType =_transactionType.asStateFlow();

    private var _dateOfExpense= MutableStateFlow<Long>(System.currentTimeMillis())


    private var _showDatePicker = MutableStateFlow<Boolean>(false)
    val showDatePicker = _showDatePicker.asStateFlow()


    fun setTransactionType(type:String)
    {
        _transactionType.value=type
    }

    fun setExpense(expense: String) {
        _expense.value = expense
    }

    fun setDescription(description:String) {
         _description.value=description
    }

    fun getDate(): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        return formatter.format(Date(_dateOfExpense.value))
    }

    fun setDate(date:Long) {
        _dateOfExpense.value=date
    }

    fun toggleShowDatePicker() {
        Log.d("sfsfs","something happening")
       _showDatePicker.value=_showDatePicker.value.not()
    }

    fun saveExpense() {
        try {
            val expense = Expense(
                amount = _expense.value.toDouble(),
                dateOfExpense = _dateOfExpense.value,
                category = _description.value,
            )
            viewModelScope.launch {
                dao.insertExpense(expense)
                _toastDescription.value="Saved Successfully"
                delay(500)
                _toastDescription.value=null
            }
        } catch (error: Exception) {
            _toastDescription.value="Failed To Save"
            viewModelScope.launch {
                delay(500)
                _toastDescription.value=null
            }
        }
    }

}