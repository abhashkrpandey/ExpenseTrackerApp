package com.mydomain.expensetracker.viewModelFactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mydomain.expensetracker.dao.ExpenseDao
import com.mydomain.expensetracker.viewmodel.HomeScreenViewModel

class HomeViewModelFactory(private val dao: ExpenseDao): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeScreenViewModel(dao) as T
    }
}