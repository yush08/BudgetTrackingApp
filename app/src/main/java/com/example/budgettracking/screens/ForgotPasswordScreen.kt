package com.example.budgettracking.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.budgettracking.ui.theme.AppAccent
import com.example.budgettracking.ui.theme.AppBackground
import com.example.budgettracking.ui.theme.AppTextPrimary
import com.example.budgettracking.ui.theme.AppTextSecondary
import com.example.budgettracking.ui.theme.Outfit
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ForgotPasswordScreen(onResetDone: () -> Unit) {

    var email by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Reset Password",
            color = AppTextPrimary,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Outfit
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Enter your email and we'll send you a link to reset your password.",
            color = AppTextSecondary,
            fontSize = 14.sp,
            fontFamily = Outfit
        )

        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AppAccent,
                focusedLabelColor = AppAccent,
                cursorColor = AppAccent
            )
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                if (email.isNotBlank()) {
                    isLoading = true
                    auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener { task ->
                            isLoading = false
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    context,
                                    "Password reset email sent successfully.",
                                    Toast.LENGTH_LONG
                                ).show()
                                onResetDone()
                            } else {
                                Toast.makeText(
                                    context,
                                    task.exception?.message ?: "Failed to send reset email.",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                } else {
                    Toast.makeText(context, "Please enter your email", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(AppAccent)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = Color.Black,
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text("Send Reset Link", color = Color.Black)
            }
        }
    }
}
