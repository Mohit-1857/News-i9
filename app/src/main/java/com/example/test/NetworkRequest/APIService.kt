package com.example.test.NetworkRequest

import com.example.test.DataModel.News
import com.example.test.NewsList
import com.example.test.NewsResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {


    companion object{
        const val BASE_URL = "https://newsapi.org/v2/"
    }


    @GET("top-headlines")
     fun getNews(
        @Query("country") country:String="in",
        @Query("page") page: Int =1,
        //@Query("language") language:String="he",
        @Query("category") category: String = "",
        @Query("apiKey") apiKey:String="c7bd4787bfd746f681c7680919415a2b"

    ) : Call<NewsResponse>


     @GET("everything")
     fun getEveryNews(
         @Query("apiKey") apiKey:String="c7bd4787bfd746f681c7680919415a2b"
     ) : Call<NewsResponse>

//    companion object{
//        var apiService : APIService?= null
//        fun getInstance() : APIService{
//            if(apiService == null){
//                apiService = Retrofit.Builder()
//                    .baseUrl("https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=")
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build().create(APIService::class.java)
//            }
//            return apiService!!
//        }
//    }

}