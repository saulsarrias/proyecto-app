package com.example.andamiosapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ManagerViewModelFactory() : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(ManagerViewModel::class.java)) {
            return ManagerViewModel() as T
        }

        throw java.lang.IllegalArgumentException("Unknown ViewModelclass")
        }
}