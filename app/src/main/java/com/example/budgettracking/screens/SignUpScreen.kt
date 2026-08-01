package com.example.budgettracking.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.budgettracking.ui.theme.AppAccent
import com.example.budgettracking.ui.theme.AppBackground
import com.example.budgettracking.ui.theme.AppDivider
import com.example.budgettracking.ui.theme.AppSurfaceVariant
import com.example.budgettracking.ui.theme.AppTextPrimary
import com.example.budgettracking.ui.theme.AppTextSecondary
import com.example.budgettracking.ui.theme.Outfit
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun SignUpScreen(onSignUpSuccess: () -> Unit, onSignInClick: () -> Unit) {

    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    var loading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 48.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Sign up!",
            color = AppTextPrimary,
            fontSize = 38.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Outfit
        )

        Spacer(Modifier.height(30.dp))

        Text("Full name*", color = AppTextPrimary, fontSize = 14.sp, fontFamily = Outfit)
        Spacer(Modifier.height(6.dp))
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            placeholder = { Text("Enter your Full name", color = AppTextSecondary) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            colors = textFieldColors()
        )

        Spacer(Modifier.height(16.dp))

        Text("E-mail*", color = AppTextPrimary, fontSize = 14.sp, fontFamily = Outfit)
        Spacer(Modifier.height(6.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("Enter your email", color = AppTextSecondary) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            colors = textFieldColors()
        )

        Spacer(Modifier.height(16.dp))

        Text("Password*", color = AppTextPrimary, fontSize = 14.sp, fontFamily = Outfit)
        Spacer(Modifier.height(6.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("********", color = AppTextSecondary) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = "Toggle password visibility",
                        tint = AppTextSecondary
                    )
                }
            },
            colors = textFieldColors()
        )

        Spacer(Modifier.height(16.dp))

        Text("Phone number", color = AppTextPrimary, fontSize = 14.sp, fontFamily = Outfit)
        Spacer(Modifier.height(6.dp))
        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            placeholder = { Text("Enter your Phone number", color = AppTextSecondary) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            colors = textFieldColors()
        )

        Spacer(Modifier.height(12.dp))

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                fontSize = 13.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Spacer(Modifier.height(18.dp))

        Button(
            onClick = {
                if (name.isBlank() || email.isBlank() || password.isBlank()) {
                    errorMessage = "Please fill all required fields"
                    return@Button
                }

                loading = true
                errorMessage = ""

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val userId = auth.currentUser?.uid ?: ""
                            val userData = hashMapOf(
                                "uid" to userId,
                                "name" to name,
                                "email" to email,
                                "phone" to phone,
                                "createdAt" to System.currentTimeMillis()
                            )

                            firestore.collection("users").document(userId)
                                .set(userData)
                                .addOnSuccessListener {
                                    loading = false
                                    Toast.makeText(context, "Account created!", Toast.LENGTH_SHORT).show()
                                    onSignUpSuccess()
                                }
                                .addOnFailureListener { e ->
                                    loading = false
                                    errorMessage = "Failed to save data: ${e.message}"
                                }
                        } else {
                            loading = false
                            errorMessage = "Signup failed: ${task.exception?.message}"
                        }
                    }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AppAccent)
        ) {
            if (loading) {
                CircularProgressIndicator(
                    color = Color.Black,
                    modifier = Modifier.size(22.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Text("Create account", color = Color.Black, fontSize = 16.sp, fontFamily = Outfit)
            }
        }

        Spacer(Modifier.height(24.dp))

        Row {
            Text("Already have an account?", color = AppTextPrimary, fontFamily = Outfit)
            Spacer(Modifier.width(4.dp))
            Text(
                "Sign in",
                color = AppAccent,
                fontFamily = Outfit,
                modifier = Modifier.clickable { onSignInClick() }
            )
        }
    }
}

@Composable
private fun textFieldColors() = OutlinedTextFieldDefaults.colors(
    unfocusedBorderColor = AppDivider,
    focusedBorderColor = AppAccent,
    unfocusedContainerColor = AppSurfaceVariant,
    focusedContainerColor = AppSurfaceVariant,
    focusedTextColor = AppTextPrimary,
    unfocusedTextColor = AppTextPrimary
)
