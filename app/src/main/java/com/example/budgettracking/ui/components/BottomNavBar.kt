package com.example.budgettracking.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun BottomNavBar(
    navController: NavController,
    onAddClick: () -> Unit
) {
    Box {
        // Bottom bar background
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(Color(0xFF1C1C1C))
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = { navController.navigate("home") }) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    tint = Color(0xFFB8F27C)
                )
            }

            IconButton(onClick = { navController.navigate("stats") }) {
                Icon(
                    imageVector = Icons.Default.BarChart,
                    contentDescription = "Stats",
                    tint = Color.Gray
                )
            }

            Spacer(modifier = Modifier.width(50.dp)) // Space for FAB

            IconButton(onClick = { navController.navigate("insights") }) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = "Insights",
                    tint = Color.Gray
                )
            }

            IconButton(onClick = { navController.navigate("profile") }) {
                Icon(
                    imageVector = Icons.Default.GridView,
                    contentDescription = "Profile",
                    tint = Color.Gray
                )
            }
        }

        // Center FAB
        Box(
            modifier = Modifier
                .size(64.dp)
                .align(Alignment.TopCenter)
                .offset(y = (-20).dp)
                .shadow(8.dp, CircleShape)
                .background(Color(0xFFB8F27C), CircleShape)
                .clickable { onAddClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                tint = Color.Black,
                modifier = Modifier.size(32.dp)
            )
        }
    }

}
