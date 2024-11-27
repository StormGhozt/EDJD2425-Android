package com.example.shoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.shoppinglist.Lists.AddListView
import com.example.shoppinglist.Lists.Item.AddItemView
import com.example.shoppinglist.Lists.Item.ListItemsView
import com.example.shoppinglist.Lists.ListListsView

import com.example.shoppinglist.Login.LoginView
import com.example.shoppinglist.Login.RegisterView
import com.example.shoppinglist.ui.theme.ShoppingListTheme
import com.google.firebase.auth.FirebaseAuth

const val TAG = "ShoppingList"

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        auth = FirebaseAuth.getInstance()
        setContent {
            ShoppingListTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = Screen.Login.route
                    ) {
                        composable(Screen.Login.route) {
                            LoginView(
                                modifier = Modifier.padding(innerPadding),
                                onLoginSuccess = {
                                    navController.navigate(Screen.Home.route)
                                },
                                navController = navController
                            )
                        }
                        composable(Screen.Home.route) {
                            ListListsView(
                                navController = navController
                            )
                        }
                        composable(Screen.AddList.route) {
                            AddListView(navController = navController)
                        }
                        composable(Screen.Register.route) {
                            RegisterView(
                                modifier = Modifier.padding(innerPadding),
                                onRegisterSuccess = {
                                    navController.navigate(Screen.Home.route)
                                }
                            )
                        }
                        composable(
                            route = Screen.ListItem.route + "?listId={listId}&listName={listName}",
                            arguments = listOf(
                                navArgument("listId") { type = NavType.StringType },
                                navArgument("listName") { type = NavType.StringType }
                            )
                        ) {
                            val listId = it.arguments?.getString("listId") ?: ""
                            val listName = it.arguments?.getString("listName") ?: ""
                            ListItemsView(
                                navController = navController,
                                modifier = Modifier.padding(innerPadding),
                                listId = listId,
                                listName = listName
                            )
                        }
                        composable(
                            route = Screen.AddItem.route + "?listId={listId}",
                            arguments = listOf(
                                navArgument("listId") { type = NavType.StringType }
                            )
                        ) {
                            val listId = it.arguments?.getString("listId") ?: ""
                            AddItemView(
                                navController = navController,
                                listId = listId,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }
                }
                LaunchedEffect(Unit) {
                    val currentUser = auth.currentUser
                    if (currentUser != null) {
                        navController.navigate(Screen.Home.route)
                    }
                }
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            reload()
        }
    }

    private fun reload() {
        // Implement your reload logic here
    }
}



sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Home : Screen("home")
    object AddList : Screen("addList")
    object Register : Screen("register")
    object ListItem : Screen("listItem")
    object AddItem : Screen("addItem")
}