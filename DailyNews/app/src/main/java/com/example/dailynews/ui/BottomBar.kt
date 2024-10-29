package com.example.dailynews.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.dailynews.R
import com.example.dailynews.Screen

data class BottomNavigationItem(
    val title: String,
    val icon: ImageVector,
    val screen: Screen,
)

@Composable
fun BottomBar(
    navController: NavController = rememberNavController()
) {

    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }

    val items = listOf(
        BottomNavigationItem(
            title = "Home",
            icon = Icons.Filled.Home,
            screen = Screen.Latest
        ),
        BottomNavigationItem(
            title = "Technology",
            icon = ImageVector.vectorResource(R.drawable.devices_24px),
            screen = Screen.Technology
        ),
        BottomNavigationItem(
            title = "Sports",
            icon = ImageVector.vectorResource(R.drawable.sports_and_outdoors_24px),
            screen = Screen.Sports
        ),
        BottomNavigationItem(
            title = "Politics",
            icon = ImageVector.vectorResource(R.drawable.gavel_24px),
            screen = Screen.Politics
        )
    )


    BottomAppBar {
        NavigationBar {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = selectedItemIndex == index,
                    onClick = {
                        selectedItemIndex = index
                        navController.navigate(item.screen.route)
                    },
                    label = {
                        Text(text = item.title)
                    },
                    alwaysShowLabel = true,
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title
                        )
                    }
                )
            }
        }
    }
}