package com.example.budgettracking.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0B0B0B))
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {

        // ---------- HEADER ----------
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.AccountCircle,
                    contentDescription = null,
                    tint = Color.LightGray,
                    modifier = Modifier.size(42.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "Farida Orjuova",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Row {
                Icon(
                    Icons.Default.DarkMode,
                    contentDescription = null,
                    tint = Color.LightGray,
                    modifier = Modifier.padding(end = 12.dp)
                )
                Icon(
                    Icons.Default.NotificationsNone,
                    contentDescription = null,
                    tint = Color.LightGray
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // ---------- GRID ----------
        Column(verticalArrangement = Arrangement.spacedBy(18.dp)) {

            ProfileRow(
                ProfileItem("Profile", "Login, authenticator", Icons.Default.Person),
                ProfileItem("Appearance", "Widgets, Themes", Icons.Default.GridView)
            )

            ProfileRow(
                ProfileItem("General", "Currency, clear data", Icons.Default.MoreVert),
                ProfileItem("Settings", "Account, notifications", Icons.Default.Settings)
            )

            ProfileRow(
                ProfileItem("Data", "Export & import features", Icons.Default.Storage),
                ProfileItem("Privacy", "Password, permissions", Icons.Default.Lock)
            )
        }
    }
}

data class ProfileItem(
    val title: String,
    val subtitle: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

@Composable
fun ProfileRow(item1: ProfileItem, item2: ProfileItem) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        ProfileCard(item1, Modifier.weight(1f))
        ProfileCard(item2, Modifier.weight(1f))
    }
}

@Composable
fun ProfileCard(
    item: ProfileItem,
    modifier: Modifier = Modifier
) {

    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .shadow(8.dp, RoundedCornerShape(24.dp))
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A))
    ) {

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            Box(
                modifier = Modifier
                    .size(42.dp)
                    .background(Color(0xFF2A2A2A), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    item.icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = item.title,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = item.subtitle,
                color = Color.Gray,
                fontSize = 12.sp
            )

            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    Divider(color = Color.DarkGray)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Option 1", color = Color.LightGray, fontSize = 13.sp)
                    Text("Option 2", color = Color.LightGray, fontSize = 13.sp)
                }
            }
        }
    }
}