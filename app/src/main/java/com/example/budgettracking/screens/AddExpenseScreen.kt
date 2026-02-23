package com.example.budgettracking.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import com.example.budgettracking.R
import com.example.budgettracking.data.model.Transaction
import com.example.budgettracking.data.model.TransactionType
import com.example.budgettracking.viewmodel.TransactionViewModel

@Composable
fun AddExpenseScreen(
    viewModel: TransactionViewModel,
    onBack: () -> Unit
) {
    var isExpense by remember { mutableStateOf(true) }
    var amount by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0E0E0E))
            .padding(20.dp)
    ) {

        Text(
            text = "Add Transaction",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // -------- CARD --------
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF4A4A4A), RoundedCornerShape(24.dp))
                .padding(20.dp)
        ) {

            // Top green line
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .background(Color(0xFFB7F774), RoundedCornerShape(2.dp))
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Toggle
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF2A2A2A), RoundedCornerShape(30.dp))
                    .padding(4.dp)
            ) {
                ToggleChip("Expense", isExpense) { isExpense = true }
                ToggleChip("Income", !isExpense) { isExpense = false }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Amount Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {

                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .background(Color(0xFF2A2A2A), RoundedCornerShape(12.dp))
                )

                Spacer(modifier = Modifier.width(12.dp))

                OutlinedTextField(
                    value = amount,
                    onValueChange = {
                        amount = it.filter { ch -> ch.isDigit() }
                    },


                    placeholder = {
                        Text("₹ 00,00", fontSize = 28.sp)
                    },
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    ),
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        cursorColor = Color.White
                    )
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_calendar),
                    contentDescription = null,
                    tint = Color.LightGray
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            DarkInputField(
                value = title,
                onValueChange = { title = it },
                placeholder = "Enter Title"
            )

            Spacer(modifier = Modifier.height(16.dp))

            DarkInputField(
                value = note,
                onValueChange = { note = it },
                placeholder = "Enter Note",
                singleLine = false
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            enabled = amount.isNotBlank() && title.isNotBlank(),
            onClick = {
                viewModel.addTransaction(
                    Transaction(
                        id = System.currentTimeMillis(),
                        title = title,
                        note = note,
                        amount = amount.toDouble(),
                        type = if (isExpense)
                            TransactionType.EXPENSE
                        else
                            TransactionType.INCOME
                    )
                )
                onBack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF5A74FF)
            )
        ) {
            Text(
                text = "Add",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun DarkInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    singleLine: Boolean = true
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder) },
        singleLine = singleLine,
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFF2E2E2E),
            unfocusedContainerColor = Color(0xFF2E2E2E),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.White,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        ),
        shape = RoundedCornerShape(14.dp)
    )
}

@Composable
private fun RowScope.ToggleChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .weight(1f)
            .background(
                if (selected) Color(0xFF6C5DD3) else Color.Transparent,
                RoundedCornerShape(24.dp)
            )
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (selected) Color.White else Color.LightGray,
            fontWeight = FontWeight.Medium
        )
    }
}
