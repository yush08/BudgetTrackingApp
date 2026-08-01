package com.example.budgettracking.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val _authState = MutableStateFlow<String?>(null)
    val authState: StateFlow<String?> = _authState
    // 🔹 Sign up user
    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    _authState.value = if (task.isSuccessful) "SignupSuccess" else "SignupFailed"
                }
        }
    }
    // 🔹 Login user
    fun login(email: String, password: String) {
        viewModelScope.launch {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    _authState.value = if (task.isSuccessful) "LoginSuccess" else "LoginFailed"
                }
        }
    }
    // 🔹 Check if user already logged in
    fun checkUser() {
        _authState.value = if (auth.currentUser != null) "AlreadyLoggedIn" else null
    }

    // 🔹 Logout
    fun logout() {
        auth.signOut()
        _authState.value = "LoggedOut"
    }
}