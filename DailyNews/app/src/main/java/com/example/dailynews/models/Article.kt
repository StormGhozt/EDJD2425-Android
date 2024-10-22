package com.example.dailynews.models
import com.example.dailynews.parseDate
import org.json.JSONObject
import java.util.Date

data class Article (
    val title: String?,

    val description: String?,

    val imageUrl: String?,

    val url: String?,

    val publishedAt: Date?
){
    companion object{
        fun fromJson(json: JSONObject): Article {
            return Article(
                json.getString("cleanTitle"),
                json.getString("descricao"),
                json.getString("multimediaPrincipal"),
                json.getString("url"),
                json.getString("data").parseDate()
            )
        }
    }
}