package com.example.dailynews.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.dailynews.models.Article
import com.example.dailynews.ui.ArticleRow
import com.example.dailynews.ui.theme.DailyNewsTheme
import java.util.Date


@Composable
fun HomeView( modifier: Modifier = Modifier) {

    var articles = arrayListOf(
        Article(
            "Vazio",
            "description",
            "https://beebom.com/wp-content/uploads/2024/06/wuthering-waves-review.jpg",
            "https://beebom.com/wp-content/uploads/2024/06/wuthering-waves-review.jpg",
            Date(),

        ),
        Article(
            "WEFWEFWEFWF ",
            "description",
            "https://beebom.com/wp-content/uploads/2024/06/wuthering-waves-review.jpg",
            "https://beebom.com/wp-content/uploads/2024/06/wuthering-waves-review.jpg",
            Date()),

        )

    LazyColumn {
        items(articles) { article ->
            ArticleRow(article)
        }

    }
}


@Preview(showBackground = true)
@Composable
fun HomeViewPreview() {
    DailyNewsTheme {
        HomeView()
    }
}