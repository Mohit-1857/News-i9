package com.example.test.Home

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.test.DataModel.News
import com.example.test.NetworkRequest.APIService
import com.example.test.NewsResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class NewsRepository
@Inject
constructor(private val apiService: APIService){

    var newsListResponse : List<News> by mutableStateOf(listOf())
    var newsListResponseSearch : List<News> by mutableStateOf(listOf())
    var error : String by mutableStateOf("Fail")
    val context : Context
        get() {
            TODO()
        }





    fun getNews(categoryN : String) {
        val call = apiService.getNews(category = categoryN)
        call.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful) {
                    val newsResponse = response.body()

                    if (newsResponse != null) {
                        newsListResponse = newsResponse.articles
                    }


                } else {

                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    error = t.message.toString()
            }
        })
    }

    fun getAllNews(){
        val call = apiService.getEveryNews()
        call.enqueue(object : Callback<NewsResponse>{
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if(response.isSuccessful){
                    val newsResponse = response.body()
                    if (newsResponse != null) {
                        newsListResponseSearch = newsResponse.articles
                    }
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                val newsResponse = t.message

            }

        })
    }

}