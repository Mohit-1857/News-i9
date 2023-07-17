package com.example.test

import com.example.test.DataModel.News
import com.google.gson.annotations.SerializedName

public class NewsList {
    @get:SerializedName("data")

    val newsL : List<News> = TODO()

}