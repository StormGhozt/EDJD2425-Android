package com.example.dailynews.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import com.example.dailynews.models.Article
import com.example.dailynews.ui.theme.DailyNewsTheme
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.IOException
import org.json.JSONArray


@Composable
fun HomeView(navController: NavHostController, modifier: Modifier = Modifier) {

    var articles by remember { mutableStateOf(listOf<Article>()) }


    LazyColumn(modifier = modifier.padding(top = 70.dp)) {
        itemsIndexed(items= articles) {index,article ->
            Aspect(article, navController)
        }

    }



    LaunchedEffect(Unit){
        val client = OkHttpClient()

        val request = Request.Builder()
        .url("https://www.publico.pt/api/list/ultimas")
        .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    val result = response.body!!.string()
                    val jsonResult = JSONArray(result)
                    val articlesList = mutableListOf<Article>() // Create a list outside the loop
                    for (i in 0 until jsonResult.length()) {
                        val articleJson = jsonResult.getJSONObject(i)
                        val article = Article.fromJson(articleJson)
                        articlesList.add(article) // Add article to the list
                    }
                    articles = articlesList // Update the articles state variable
                }
            }
        })
    }

    NavigationBar(
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth()
    ) {
        val DarkTheme =isSystemInDarkTheme()
        Column(
            modifier = Modifier
                .height(120.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Bottom // Align items to the bottom
        ) {
            val items = listOf("Home", "Notification", "Settings")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly // Distribute items evenly
            ) {
                items.forEach { item ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(60.dp)
                            .background(if (DarkTheme) Color(0xFF292929) else Color(0xFFd4d4d4)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = item)
                    }
                }
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun HomeViewPreview() {
    DailyNewsTheme {

    }
}