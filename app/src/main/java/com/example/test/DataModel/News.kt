package com.example.test.DataModel

import com.example.test.Source

data class News(
    val source: Source,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage : String,
    val publishedAt: String,
    val content: String
)
