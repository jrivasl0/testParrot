package com.rivas.testparrot.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

abstract class CoroutinesViewModel: ViewModel() {
    val uiScope = CoroutineScope(Dispatchers.Main)

    val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    val _error = MutableLiveData<Exception>()
    val errorResponse: LiveData<Exception> get() = _error
}