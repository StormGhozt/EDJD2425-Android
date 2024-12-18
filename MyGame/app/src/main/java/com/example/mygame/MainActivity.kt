package com.example.mygame

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mygame.ui.theme.MyGameTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()

        setContent {
            // Initialize NavController for navigation
            val navController = rememberNavController()

            // Define your theme and scaffold
            MyGameTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->

                    // Set up NavHost to manage navigation
                    NavHost(navController = navController, startDestination = "game_start") {
                        // Starting screen destination
                        composable("game_start") {
                            // Pass the onPlayClick lambda to transition to the game screen
                            StartingScreen(onStartGame = {
                                navController.navigate("game_screen")
                            })
                        }

                        // Game screen destination
                        composable("game_screen") {
                            // This composable represents your game screen
                            GameScreenView()
                        }
                    }
                }
            }
        }
    }
}