package com.example.budgettracking.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
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
import com.example.budgettracking.ui.theme.AppAccent
import com.example.budgettracking.ui.theme.AppBackground
import com.example.budgettracking.ui.theme.AppSurface
import com.example.budgettracking.ui.theme.AppSurfaceVariant
import com.example.budgettracking.ui.theme.AppTextPrimary
import com.example.budgettracking.ui.theme.AppTextSecondary
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
            .background(AppBackground)
            .padding(20.dp)
    ) {

        BackTopBar(title = "Add Transaction", onBack = onBack)

        Spacer(modifier = Modifier.height(16.dp))

        // -------- CARD --------
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(AppSurface, RoundedCornerShape(24.dp))
                .padding(20.dp)
        ) {

            // Top green line
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .background(AppAccent, RoundedCornerShape(2.dp))
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Toggle
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AppSurfaceVariant, RoundedCornerShape(30.dp))
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
                        .background(AppSurfaceVariant, RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountBalanceWallet,
                        contentDescription = null,
                        tint = AppAccent,
                        modifier = Modifier.size(22.dp)
                    )
                }

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
                        color = AppTextPrimary
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
                        cursorColor = AppTextPrimary
                    )
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_calendar),
                    contentDescription = null,
                    tint = AppTextSecondary
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
                containerColor = AppAccent,
                contentColor = Color.Black,
                disabledContainerColor = AppSurfaceVariant,
                disabledContentColor = AppTextSecondary
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
            focusedContainerColor = AppSurfaceVariant,
            unfocusedContainerColor = AppSurfaceVariant,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = AppAccent,
            focusedTextColor = AppTextPrimary,
            unfocusedTextColor = AppTextPrimary
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
                if (selected) AppAccent else Color.Transparent,
                RoundedCornerShape(24.dp)
            )
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (selected) Color.Black else AppTextSecondary,
            fontWeight = FontWeight.Medium
        )
    }
}
