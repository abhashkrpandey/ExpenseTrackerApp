package com.mydomain.expensetracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mydomain.expensetracker.enums.UiState
import com.mydomain.expensetracker.dao.ExpenseDao
import com.mydomain.expensetracker.entities.Expense
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

class HomeScreenViewModel(val dao: ExpenseDao) : ViewModel() {

    private var _uiState = MutableStateFlow<UiState>(UiState.LOADING)
    val uiState = _uiState.asStateFlow()
    private var _expenseList = MutableStateFlow<List<Expense>>(emptyList())

    private val _expenseMap = MutableStateFlow<Map<DateStampOfExpense, List<Expense>>>(emptyMap())

    val expenseMap = _expenseMap.asStateFlow();

    data class DateStampOfExpense(
        val month: String,
        val year: String,
    )


    init {
        viewModelScope.launch {
            try {
                dao.fetchExpense().collect { it->
                    _expenseList.value=it
                categoriazationOfData()
                _uiState.value = UiState.SUCCESS
                }
            } catch (exception: Exception) {
                _uiState.value = UiState.FAILURE
            }
        }
    }

    fun categoriazationOfData() {
        _expenseList.value = _expenseList.value.sortedByDescending { it.dateOfExpense }
        val expenseMapTemp = mutableMapOf<DateStampOfExpense, MutableList<Expense>>()
        val formatterMonth = SimpleDateFormat("MM");
        val formatterYear = SimpleDateFormat("yyyy")
        for (item in _expenseList.value) {
            val month = formatterMonth.format(Date(item.dateOfExpense))
            val year = formatterYear.format(Date(item.dateOfExpense))
            val dateStampOfExpense = DateStampOfExpense(month, year)
            if (expenseMapTemp.containsKey(dateStampOfExpense).not()) {
                val list = mutableListOf<Expense>()
                list.add(item)
                expenseMapTemp[dateStampOfExpense] = list
            } else {
                expenseMapTemp[dateStampOfExpense]?.add(item)
            }
        }
        _expenseMap.value = expenseMapTemp
    }

}