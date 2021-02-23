package com.rivas.testparrot.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.rivas.testparrot.AndroidApp
import com.rivas.testparrot.api.model.StringResponse
import com.rivas.testparrot.api.model.TokenResponse
import com.rivas.testparrot.api.model.UserObj
import com.rivas.testparrot.core.CoroutinesViewModel
import com.rivas.testparrot.repository.Repository
import com.rivas.testparrot.utils.Preferences
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: Repository) : CoroutinesViewModel(){

    val user = UserObj("android-challenge@parrotsoftware.io", "8mngDhoPcB3ckV7X")
    val tokenResponseObserver = MutableLiveData<TokenResponse>()
    val validateTokenResponseObserver = MutableLiveData<Boolean>()

    fun createToken() = uiScope.launch {
        _loading.value = true
        when(val token = repository.createToken(user)) {
            is Repository.ApiResult.Success<TokenResponse> -> {
                tokenResponseObserver.value = token.data
            }
            is Repository.ApiResult.Error -> {
                _error.value = token.exception
            }
        }
        _loading.value = false
    }

    fun refreshToken() = uiScope.launch {
        _loading.value = true
        val tokenResponse = TokenResponse(Preferences.getData(Preferences.REFRESH, AndroidApp.appContext).toString(), "")
        when(val token = repository.refreshToken(tokenResponse)) {
            is Repository.ApiResult.Success<TokenResponse> -> {
                tokenResponseObserver.value = token.data
            }
            is Repository.ApiResult.Error -> {
                _error.value = token.exception
            }
        }
        _loading.value = false
    }

    fun validateToken() = uiScope.launch {
        _loading.value = true
        when(val token = repository.validateToken()) {
            is Repository.ApiResult.Success<StringResponse> -> {
                validateTokenResponseObserver.value = token.data.status == "ok"
            }
            is Repository.ApiResult.Error -> {
                validateTokenResponseObserver.value = false
                _error.value = token.exception
            }
        }
        _loading.value = false
    }

    fun onTextChangedUser(s: CharSequence, start: Int, count: Int, after:Int) {
        user.username = s.toString()
    }

    fun onTextChangedPassword(s: CharSequence,  start: Int, count: Int, after:Int) {
        user.username = s.toString()
    }
}