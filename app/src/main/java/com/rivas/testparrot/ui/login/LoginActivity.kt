package com.rivas.testparrot.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import com.rivas.testparrot.R
import com.rivas.testparrot.api.model.TokenResponse
import com.rivas.testparrot.databinding.ActivityLoginBinding
import com.rivas.testparrot.lifecycleowner.TestParrotLifecycleOwner
import com.rivas.testparrot.ui.menu.MenuActivity
import com.rivas.testparrot.utils.Preferences
import com.rivas.testparrot.utils.extensions.isValidEmail
import com.rivas.testparrot.utils.extensions.observer
import com.rivas.testparrot.utils.extensions.visible
import dagger.android.DaggerActivity
import javax.inject.Inject

class LoginActivity : DaggerActivity() {

    @Inject
    lateinit var loginViewModel: LoginViewModel

    private val testParrotLifeCycleOwner = TestParrotLifecycleOwner()

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        testParrotLifeCycleOwner.lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)
        configureBinding()
        initObservers()
        validateSession()
    }

    private fun validateSession() {
        if (!Preferences.getData(Preferences.TOKEN, this).isNullOrEmpty())
            loginViewModel.validateToken()
    }

    private fun configureBinding() {
        binding.viewModel = loginViewModel
        binding.buttonLogin.setOnClickListener {
            if (isValidate()) {
                loginViewModel.createToken()
            }
        }
    }

    private fun isValidate(): Boolean =
        validatePassword() && validateEmail()

    private fun validateEmail(): Boolean {
        when {
            binding.email.text.toString().trim().isEmpty() -> {
                binding.emailTextInputLayout.error = "Required Field"
                binding.email.requestFocus()
                return false
            }
            !binding.email.text.toString().isValidEmail() -> {
                binding.emailTextInputLayout.error = "Invalid Email"
                binding.email.requestFocus()
                return false
            }
            else -> {
                binding.emailTextInputLayout.isErrorEnabled = false
            }
        }
        return true
    }

    private fun validatePassword(): Boolean {
        if (binding.password.text.toString().trim().isEmpty()) {
            binding.passwordTextInputLayout.error = "Required Field"
            binding.password.requestFocus()
            return false
        } else {
            binding.passwordTextInputLayout.isErrorEnabled = false
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        testParrotLifeCycleOwner.lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    override fun onDestroy() {
        super.onDestroy()
        testParrotLifeCycleOwner.lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }

    private fun initObservers() {
        observerToken()
        observerError()
        observerLoading()
        observerValidToken()
    }

    private fun observerValidToken() {
        loginViewModel.validateTokenResponseObserver.observer(testParrotLifeCycleOwner) {
            if (it) startMenu()
            else loginViewModel.refreshToken()
        }
    }

    private fun observerLoading() {
        loginViewModel._loading.observer(testParrotLifeCycleOwner) {
            binding.loader.visible(it)
            binding.buttonLogin.visible(!it)
        }
    }

    private fun observerError() {
        loginViewModel._error.observer(testParrotLifeCycleOwner) {
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun observerToken() {
        loginViewModel.tokenResponseObserver.observer(testParrotLifeCycleOwner) {
            processData(it)
        }
    }

    private fun startMenu() {
        startActivity(Intent(this, MenuActivity::class.java))
        finish()
    }

    private fun processData(it: TokenResponse) {
        Preferences.putData(Preferences.TOKEN, it.access, this)
        Preferences.putData(Preferences.REFRESH, it.refresh, this)
        startMenu()
    }
}