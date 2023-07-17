package com.example.test.Data_Store

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.Credentials.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class DataStoreViewModel
@Inject
constructor(private val dataStorePrefrence: DataStorePrefrence) : ViewModel()
{
    fun writeToLocal(key : String) = viewModelScope.launch{
        dataStorePrefrence.saveToDataStore(key)
    }



    val id : String = dataStorePrefrence.read.toString()

//    suspend fun readToLocal() = dataStorePrefrence.readToLocal().collect{
//            withContext(Dispatchers.Main){
//                id = it
//            }
//    }
}