package com.example.test.Read_Later

import androidx.lifecycle.ViewModel
import com.example.test.Credentials.ViewModel.FAuthViewModel
import com.example.test.DataModel.News
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap


@HiltViewModel
class FIrebaseDatabaseViewModel
@Inject
constructor() : ViewModel()

{

    private val firestore : FirebaseFirestore = FirebaseFirestore.getInstance()


    fun storeData( data : News , id : String){
        val news = mutableMapOf<String , String>()
        news["title"] = data.title
        news["description"] = data.description
        news["url"] = data.url
        news["urlToImage"] = data.urlToImage
        news["author"] = data.author
        news["publishedAt"] = data.publishedAt
        news["content"] = data.content
        news["source"] = data.source.name



        firestore.collection("Users").document(id).collection("Read Later").add(news)

    }

    fun readData(){
        firestore.collection("Users").document().collection("Read Later")
            .get()
            .addOnCompleteListener{
                it.getResult()
            }
    }
}