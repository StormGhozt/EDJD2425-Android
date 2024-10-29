package com.example.dailynews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dailynews.ui.ArticleDetailsScreen
import com.example.dailynews.ui.BottomBar
import com.example.dailynews.ui.HomeView
import com.example.dailynews.ui.theme.DailyNewsTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DailyNewsTheme {
                val navController = rememberNavController()
                val currentCategory = remember { mutableStateOf(Screen.Latest) }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomBar(navController) }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Latest.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Home.route) { backStackEntry ->
                            val category = backStackEntry.arguments?.getString("category") ?: "latest"
                            HomeView(
                                navController = navController,
                                category = category
                            )
                        }
                        composable(
                            "article_details/{articleUrl}",
                            arguments = listOf(navArgument("articleUrl") { type = NavType.StringType })
                        ) { backStackEntry ->
                            ArticleDetailsScreen(
                                articleUrl = backStackEntry.arguments?.getString("articleUrl") ?: "",
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}

sealed class Screen(val route: String) {
    object Home : Screen("home/{category}")
    object Latest: Screen("home/ultimas")
    object Technology : Screen("home/tecnologia")
    object Sports : Screen("home/desporto")
    object Politics : Screen("home/politica")

    object ArticleDetail : Screen("article_detail/{articleUrl}")

}