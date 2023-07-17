package com.example.test

import com.example.test.DataModel.News

data class NewsResponse(
    val articles: List<News>,
    val status: String,
    val totalResults: Int
)