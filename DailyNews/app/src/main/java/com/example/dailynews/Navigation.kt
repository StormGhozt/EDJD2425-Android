package com.example.dailynews.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.dailynews.ui.ArticleDetailsScreen
import com.example.dailynews.ui.HomeView

@Composable
fun AppNavigation(modifier: Modifier) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeView(navController, modifier= modifier) }
        composable(
            "article_details/{articleUrl}",
            arguments = listOf(navArgument("articleUrl") { type = NavType.StringType })
        ) { backStackEntry ->
            // Pass navController to ArticleDetailsScreen
            ArticleDetailsScreen(
                articleUrl = backStackEntry.arguments?.getString("articleUrl") ?: "",
                navController = navController
            )
        }
        composable("error_screen") { ErrorScreen() }
    }
}

@Composable
fun ErrorScreen() {
    Text("Error: Invalid article URL")
}