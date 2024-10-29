package com.example.dailynews.ui

import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController

@Composable
fun ArticleDetailsScreen(articleUrl: String, navController: NavHostController) {
    AndroidView(factory = { context ->
        WebView(context).apply {
            webViewClient = object : WebViewClient() {
                override fun onReceivedError(
                    view: WebView,
                    request: WebResourceRequest,
                    error: WebResourceError
                ) {
                    navController.navigate("error_screen")
                }
            }
            loadUrl(articleUrl)
        }
    })
}