package com.example.dailynews.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
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
fun HomeView(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    category: String
) {

    var articles by remember { mutableStateOf(listOf<Article>()) }

    LaunchedEffect(Unit){
        val client = OkHttpClient()

        val request = Request.Builder()
        .url("https://www.publico.pt/api/list/$category")
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

    LazyColumn(
        modifier = modifier.padding(top = 1.dp)
    ) {
        itemsIndexed(items= articles) {index,article ->
            Aspect(article, navController)
        }
    }
}
