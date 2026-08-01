package com.example.budgettracking.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.budgettracking.data.model.TransactionType
import com.example.budgettracking.ui.theme.AppAccent
import com.example.budgettracking.ui.theme.AppBackground
import com.example.budgettracking.ui.theme.AppExpense
import com.example.budgettracking.ui.theme.AppSurface
import com.example.budgettracking.ui.theme.AppSurfaceVariant
import com.example.budgettracking.ui.theme.AppTextPrimary
import com.example.budgettracking.ui.theme.AppTextSecondary
import com.example.budgettracking.ui.theme.AppThemeState
import com.example.budgettracking.viewmodel.TransactionViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlin.math.roundToInt

@Composable
fun ProfileScreen(
    viewModel: TransactionViewModel = viewModel(),
    onNotificationsClick: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    val context = LocalContext.current
    val transactions by viewModel.transactions.collectAsState()

    val currentUser = remember { FirebaseAuth.getInstance().currentUser }
    val email = currentUser?.email ?: "alex.morgan@email.com"
    val name = currentUser?.displayName?.takeIf { it.isNotBlank() }
        ?: email.substringBefore('@').replaceFirstChar { it.uppercase() }

    val income = transactions.filter { it.type == TransactionType.INCOME }.sumOf { it.amount }
    val expense = transactions.filter { it.type == TransactionType.EXPENSE }.sumOf { it.amount }
    val balance = income - expense
    val savingsRate = if (income > 0) ((income - expense) / income * 100).roundToInt() else 0

    var notificationsOn by remember { mutableStateOf(true) }
    var showLogoutDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {

        // ---------- TOP BAR ----------
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Profile", color = AppTextPrimary, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            IconButton(onClick = onNotificationsClick) {
                Icon(Icons.Default.NotificationsNone, "Notifications", tint = AppTextPrimary)
            }
        }

        Spacer(Modifier.height(16.dp))

        // ---------- IDENTITY ----------
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(AppSurfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = name.take(1).uppercase(),
                    color = AppAccent,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(Modifier.width(16.dp))
            Column {
                Text(name, color = AppTextPrimary, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                Text(email, color = AppTextSecondary, fontSize = 13.sp)
            }
        }

        Spacer(Modifier.height(20.dp))

        // ---------- STATS STRIP ----------
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(AppSurface)
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            StatCell("Balance", money(balance), if (balance >= 0) AppAccent else AppExpense)
            StatDivider()
            StatCell("Transactions", transactions.size.toString(), AppTextPrimary)
            StatDivider()
            StatCell("Saved", "$savingsRate%", AppAccent)
        }

        Spacer(Modifier.height(24.dp))

        // ---------- SETTINGS ----------
        SectionLabel("Preferences")
        SettingsGroup {
            ToggleRow(
                icon = Icons.Default.Notifications,
                title = "Notifications",
                checked = notificationsOn,
                onCheckedChange = { notificationsOn = it }
            )
            RowDivider()
            NavRow(Icons.Default.CurrencyRupee, "Currency", trailing = "INR (₹)") {
                Toast.makeText(context, "Currency is set to Indian Rupee", Toast.LENGTH_SHORT).show()
            }
            RowDivider()
            ToggleRow(
                icon = if (AppThemeState.isDark) Icons.Default.DarkMode else Icons.Default.LightMode,
                title = "Dark mode",
                checked = AppThemeState.isDark,
                onCheckedChange = { AppThemeState.isDark = it }
            )
        }

        Spacer(Modifier.height(20.dp))

        SectionLabel("Account")
        SettingsGroup {
            NavRow(Icons.Default.Person, "Edit profile") {
                Toast.makeText(context, "Coming soon", Toast.LENGTH_SHORT).show()
            }
            RowDivider()
            NavRow(Icons.Default.Lock, "Privacy & security") {
                Toast.makeText(context, "Coming soon", Toast.LENGTH_SHORT).show()
            }
            RowDivider()
            NavRow(Icons.Default.Storage, "Export data") {
                Toast.makeText(context, "Coming soon", Toast.LENGTH_SHORT).show()
            }
            RowDivider()
            NavRow(Icons.Default.HelpOutline, "Help & support") {
                Toast.makeText(context, "Coming soon", Toast.LENGTH_SHORT).show()
            }
        }

        Spacer(Modifier.height(24.dp))

        // ---------- LOG OUT ----------
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(AppSurface)
                .clickable { showLogoutDialog = true },
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.AutoMirrored.Filled.Logout, null, tint = AppExpense, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(8.dp))
                Text("Log out", color = AppExpense, fontWeight = FontWeight.Medium)
            }
        }

        Spacer(Modifier.height(16.dp))
    }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            containerColor = AppSurface,
            title = { Text("Log out?", color = AppTextPrimary) },
            text = { Text("You'll need to sign in again to access your budget.", color = AppTextSecondary) },
            confirmButton = {
                TextButton(onClick = {
                    showLogoutDialog = false
                    FirebaseAuth.getInstance().signOut()
                    onLogout()
                }) { Text("Log out", color = AppExpense) }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Cancel", color = AppAccent)
                }
            }
        )
    }
}

@Composable
private fun RowScope.StatCell(label: String, value: String, valueColor: Color) {
    Column(
        modifier = Modifier.weight(1f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(value, color = valueColor, fontWeight = FontWeight.Bold, fontSize = 15.sp, maxLines = 1)
        Spacer(Modifier.height(4.dp))
        Text(label, color = AppTextSecondary, fontSize = 12.sp)
    }
}

@Composable
private fun StatDivider() {
    Box(
        modifier = Modifier
            .width(1.dp)
            .height(32.dp)
            .background(AppSurfaceVariant)
    )
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text,
        color = AppTextSecondary,
        fontSize = 13.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
    )
}

@Composable
private fun SettingsGroup(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(AppSurface),
        content = content
    )
}

@Composable
private fun RowDivider() {
    HorizontalDivider(color = AppSurfaceVariant, modifier = Modifier.padding(start = 56.dp))
}

@Composable
private fun NavRow(
    icon: ImageVector,
    title: String,
    trailing: String? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = AppAccent, modifier = Modifier.size(22.dp))
        Spacer(Modifier.width(14.dp))
        Text(title, color = AppTextPrimary, fontSize = 15.sp, modifier = Modifier.weight(1f))
        if (trailing != null) {
            Text(trailing, color = AppTextSecondary, fontSize = 13.sp)
            Spacer(Modifier.width(6.dp))
        }
        Icon(Icons.Default.ChevronRight, null, tint = AppTextSecondary, modifier = Modifier.size(20.dp))
    }
}

@Composable
private fun ToggleRow(
    icon: ImageVector,
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = AppAccent, modifier = Modifier.size(22.dp))
        Spacer(Modifier.width(14.dp))
        Text(title, color = AppTextPrimary, fontSize = 15.sp, modifier = Modifier.weight(1f))
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.Black,
                checkedTrackColor = AppAccent,
                uncheckedThumbColor = AppTextSecondary,
                uncheckedTrackColor = AppSurfaceVariant
            )
        )
    }
}
