package com.example.dailynews.ui



import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.dailynews.R
import com.example.dailynews.models.Article
import com.example.dailynews.ui.theme.DailyNewsTheme
import java.util.Date

@Composable
fun ArticleRow(article:Article) {

   Row {
       //Box(modifier = Modifier.size(100.dp).background(Color.Gray)) {
           article.urlToImage?.let {
               AsyncImage(
                   model = it,
                   contentDescription = "image article",
                   modifier = Modifier.padding(4.dp)
                       .height(100.dp)
                       .width(100.dp)
               )

           }?:run{
               Image(
                   modifier = Modifier.padding(4.dp)
                       .height(100.dp)
                       .width(100.dp),
                   painter =  painterResource(id = R.drawable.baseline_broken_image_24),
                   contentDescription = "image article" )
           }
       //}
       Column(modifier = Modifier.padding(4.dp)) {
       Text(text = article.title ?: "vazio")
       Text(text = article.description!!)
       Text(Date().toString())
       }

   }
}

@Preview(showBackground = true)
@Composable
fun ArticleRowPreview() {
    var articles =
        Article(
            "Article",
            "description",
            "https://beebom.com/wp-content/uploads/2024/06/wuthering-waves-review.jpg",
            "https://beebom.com/wp-content/uploads/2024/06/wuthering-waves-review.jpg",
            Date()
        )
    DailyNewsTheme {
        ArticleRow(articles)
    }
}