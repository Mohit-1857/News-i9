package com.example.test.Home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.test.DataModel.News
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class NewsViewModel
@Inject
constructor(private val newsRepository: NewsRepository) : ViewModel(){
    var listResponse : List<News> by mutableStateOf(listOf())
    var searchList : List<News> by mutableStateOf(listOf())
    var errorMessage : String by mutableStateOf("")

    fun getNewss(category: String){
        newsRepository.getNews(category)
        listResponse = newsRepository.newsListResponse
        errorMessage = newsRepository.error
    }

    fun getAllNews(){
        newsRepository.getAllNews()
        searchList  = newsRepository.newsListResponseSearch
        errorMessage = newsRepository.error

    }




}