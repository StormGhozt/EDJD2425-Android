package com.example.dailynews.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.dailynews.R
import com.example.dailynews.models.Article
import com.example.dailynews.toYYYYMMDD
import android.net.Uri
import android.text.Html
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.NavigationBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp


@Composable
fun Aspect(article:Article, navController: NavHostController) {

    val DarkTheme = isSystemInDarkTheme()
    Box(
        modifier = Modifier
            .padding(7.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(if (DarkTheme) Color(0xFF292929) else Color(0xFFd4d4d4))
            .clickable {
                if (article.url != null && article.url.isNotBlank()) {
                    val encodedUrl = Uri.encode(article.url) // Encode the URL
                    navController.navigate("article_details/$encodedUrl")
                } else {
                    navController.navigate("error_screen")
                }
            }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {


            Column(modifier = Modifier.padding(4.dp)) {
                article.imageUrl?.let {
                    AsyncImage(
                        model = it,
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center,
                        contentDescription = "image article",
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(10.dp))
                    )
                } ?: run {
                    Image(
                        modifier = Modifier
                            .padding(4.dp)
                            .height(100.dp)
                            .width(100.dp),
                        painter = painterResource(id = R.drawable.baseline_broken_image_24),
                        contentDescription = "image article"
                    )
                }
                Text(
                    text = article.title ?: "", fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Left,
                    fontSize = 25.sp,
                    fontFamily = FontFamily.Serif
                )
                Text(
                    textAlign = TextAlign.Left,
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Serif,
                    text = if (!article.description.isNullOrEmpty()) {
                        Html.fromHtml(article.description, Html.FROM_HTML_MODE_LEGACY)
                            .toString()
                    } else ""
                )
                Row(modifier = Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.weight(1f)) // Push text to the right
                    Text(
                        text = article.publishedAt?.toYYYYMMDD() ?: "",
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Serif
                    )
                }
            }
        }
    }

}

