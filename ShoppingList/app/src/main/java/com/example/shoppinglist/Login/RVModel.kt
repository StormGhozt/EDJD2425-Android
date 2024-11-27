package com.example.shoppinglist.Login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.google.firebase.auth.FirebaseAuth

data class RegisterState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

class RegisterViewModel : ViewModel() {
    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun onEmailChange(email: String) {
        _state.value = _state.value.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        _state.value = _state.value.copy(password = password)
    }

    fun onRegisterClick(onRegisterSuccess: () -> Unit) {
        val email = _state.value.email
        val password = _state.value.password

        if (email.isBlank() || password.isBlank()) {
            _state.value = _state.value.copy(error = "Email and password cannot be empty")
            return
        }

        _state.value = _state.value.copy(isLoading = true, error = null)

        viewModelScope.launch {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("RegisterViewModel", "createUserWithEmail:success")
                        onRegisterSuccess()
                    } else {
                        Log.w("RegisterViewModel", "createUserWithEmail:failure", task.exception)
                        _state.value = _state.value.copy(error = task.exception?.message)
                    }
                    _state.value = _state.value.copy(isLoading = false)
                }
        }
    }
}