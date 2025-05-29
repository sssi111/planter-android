package com.example.planter.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun BottomBar(
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier) {
        BottomBarItem(
            icon = Icons.Default.Home,
            label = "Home",
            selected = selectedIndex == 0,
            onClick = { onTabSelected(0) }
        )
        BottomBarItem(
            icon = Icons.Default.Favorite,
            label = "Favourite",
            selected = selectedIndex == 1,
            onClick = { onTabSelected(1) }
        )
        BottomBarItem(
            icon = Icons.Default.ShoppingCart,
            label = "Shopping",
            selected = selectedIndex == 2,
            onClick = { onTabSelected(2) }
        )
        BottomBarItem(
            icon = Icons.Default.Person,
            label = "Profile",
            selected = selectedIndex == 3,
            onClick = { onTabSelected(3) }
        )
    }
}

@Composable
private fun androidx.compose.foundation.layout.RowScope.BottomBarItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = { Icon(icon, contentDescription = label) },
        label = { Text(label) }
    )
}