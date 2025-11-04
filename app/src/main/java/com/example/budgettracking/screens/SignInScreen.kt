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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onForgotPasswordClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0B0B0B))
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start
    ) {

        Spacer(Modifier.height(60.dp))

        Text(
            text = "Welcome Back!",
            color = Color.White,
            fontSize = 42.sp ,
            fontWeight = FontWeight.Medium,
            fontFamily = Outfit
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Log in to manage your daily expenses & savings",
            color = Color(0xFF9E9E9E),
            fontSize = 14.sp
        )

        Spacer(Modifier.height(32.dp))

        // Email
        Text(
            text = "Email",
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = Outfit
        )

        Spacer(Modifier.height(6.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1A1A1A), shape = RoundedCornerShape(10.dp))
                .padding(horizontal = 16.dp, vertical = 3.dp)
        ) {
            TextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth(),
                textStyle = LocalTextStyle.current.copy(color = Color.White),
                placeholder = { Text("Enter your email", color = Color.Gray) },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color(0xFFB2FF59)
                )
            )
        }

        Spacer(Modifier.height(14.dp))

        // Password
        Text(
            text = "Password",
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = Outfit
        )
        Spacer(Modifier.height(6.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1A1A1A), shape = RoundedCornerShape(10.dp))
                .padding(horizontal = 16.dp, vertical = 3.dp)
        ) {
            TextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth(),
                textStyle = LocalTextStyle.current.copy(color = Color.White),
                placeholder = { Text("Enter your password", color = Color.Gray) },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color(0xFFB2FF59)
                )
            )
        }

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Forgot Password?",
            color = Color(0xFFB2FF59),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .align(Alignment.End)
                .clickable { onForgotPasswordClick() }
        )

        Spacer(Modifier.height(24.dp))

        // Sign-In Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .background(Color(0xFFB2FF59), shape = RoundedCornerShape(12.dp))
                .clickable { onLoginClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Sign In",
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Outfit
            )
        }


        Spacer(Modifier.height(20.dp))

        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Don't have an account?", color = Color.Gray, fontSize = 14.sp)
            Spacer(Modifier.width(6.dp))
            Text(
                "Sign Up",
                color = Color(0xFFB2FF59),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onSignUpClick() }
            )
        }
    }
}
