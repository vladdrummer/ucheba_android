package com.vladdrummer.uchebaandroid.ui.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vladdrummer.uchebaandroid.R
import com.vladdrummer.uchebaandroid.api.Repo
import kotlinx.coroutines.*

class LoginViewModel() : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    private val coroutineContext: CoroutineDispatcher = Dispatchers.IO
    private val coroutineScope = CoroutineScope(coroutineContext + Job())

    fun login(username: String, password: String) {

        coroutineScope.launch {
            val response = Repo.login(username, password)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    _loginResult.value =
                        LoginResult(success = LoggedInUserView(displayName = "Logged"))
                } else {
                    _loginResult.value = LoginResult(error = R.string.login_failed)
                }
            }
        }

    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 2
    }

}