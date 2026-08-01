package com.example.budgettracking.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.budgettracking.ui.theme.AppAccent
import com.example.budgettracking.ui.theme.AppBackground
import com.example.budgettracking.ui.theme.AppSurface
import com.example.budgettracking.ui.theme.AppTextPrimary
import com.example.budgettracking.ui.theme.AppTextSecondary
import com.example.budgettracking.ui.theme.Outfit
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onForgotPasswordClick: () -> Unit
) {
    val auth = remember { FirebaseAuth.getInstance() }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    fun attemptLogin() {
        if (email.isBlank() || password.isBlank()) {
            errorMessage = "Please enter your email and password"
            return
        }
        loading = true
        errorMessage = ""
        auth.signInWithEmailAndPassword(email.trim(), password)
            .addOnCompleteListener { task ->
                loading = false
                if (task.isSuccessful) {
                    onLoginClick()
                } else {
                    errorMessage = task.exception?.message ?: "Login failed. Please try again."
                }
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start
    ) {

        Spacer(Modifier.height(60.dp))

        Text(
            text = "Welcome Back!",
            color = AppTextPrimary,
            fontSize = 42.sp ,
            fontWeight = FontWeight.Medium,
            fontFamily = Outfit
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Log in to manage your daily expenses & savings",
            color = AppTextSecondary,
            fontSize = 14.sp
        )

        Spacer(Modifier.height(32.dp))

        // Email
        Text(
            text = "Email",
            color = AppTextPrimary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = Outfit
        )

        Spacer(Modifier.height(6.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(AppSurface, shape = RoundedCornerShape(10.dp))
                .padding(horizontal = 16.dp, vertical = 3.dp)
        ) {
            TextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth(),
                textStyle = LocalTextStyle.current.copy(color = AppTextPrimary),
                placeholder = { Text("Enter your email", color = AppTextSecondary) },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = AppAccent
                )
            )
        }

        Spacer(Modifier.height(14.dp))

        // Password
        Text(
            text = "Password",
            color = AppTextPrimary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = Outfit
        )
        Spacer(Modifier.height(6.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(AppSurface, shape = RoundedCornerShape(10.dp))
                .padding(horizontal = 16.dp, vertical = 3.dp)
        ) {
            TextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth(),
                textStyle = LocalTextStyle.current.copy(color = AppTextPrimary),
                placeholder = { Text("Enter your password", color = AppTextSecondary) },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = AppAccent
                )
            )
        }

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Forgot Password?",
            color = AppAccent,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .align(Alignment.End)
                .clickable { onForgotPasswordClick() }
        )

        if (errorMessage.isNotEmpty()) {
            Spacer(Modifier.height(12.dp))
            Text(
                text = errorMessage,
                color = Color(0xFFFF6B6B),
                fontSize = 13.sp
            )
        }

        Spacer(Modifier.height(24.dp))

        // Sign-In Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .background(AppAccent, shape = RoundedCornerShape(12.dp))
                .clickable(enabled = !loading) { attemptLogin() },
            contentAlignment = Alignment.Center
        ) {
            if (loading) {
                CircularProgressIndicator(
                    color = Color.Black,
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(22.dp)
                )
            } else {
                Text(
                    "Sign In",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Outfit
                )
            }
        }


        Spacer(Modifier.height(20.dp))

        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Don't have an account?", color = AppTextSecondary, fontSize = 14.sp)
            Spacer(Modifier.width(6.dp))
            Text(
                "Sign Up",
                color = AppAccent,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onSignUpClick() }
            )
        }
    }
}
